package edu.ncsu.csc573.project.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigurationManager {
	public static final int DEFAULT_SERVER_PORT = 9000;
	public static final int DEFAULT_TIME_OUT = 2;

	private int serverPort;
	private int timeOut;

	private static Properties config = null;
	private static ConfigurationManager confManager = null;
	private static Logger logger;

	private ConfigurationManager() {

	}

	public static ConfigurationManager getInstance() {
		logger = Logger.getLogger(ConfigurationManager.class);

		if (confManager == null) {
			config = new Properties();
			try {
				config.load(ClassLoader
						.getSystemResourceAsStream("configuration.properties"));
			} catch (IOException e) {
				logger.error("Unable to load configuration file", e);
				logger.info("Using default values");
			}
			confManager = new ConfigurationManager();
		}

		return confManager;
	}

	public int getServerPort() {
		String servPortPropertyVal = config.getProperty(
				"SERVER_LISTENING_PORT", String.valueOf(DEFAULT_SERVER_PORT))
				.trim();
		logger.debug("Server port property value obtained from configuration file is "
				+ servPortPropertyVal);
		
		if (serverPort == 0) {
			try {
				serverPort = Integer.parseInt(servPortPropertyVal);
			} catch (Exception e) {
				logger.error(
						"Unable to get server listening port form configurations file",
						e);
				logger.info("Using default server port 9000");
			}
		}
		return serverPort;
	}
	
	public int getTimeOut() {
		String timeOutPropertyVal = config.getProperty(
				"CLIENT_SOCKET_TIMEOUT", String.valueOf(DEFAULT_TIME_OUT))
				.trim();
		logger.debug("Timeout property value obtained from configuration file is "
				+ timeOutPropertyVal);
		
		if (timeOut == 0) {
			try {
				timeOut = Integer.parseInt(timeOutPropertyVal);
			} catch (Exception e) {
				logger.error(
						"Unable to get server listening port form configurations file",
						e);
				logger.info("Using default server port 9000");
			}
		}
		return timeOut;
	}
}
