package edu.ncsu.csc573.project.common.messages;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.schema.CommandRequestType;
import edu.ncsu.csc573.project.common.schema.FileParamType;
import edu.ncsu.csc573.project.common.schema.PublishType;
import edu.ncsu.csc573.project.common.schema.PublishTypeParams;
import edu.ncsu.csc573.project.common.schema.Request;

public class PublishRequestMessage extends RequestMessage {
	private PublishSearchParameter pubParam;
	private Logger logger;
	
	public IParameter getParameter() {
	    	return pubParam;
	}
	
	public void createRequest(EnumOperationType opType, IParameter parameter) {
		super.createRequest(opType, parameter);
		pubParam = (PublishSearchParameter)parameter;
	}
	
	public void setParameter(IParameter param) {
		if(param instanceof PublishSearchParameter) {
			pubParam = (PublishSearchParameter) param;
		} else {
			if(logger == null) {
				logger = Logger.getLogger(PublishRequestMessage.class);
			}
			logger.error("Invalid parameter");
		}
	}
	
	public String getRequestInXML() throws Exception {
		Request req = new Request();
		req.setId(BigInteger.valueOf(System.currentTimeMillis()));
		CommandRequestType publish = new CommandRequestType();
		PublishType publishType = new PublishType();
		PublishTypeParams lpt = new PublishTypeParams();
		
		pubParam.resetCounter();
		
		do {
			FileParamType fileParamType = new FileParamType();
			
			fileParamType.setId(pubParam.getParamCount());
			fileParamType.setFilename(pubParam.getParamValue(EnumParamsType.FILENAME).toString());
			fileParamType.setFiledigest(pubParam.getParamValue(EnumParamsType.FILEDIGEST).toString());
			fileParamType.setFilesize(pubParam.getParamValue(EnumParamsType.FILESIZE).toString());
			fileParamType.setAbstract(pubParam.getParamValue(EnumParamsType.ABSTRACT).toString());
			fileParamType.setIpaddress(pubParam.getParamValue(EnumParamsType.IPADDRESS).toString());
			pubParam.setNextParam();
			lpt.getFile().add(fileParamType);
		} while(pubParam.getParamCount() < pubParam.getSize());
	
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
			PublishTypeParams regparams = regType.getParams();
			List<FileParamType> fileList = regparams.getFile();
			IParameter param = new PublishSearchParameter();
			
			for(FileParamType fileParamType : fileList) {
				param.add(EnumParamsType.FILENAME, fileParamType.getFilename());
				param.add(EnumParamsType.FILEDIGEST, fileParamType.getFiledigest());
				param.add(EnumParamsType.FILESIZE, fileParamType.getFilesize());
				param.add(EnumParamsType.ABSTRACT, fileParamType.getAbstract());
				param.add(EnumParamsType.IPADDRESS, fileParamType.getIpaddress());
				param.add(EnumParamsType.DELIMITER, fileParamType.getId());
			}
			
			this.setOperationType(EnumOperationType.PUBLISH);
			this.setParameter(param);
		} catch(Exception e) {
			logger.error("Unable to parse request from string", e);
		}
	}
}
