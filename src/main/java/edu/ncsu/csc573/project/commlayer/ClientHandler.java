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

import org.apache.log4j.Logger;

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
		logger.info("Handling client ");

		// Expect register or login request from the client
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conncetedSocket.getInputStream()));

			// sleep till the data is ready from the client
			while (!br.ready()) {
				logger.debug("Waiting for request from client "
						+ conncetedSocket.getRemoteSocketAddress());
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					logger.error("Unexpected error from client", e);
				}
			}

			StringBuffer sb = new StringBuffer();
			int c;
			int charCount = 0;
			while ((c = br.read()) != -1 && sb.indexOf("</request>") == -1) {
				sb.append((char) c);
			}
			logger.info("Request from client is : " + sb.toString());
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					conncetedSocket.getOutputStream())));

			pw.println("Welcome !");
			pw.println("Bye");
			pw.flush();
			logger.info("Done with the client");
		} catch (IOException e) {
			logger.error("Failed to read data from the client socket");
		}
	}

}
