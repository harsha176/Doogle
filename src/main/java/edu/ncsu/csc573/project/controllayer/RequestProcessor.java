package edu.ncsu.csc573.project.controllayer;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.IParameter;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;
import edu.ncsu.csc573.project.common.messages.LoginResponseMessage;
import edu.ncsu.csc573.project.common.messages.LogoutResponseMessage;
import edu.ncsu.csc573.project.common.messages.Parameter;
import edu.ncsu.csc573.project.common.messages.RegisterResponseMessage;

public class RequestProcessor {
	private Logger logger;
	
	public IResponse processRequest(IRequest req) {
		logger = Logger.getLogger(RequestProcessor.class);
		IResponse response = null;
		IParameter params = null;
		// sample responses
		switch (req.getOperationType()) { 
		case REGISTER: 
			response = new RegisterResponseMessage();
			params = new Parameter();
			params.add(EnumParamsType.STATUSCODE, new BigInteger(String.valueOf(0)));
			params.add(EnumParamsType.MESSAGE, req.getParameter().getParamValue(EnumParamsType.USERNAME) + " successfully registered");
			response.createResponse(EnumOperationType.REGISTERRESPONSE, params);
			break;
		case LOGIN:
			response = new LoginResponseMessage();
			params = new Parameter();
			params.add(EnumParamsType.STATUSCODE, new BigInteger(String.valueOf(0)));
			params.add(EnumParamsType.MESSAGE, req.getParameter().getParamValue(EnumParamsType.USERNAME) + " successfully logged in");
			response.createResponse(EnumOperationType.LOGINRESPONSE, params);
			break;
		case LOGOUT:
			response = new LogoutResponseMessage();
			params = new Parameter();
			params.add(EnumParamsType.STATUSCODE, new BigInteger(String.valueOf(0)));
			response.createResponse(EnumOperationType.LOGOUTRESPONSE, params);
		default :
			try {
			logger.error("Invalid request " + req.getRequestInXML());
			} catch(Exception e) {
				logger.error(" Invalid request: unable to create xml ");
			}
		}	
		return response;
	}
}