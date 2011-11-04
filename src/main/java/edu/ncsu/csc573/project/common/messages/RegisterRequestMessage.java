package edu.ncsu.csc573.project.common.messages;

import java.math.BigInteger;
import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.schema.CommandType;
import edu.ncsu.csc573.project.common.schema.RegisterParamsType;
import edu.ncsu.csc573.project.common.schema.RegisterType;
import edu.ncsu.csc573.project.common.schema.Request;

public class RegisterRequestMessage extends RequestMessage {
	private Logger logger;

	public String getRequestInXML() throws Exception {

		logger = Logger.getLogger(RegisterParamsType.class);

		Request req = new Request();
		req.setId(BigInteger.valueOf(System.currentTimeMillis()));
		CommandType register = new CommandType();
		RegisterType rt = new RegisterType();
		RegisterParamsType rpt = new RegisterParamsType();
		
		rpt.setUsername((getParameter().getParamValue(EnumParamsType.USERNAME).toString()));
		rpt.setEmailId(getParameter().getParamValue(EnumParamsType.EMAIL_ID).toString());
		rpt.setPassword(getParameter().getParamValue(EnumParamsType.PASSWORD).toString());
		rpt.setDesignation(getParameter().getParamValue(EnumParamsType.DESIGNATION).toString());

		rt.setParmas(rpt);
		register.setRegister(rt);
		req.setCommand(register);

		return getXML(req);
	}

	public void parseXML(String XML) {
		logger = Logger.getLogger(RegisterRequestMessage.class);
		try {
			Request req = getRequest(XML);
			
			CommandType command = req.getCommand();
			RegisterType regType = command.getRegister();
			RegisterParamsType regparams = regType.getParmas();
			IParameter param = new Parameter();
			param.add(EnumParamsType.USERNAME, regparams.getUsername());
			param.add(EnumParamsType.PASSWORD, regparams.getPassword());
			param.add(EnumParamsType.EMAIL_ID, regparams.getEmailId());
			param.add(EnumParamsType.DESIGNATION, regparams.getDesignation());
			
			this.setOperationType(EnumOperationType.REGISTER);
			this.setParameter(param);
		} catch(Exception e) {
			logger.error("Unable to parse request from string", e);
		}
	}
}