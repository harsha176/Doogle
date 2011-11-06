/**
 * 
 */
package edu.ncsu.csc573.project.common.messages;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.schema.Request;
import java.util.List;

/**
 * @author doogle-dev
 * 
 */
public abstract class RequestMessage implements IRequest {

	private EnumOperationType operationType;
	private IParameter params;
	private Logger logger;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.common.messages.IRequest#createRequest(edu.ncsu
	 * .csc573.project.common.messages.EnumOperationType,
	 * edu.ncsu.csc573.project.common.messages.IParameter)
	 */
	public void createRequest(EnumOperationType opType, IParameter parameter) {
		// TODO Auto-generated method stub
		operationType = opType;
		params = parameter;
	}

	protected void setOperationType(EnumOperationType opType) {
		operationType = opType;
	}

	protected void setParameter(IParameter param) {
		params = param;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ncsu.csc573.project.common.messages.IRequest#getOperationType()
	 */
	public EnumOperationType getOperationType() {
		return operationType;
	}

	public IParameter getParameter() {
		return params;
	}

	// 
	public String getXML(Request req) throws Exception{
		logger = Logger.getLogger(RequestMessage.class);
		StringWriter reqXMLWriter = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(Request.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(req, reqXMLWriter);
		} catch (Exception e) {
				logger.error("Unable to parse request ", e);
				throw e;
			} finally {
				reqXMLWriter.close();
		}
		return reqXMLWriter.toString();
	}

	public Request getRequest(String XML) throws Exception {
		return getRequestFromGenXML(XML);
	}	
	
	public static IRequest createRequest(String XML) throws Exception {
		IRequest req = null;
		
		if(XML.indexOf("Register") != -1) {
			req = new RegisterRequestMessage();
			req.parseXML(XML);
		} /*else if(XML.indexOf("Login")){
			
		}*/
		return req;
		
	}
	
	private static Request getRequestFromGenXML(String XML) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Request.class);
		Unmarshaller unMarsheller = context.createUnmarshaller();
		Request req = (Request)unMarsheller.unmarshal(new StringReader(XML));
		return req;
	}
}
