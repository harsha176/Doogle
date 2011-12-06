package edu.ncsu.csc573.project.common.messages;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.ByteOperationUtil;
import edu.ncsu.csc573.project.common.schema.CommandRequestType;
import edu.ncsu.csc573.project.common.schema.Request;
import edu.ncsu.csc573.project.common.schema.UnpublishType;
import edu.ncsu.csc573.project.common.schema.UnpublishTypeParams;

public class UnPublishRequestMessage extends RequestMessage {
	private Logger logger = Logger.getLogger(UnPublishRequestMessage.class);
	
	public String getRequestInXML() throws Exception {
		Request req = new Request();
		CommandRequestType unPublishType = new CommandRequestType();
		UnpublishType loginType = new UnpublishType();
		UnpublishTypeParams lpt = new UnpublishTypeParams();

		lpt.setFilename(((getParameter().getParamValue(EnumParamsType.FILENAME)
				.toString())));
		lpt.setFiledigest(ByteOperationUtil
				.convertBytesToString((byte[]) getParameter().getParamValue(
						EnumParamsType.FILEDIGEST)));

		loginType.setParams(lpt);
		unPublishType.setUnpublish(loginType);
		req.setCommand(unPublishType);
		return getXML(req);
	}

	public void parseXML(String XML) {
		try {
			Request req = getRequest(XML);

			CommandRequestType command = req.getCommand();
			UnpublishType loginType = command.getUnpublish();
			UnpublishTypeParams loginparams = loginType.getParams();
			IParameter param = new Parameter();
			param.add(EnumParamsType.FILENAME, loginparams.getFilename());
			param.add(EnumParamsType.FILEDIGEST, ByteOperationUtil
					.convertStringToBytes(loginparams.getFiledigest()));

			this.setOperationType(EnumOperationType.UNPUBLISH);
			this.setParameter(param);
		} catch (Exception e) {
			logger.error("Unable to parse request from string", e);
		}

	}

}
