/**
 * 
 */
package edu.ncsu.csc573.project.commlayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import edu.ncsu.csc573.project.common.ConfigurationManager;
import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;

/**
 * @author doogle-dev
 * 
 */
public class CommunicationService implements ICommunicationService {

	private Socket clientSocket;
	private InetAddress BSSAddress;
	private static Logger logger;
	private PeerServer server;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.commlayer.ICommunicationService#initialize(java
	 * .lang.String, edu.ncsu.csc573.project.commlayer.IPublishHandler)
	 */
	public void initialize(String BootStrapServer,
			IPublishHandler aPublishHandler) throws Exception {
		// get a logger instance for this class
		logger = Logger.getLogger(this.getClass());
		logger.debug("Inside initialization of communication handler");
		/*
		 * if the socket is already connected then initialization is already
		 * done and so simply exiting.
		 */
		synchronized (CommunicationService.class) {
			if (clientSocket != null && clientSocket.isConnected()) {
				logger.debug("Already connected to bootstrapserver");
				logger.info("Already initialized");
				return;
			}

			logger.info("Start server");
			server = new PeerServer();
			try {
				BSSAddress = InetAddress.getByName(BootStrapServer);
			} catch (UnknownHostException excpByHostName) {
				try {
					logger.info("Unable to find the address of the host: "
							+ BootStrapServer + " by name");
					logger.info("Trying by ip address");
					BSSAddress = InetAddress.getByAddress(BootStrapServer
							.trim().getBytes());
				} catch (UnknownHostException excpByIpAddress) {
					logger.info("Unable to find the address of host: "
							+ BootStrapServer + " even by ip address");
					cleanUp();
					throw excpByIpAddress;
				}
			}

			int BSSport = ConfigurationManager.getInstance().getServerPort();
			int timeOut = ConfigurationManager.getInstance().getTimeOut();

			clientSocket = new Socket();
			InetSocketAddress serverSocket = new InetSocketAddress(BSSAddress,
					BSSport);
			clientSocket.setKeepAlive(true);
			clientSocket.setTcpNoDelay(true);
			
			logger.debug("Enabled Keepalive socket option");
			try {
				clientSocket.connect(serverSocket, timeOut);
			} catch (SocketTimeoutException e) {
				logger.error("Connection timed out", e);
				cleanUp();
				throw e;
			} catch (IOException exp) {
				logger.error("Connection refused", exp);
				cleanUp();
				throw exp;
			} 
		}

	}

	private void cleanUp() throws Exception{
		
		if(server != null) {
			server.stop();
		}
		while(!server.isServerRunning()) {
			logger.info("Waiting for peer server to close");
			Thread.sleep(100);
		}
		if(clientSocket != null) {
			clientSocket.close();
		}
		logger.error("Unable to initialize Communication layer. Exiting from Application");
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.commlayer.ICommunicationService#executeRequest
	 * (edu.ncsu.csc573.project.common.messages.IRequest)
	 */
	public IResponse executeRequest(IRequest request) throws Exception{
		BlockingThread bt = new BlockingThread(clientSocket, request);
		bt.start();
		try {
			Thread.currentThread().join(ConfigurationManager.getInstance().getCLITimeOut());
			if(!bt.isResponseReady()) {
				logger.info("Failed to get response for the request : " + request.getOperationType());
				throw new Exception();
			} else {
				logger.info("Received response : " + bt.getResponse());
			}
		} catch (InterruptedException e) {
			
		}
		bt.getResponse();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.commlayer.ICommunicationService#publishRequest
	 * (edu.ncsu.csc573.project.common.messages.IRequest,
	 * edu.ncsu.csc573.project.commlayer.IResponseListener)
	 */
	public void publishRequest(IRequest request, IResponseListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.commlayer.ICommunicationService#subscribeRequestTopic
	 * (edu.ncsu.csc573.project.common.messages.EnumOperationType,
	 * edu.ncsu.csc573.project.commlayer.IRequestListener)
	 */
	public void subscribeRequestTopic(EnumOperationType operationType,
			IRequestListener reqListener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ncsu.csc573.project.commlayer.ICommunicationService#close()
	 */
	public void close() throws Exception {
		if (clientSocket == null) {
			throw new Exception("Communication Layer is not initialized");
		}
		try {
			clientSocket.close();
		} catch (Exception e) {
			logger.error("Failed to close socket. Trying again");
		}
		server.stop();
	}

	public boolean isConnected() {
		if (clientSocket == null) {
			logger.info("Communication Layer is not initialized");
			return false;
		}
		return clientSocket.isConnected();
	}

	class BlockingThread extends Thread {
		private Socket clientSocket;
		private IRequest request;
		private String response = null;
		
		BlockingThread(Socket clientSocket, IRequest request) {
			super();
			this.clientSocket = clientSocket;
			this.request = request;
		}

		public void run() {

			try {
				/**
				 * Request has to be transformed from IRequest to request object
				 */
				PrintWriter pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream())));
				pw.println(request.getRequestInXML());
				//pw.println(System.);
				pw.flush();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				StringBuffer sb = new StringBuffer();
				//String temp;
			
				while(!br.ready()) {
					logger.info("No data received from server. Trying again...");
					Thread.sleep(1000);  // to be removed.
				}
				int ch;
				int charCount = 0;
				while ((ch = br.read()) != -1 && sb.indexOf("</request>") == -1) {
					sb.append((char)ch);
					charCount++;
				}
				
				response = sb.toString();
				logger.info("Response is :");
				logger.info(response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public String getResponse() {
			return response;
		}
		
		public boolean isResponseReady() {
			return (response == null ? false : true);
		}
	}

	public boolean isPeerServerRunning() {
		return server.isServerRunning();
	}
}
