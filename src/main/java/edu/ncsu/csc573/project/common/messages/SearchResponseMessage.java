package edu.ncsu.csc573.project.common.messages;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.schema.CommandResponseType;
import edu.ncsu.csc573.project.common.schema.MatchMetricFileParamType;
import edu.ncsu.csc573.project.common.schema.Response;
import edu.ncsu.csc573.project.common.schema.SearchResponseType;
import edu.ncsu.csc573.project.common.schema.SearchResponseTypeParams;

public class SearchResponseMessage extends ResponseMessage {
	private Logger logger;
	
	private SearchResponseTypeParams searchResponseParams;
	
	public SearchResponseMessage() {
		searchResponseParams = new SearchResponseTypeParams();
	}

	public List<MatchMetricFileParamType> getFiles() {
		return searchResponseParams.getFile();
	}
	
	public String getRequestInXML() throws Exception {
		Response req = new Response();
		req.setId(BigInteger.valueOf(System.currentTimeMillis()));
		CommandResponseType publish = new CommandResponseType();
		SearchResponseType publishType = new SearchResponseType();
		SearchResponseTypeParams lpt = searchResponseParams;
		
		publishType.setParams(lpt);
		publish.setSearchResponse(publishType);
		req.setCommand(publish);
	
		return getXML(req);
	}

	public void parseXML(String XML) {
		logger = Logger.getLogger(RegisterRequestMessage.class);
		try {
			Response req = getResponse(XML);
			
			CommandResponseType command = req.getCommand();
			SearchResponseType regType = command.getSearchResponse();
			searchResponseParams = regType.getParams();
			
			this.setOperationType(EnumOperationType.SEARCHRESPONSE);
		} catch(Exception e) {
			logger.error("Unable to parse request from string", e);
		}
	}

	/*public int getStatusOfResponse() {
		return status;
	}
	
	public String getStatusMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatusOfResponse(int status) {
		this.status = status;
	}*/
}
