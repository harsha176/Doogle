package edu.ncsu.csc573.project.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigurationManager {
	public static final int DEFAULT_SERVER_PORT = 9000;
	public static final int DEFAULT_TIME_OUT = 2;
	public static final long DEFAULT_CLI_TIME_OUT = 60000;
	public static final int DEFAULT_BACK_LOG_COUNT = 10;
	
	private int serverPort;
	private int timeOut;
	private long cliTimeOut;
	private int backLogCount;
	private String trigramDataBase;

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
	
	private long getAsLong(String parameter, long default_value) {
		String timeOutPropertyVal = config.getProperty(
				parameter).trim();
		if(timeOutPropertyVal == null || timeOutPropertyVal.trim().equals("")) {
			logger.error("Unable to fetch "+ parameter + "for configuration file");
			logger.info("Using default value " + default_value + "for " + parameter);
			return default_value;
		}
		try {
			return Long.parseLong(timeOutPropertyVal);
		}catch (Exception e) {
			logger.error("The given parameter " + parameter + "is not a valid integer/long");
			logger.error("Please configure it as an integer value in configurations.xml file");
			return default_value;
		}
	}
	
	private int getAsInt(String parameter, int default_value) {
		String timeOutPropertyVal = config.getProperty(
				parameter).trim();
		if(timeOutPropertyVal == null || timeOutPropertyVal.trim().equals("")) {
			logger.error("Unable to fetch "+ parameter + "for configuration file");
			logger.info("Using default value " + default_value + "for " + parameter);
			return default_value;
		}
		try {
			return Integer.parseInt(timeOutPropertyVal);
		}catch (Exception e) {
			logger.error("The given parameter " + parameter + "is not a valid integer/long");
			logger.error("Please configure it as an integer value in configurations.xml file");
			return default_value;
		}
	}
	
	private String getAsString(String parameter, String default_value) {
		String timeOutPropertyVal = config.getProperty(
				parameter).trim();
		if(timeOutPropertyVal == null || timeOutPropertyVal.trim().equals("")) {
			logger.error("Unable to fetch "+ parameter + "for configuration file");
			logger.info("Using default value " + default_value + "for " + parameter);
			return default_value;
		}
		return timeOutPropertyVal;
	}
	
	public int getServerPort() {
		if (serverPort == 0) {
			serverPort = getAsInt("SERVER_LISTENING_PORT", DEFAULT_SERVER_PORT);
		}
		return serverPort;
	}
	
	public int getTimeOut() {
		if (timeOut == 0) {
			timeOut = getAsInt("CLIENT_SOCKET_TIMEOUT", DEFAULT_TIME_OUT);
		}
		return timeOut;
	}
	
	public long getCLITimeOut() {
		if (cliTimeOut == 0) {
			cliTimeOut = getAsLong("CLI_TIMEOUT", DEFAULT_CLI_TIME_OUT);
		}
		return cliTimeOut;
	}
	
	public int getBackLogCount() {
		if(backLogCount == 0) {
			backLogCount = getAsInt("BACK_LOG_COUNT", 10);
		}
		return backLogCount;
	}
	
	// get all the trigrams
	public String getTrigramDatabase() {
		if(trigramDataBase == null) {
			trigramDataBase = getAsString("TRIGRAM_DATABASE_FILE", "trigram.txt");
		}
		return trigramDataBase;
	}
}
