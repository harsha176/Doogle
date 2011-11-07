/**
 * 
 */
package edu.ncsu.csc573.project.common.messages;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.schema.Response;
import edu.ncsu.csc573.project.common.schema.Request;
/**
 *
 * @author krishna
 */
public abstract class ResponseMessage extends RequestMessage implements IResponse {
	private Logger logger;
	protected int errorid;
        protected String message;
        
        @Override
        public IStatus getStatus() {
            return new Status(errorid);
        }
        
        @Override 
        public String getMessage() {
            return message;
        }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ncsu.csc573.project.common.messages.IRequest#createRequest(edu.ncsu
	 * .csc573.project.common.messages.EnumOperationType,
	 * edu.ncsu.csc573.project.common.messages.IParameter)
	 */
	public void createResponse(EnumOperationType opType, IParameter parameter) {
		// TODO Auto-generated method stub
            super.createRequest(opType, parameter);
	}
        
        @Override
        public void createRequest(EnumOperationType opType, IParameter parameter) {
            logger.warn("Create response should be called instead of create request");
            createResponse(opType, parameter);
        }

        @Override
        public String getXML(Request req) throws Exception{
            throw new Exception("Invalid operation getXNL from request on response object");
        }
	
	public String getXML(Response req) throws Exception{
		logger = Logger.getLogger(RequestMessage.class);
		StringWriter reqXMLWriter = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(Response.class);
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

	public Response getResponse(String XML) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Response.class);
		Unmarshaller unMarsheller = context.createUnmarshaller();
		Response req = (Response)unMarsheller.unmarshal(new StringReader(XML));
		return req;
	}	
}
