/**
 * 
 */
package edu.ncsu.csc573.project.commlayer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import edu.ncsu.csc573.project.common.ConfigurationManager;
import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;
import edu.ncsu.csc573.project.common.schema.Request;

/**
 * @author doogle-dev
 * 
 */
public class CommunicationService implements ICommunicationService {

	private Socket clientSocket;
	private InetAddress BSSAddress;
	private static Logger logger;
	
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
					logger.error("Unable to initialize Communication layer exiting");
					throw excpByIpAddress;
				}
			}

			int BSSport = ConfigurationManager.getInstance().getServerPort();
			int timeOut = ConfigurationManager.getInstance().getTimeOut();
			
			clientSocket = new Socket();
			InetSocketAddress serverSocket = new InetSocketAddress(BSSAddress, BSSport);
			clientSocket.setKeepAlive(true);
			logger.debug("Enabled Keepalive socket option");
			try {
				clientSocket.connect(serverSocket, timeOut);
			} catch (SocketTimeoutException e) {
				logger.error("Connection timed out", e);
				throw e;
			} catch (IOException exp) {
				logger.error("Connection refused", exp);
				throw exp;
			}
			
			logger.info("Succesfully initialized communication layer");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.commlayer.ICommunicationService#executeRequest
	 * (edu.ncsu.csc573.project.common.messages.IRequest)
	 */
	public IResponse executeRequest(IRequest request) {
		
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
		if(clientSocket == null) {
			throw new Exception("Communication Layer is not initialized");
		}
		try {
		clientSocket.close();
		} catch (Exception e) {
			logger.error("Failed to close socket. Trying again");
		}
	}

	public boolean isConnected() {
		if(clientSocket == null) {
			logger.info("Communication Layer is not initialized");
			return false;
		}
		return clientSocket.isConnected();
	}
	
	class BlockingThread extends Thread {
		private Socket clientSocket;
		private IRequest request;
		
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
				//Request = transformRequest(request);
				
				PrintWriter pw = new PrintWriter(new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream())));
				JAXBContext context = JAXBContext.newInstance(Request.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(request, pw);
				pw.flush();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
