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
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.RequestMessage;

/**
 * This class handles client request.
 * 
 * @author doogle-dev
 * 
 */
public class ClientHandler implements Runnable {
	private Socket conncetedSocket;
	private Logger logger;

	public ClientHandler(Socket connSock) {
		logger = Logger.getLogger(ClientHandler.class);
		conncetedSocket = connSock;
	}

	public void run() {
		SocketAddress clientAddress = conncetedSocket.getRemoteSocketAddress();
		
		logger.info("Handling client " + clientAddress);

		// Expect register or login request from the client
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conncetedSocket.getInputStream()));

			// read each request and see till it logout
			// sleep till the data is ready from the client
			IRequest req = null;
			do {
				while (!br.ready()) {
					logger.debug("Waiting for request from client "
							+ conncetedSocket.getRemoteSocketAddress());
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						logger.error("Unexpected error from client" + conncetedSocket.getRemoteSocketAddress(), e);
					}
				}

				StringBuffer sb = new StringBuffer();
				int c;
				int charCount = 0;
				while ((c = br.read()) != -1 && sb.indexOf("</request>") == -1) {
					sb.append((char) c);
				}

				logger.debug("received data from client " + sb.toString());
				
				PrintWriter pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(
								conncetedSocket.getOutputStream())));
				
				try {
					req = RequestMessage.createRequest(sb.toString().trim());
					logger.info("Request from client " + conncetedSocket.getRemoteSocketAddress() + " is : "
							+ req.getRequestInXML());
					pw.println(req.getRequestInXML());
					pw.flush();
				} catch (Exception e) {
					logger.error("Unable to parse request", e);
				}
				logger.info("Waiting for requests from client :" + clientAddress);
			} while (req == null || req.getOperationType() != EnumOperationType.LOGOUT);
		} catch (IOException e) {
			logger.error("Failed to read data from the client " + conncetedSocket.getRemoteSocketAddress());
		} finally {
			if(conncetedSocket != null) {
				try {
					conncetedSocket.close();
				} catch (IOException e) {
					logger.error("Failed to close " + conncetedSocket.getRemoteSocketAddress(), e);
				}
			}
		}
	}
}