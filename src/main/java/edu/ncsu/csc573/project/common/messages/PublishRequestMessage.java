package edu.ncsu.csc573.project.common.messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import edu.ncsu.csc573.project.common.ByteOperationUtil;
import edu.ncsu.csc573.project.common.ConfigurationManager;
import edu.ncsu.csc573.project.common.schema.CommandRequestType;
import edu.ncsu.csc573.project.common.schema.FileParamType;
import edu.ncsu.csc573.project.common.schema.PublishType;
import edu.ncsu.csc573.project.common.schema.PublishTypeParams;
import edu.ncsu.csc573.project.common.schema.Request;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.DigestAdaptor;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.IDigest;

public class PublishRequestMessage extends RequestMessage {
	private Logger logger;
	private PublishTypeParams pubTypeParams;
	
	public List<FileParamType> getFileList() {
		return pubTypeParams.getFile();
	}
	
	public PublishRequestMessage() {
		super();
		pubTypeParams = new PublishTypeParams();
	} 
	
	/*
	public IParameter getParameter() {
		return pubParam;
	}
	
	public void createRequest(EnumOperationType opType, IParameter parameter) {
		super.createRequest(opType, parameter);
		pubParam = (PublishSearchParameter) parameter;
	}

	public void setParameter(IParameter param) {
		if (param instanceof PublishSearchParameter) {
			pubParam = (PublishSearchParameter) param;
		} else {
			if (logger == null) {
				logger = Logger.getLogger(PublishRequestMessage.class);
			}
			logger.error("Invalid parameter");
		}
	}
	*/
	public String getRequestInXML() throws Exception {
		Request req = new Request();
		req.setId(BigInteger.valueOf(System.currentTimeMillis()));
		CommandRequestType publish = new CommandRequestType();
		PublishType publishType = new PublishType();
		PublishTypeParams lpt = pubTypeParams;
		
		publishType.setParams(lpt);
		publish.setPublish(publishType);
		req.setCommand(publish);
		return getXML(req);
	}

	public void parseXML(String XML) {
		logger = Logger.getLogger(RegisterRequestMessage.class);
		try {
			Request req = getRequest(XML);

			CommandRequestType command = req.getCommand();
			PublishType regType = command.getPublish();
			pubTypeParams = regType.getParams();
			
			this.setOperationType(EnumOperationType.PUBLISH);
		} catch (Exception e) {
			logger.error("Unable to parse request from string", e);
		}
	}

	public static IRequest getPublishRequest() throws Exception {

		Logger logger = Logger.getLogger(PublishRequestMessage.class);
		File pubDir = ConfigurationManager.getInstance().getPublishDirectory();

		FilenameFilter textFilter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (name.endsWith(".txt"))
					return true;
				else
					return false;
			}
		};
		IDigest digestUtil = null;

		try {
			digestUtil = DigestAdaptor.getInstance();
		} catch (Exception e) {
			logger.error("Unable to initialize digest utility", e);
			return null;
		}

		List<File> files = Arrays.asList(pubDir.listFiles(textFilter));
		PublishRequestMessage PublishRequest = new PublishRequestMessage();
		List<FileParamType> FileParams = PublishRequest.getFileList();
		String localIPAddress = ConfigurationManager.getInstance()
				.getHostInterface();

		/*try {
			localIPAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("Unable to get IPAddress of the host interface", e);
		}*/
		for (File file : files) {

			FileParamType fpt = new FileParamType();
			fpt.setAbstract(getAbstract(file));
			fpt.setFiledigest(ByteOperationUtil
					.convertBytesToString(digestUtil.getDigest(file)));
			fpt.setFilesize(String.valueOf(file.length()));
			fpt.setFilename(file.getName());
			fpt.setIpaddress(localIPAddress);
			FileParams.add(fpt);
			
		}
		PublishRequest.createRequest(EnumOperationType.PUBLISH, null);
		return PublishRequest;
	}

	private static String getAbstract(File file) throws IOException {
		StringBuffer sb = new StringBuffer();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			sb.append(br.readLine());
			// sb.append(System.lineSeparator());
			sb.append(br.readLine());
			// sb.append(System.lineSeparator());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
