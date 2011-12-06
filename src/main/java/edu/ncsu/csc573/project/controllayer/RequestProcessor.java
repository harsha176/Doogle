package edu.ncsu.csc573.project.controllayer;

import edu.ncsu.csc573.project.common.ByteOperationUtil;
import edu.ncsu.csc573.project.common.messages.ChangePasswordResponseMessage;
import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.messages.ACKResponse;
import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.ForgotPWDResponseMessage;
import edu.ncsu.csc573.project.common.messages.IParameter;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;
import edu.ncsu.csc573.project.common.messages.LoginResponseMessage;
import edu.ncsu.csc573.project.common.messages.LogoutResponseMessage;
import edu.ncsu.csc573.project.common.messages.Parameter;
import edu.ncsu.csc573.project.common.messages.PublishRequestMessage;
import edu.ncsu.csc573.project.common.messages.PublishResponseMessage;
import edu.ncsu.csc573.project.common.messages.RegisterResponseMessage;
import edu.ncsu.csc573.project.common.messages.SearchResponseMessage;
import edu.ncsu.csc573.project.common.schema.MatchMetricFileParamType;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.HashSpaceManagerFactory;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.IHashSpaceManager;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.Query;
import edu.ncsu.csc573.project.controllayer.usermanagement.IUsersManager;
import edu.ncsu.csc573.project.controllayer.usermanagement.User;
import edu.ncsu.csc573.project.controllayer.usermanagement.UserManagementException;

public class RequestProcessor {

	private Logger logger;
	private IUsersManager usermanager;
	private IHashSpaceManager hashSpaceManager;

	public RequestProcessor() {
		try {
			usermanager = IUsersManager.getInstance();
			hashSpaceManager = HashSpaceManagerFactory.getInstance();
		} catch (Exception e) {
			logger.error("Unable to initialize UserManager module", e);
		}
	}

	public IResponse processRequest(IRequest req) {
		logger = Logger.getLogger(RequestProcessor.class);
		IResponse response = null;
		IParameter params = null;
		// sample responses
		switch (req.getOperationType()) {
		case REGISTER:
			/*
			 * call usermanager
			 */
			User newUser = new User();
			newUser.setUsername(req.getParameter()
					.getParamValue(EnumParamsType.USERNAME).toString());
			newUser.setPassword(req.getParameter()
					.getParamValue(EnumParamsType.PASSWORD).toString());
			newUser.setFirstName(req.getParameter()
					.getParamValue(EnumParamsType.FIRSTNAME).toString());
			newUser.setLastName(req.getParameter()
					.getParamValue(EnumParamsType.LASTNAME).toString());
			newUser.setEmailID(req.getParameter()
					.getParamValue(EnumParamsType.EMAIL_ID).toString());

			try {
				response = new RegisterResponseMessage();
				params = new Parameter();
				usermanager.addUser(newUser);
				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(0)));
				params.add(EnumParamsType.MESSAGE, req.getParameter()
						.getParamValue(EnumParamsType.USERNAME)
						+ " successfully registered");
			} catch (UserManagementException e1) {
				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(e1.getStatus())));
				params.add(EnumParamsType.MESSAGE, e1.getMessage());
			}

			response.createResponse(EnumOperationType.REGISTERRESPONSE, params);
			break;
		case LOGIN:
			response = new LoginResponseMessage();
			params = new Parameter();
			try {
				usermanager.userLogin(
						req.getParameter()
								.getParamValue(EnumParamsType.USERNAME)
								.toString(),
						req.getParameter()
								.getParamValue(EnumParamsType.PASSWORD)
								.toString());
				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(0)));
				params.add(EnumParamsType.MESSAGE, req.getParameter()
						.getParamValue(EnumParamsType.USERNAME)
						+ " successfully logged in");
			} catch (UserManagementException e1) {
				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(e1.getStatus())));
				params.add(EnumParamsType.MESSAGE, e1.getMessage());
			}
			response.createResponse(EnumOperationType.LOGINRESPONSE, params);
			break;
		case LOGOUT:
			response = new LogoutResponseMessage();
			params = new Parameter();
			String username = req.getParameter()
					.getParamValue(EnumParamsType.USERNAME).toString();
			try {
				usermanager.userLogout(req.getParameter()
						.getParamValue(EnumParamsType.USERNAME).toString());

				//hashSpaceManager.removePublishedFiles(username);
				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(0)));

			} catch (UserManagementException e1) {
				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(e1.getStatus())));
				params.add(EnumParamsType.MESSAGE, e1.getMessage());

			}
			response.createResponse(EnumOperationType.LOGOUTRESPONSE, params);
			break;
		case CHANGEPASSWORD:
			response = new ChangePasswordResponseMessage();
			params = new Parameter();

			try {
				usermanager.changePassword(
						req.getParameter()
								.getParamValue(EnumParamsType.USERNAME)
								.toString(),
						req.getParameter()
								.getParamValue(EnumParamsType.PASSWORD)
								.toString(),
						req.getParameter()
								.getParamValue(EnumParamsType.NEWPASSWORD)
								.toString());

				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(0)));
				params.add(EnumParamsType.MESSAGE,
						"Password sent to your mail account");
			} catch (UserManagementException e1) {

				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(e1.getStatus())));
				params.add(EnumParamsType.MESSAGE, e1.getMessage());
			}
			response.createResponse(EnumOperationType.FORGOTPASSWORD, params);
			break;
		case FORGOTPASSWORD:
			response = new ForgotPWDResponseMessage();
			params = new Parameter();

			try {
				usermanager.forgotPassword(req.getParameter()
						.getParamValue(EnumParamsType.USERNAME).toString());

				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(0)));
				params.add(EnumParamsType.MESSAGE,
						"Password successfully updated");
			} catch (UserManagementException e1) {

				params.add(EnumParamsType.STATUSCODE,
						new BigInteger(String.valueOf(e1.getStatus())));
				params.add(EnumParamsType.MESSAGE, e1.getMessage());
			}
			response.createResponse(EnumOperationType.FORGOTPASSWORD, params);
			break;
		case PUBLISH:
			logger.debug("Processing publish request");
			hashSpaceManager.handlePublishRequest((PublishRequestMessage) req);
			response = new PublishResponseMessage();
			params = new Parameter();
			params.add(EnumParamsType.STATUSCODE,
					new BigInteger(String.valueOf(0)));
			params.add(EnumParamsType.MESSAGE,
					"Successfully published folder on server");
			response.createResponse(EnumOperationType.PUBLISHRESPONSE, params);
			break;

		case SEARCH:
			logger.debug("Processing search request");
			SearchResponseMessage srm = new SearchResponseMessage();
			List<MatchMetricFileParamType> searchResponseparams;
			String query_string = req.getParameter()
					.getParamValue(EnumParamsType.SEARCHKEY).toString();
			searchResponseparams = hashSpaceManager.search(new Query(
					query_string));
			srm.getFiles().addAll(searchResponseparams);
			srm.createResponse(EnumOperationType.SEARCHRESPONSE, null);
			response = srm;
			logger.info("Done processing search request : searchResults count : "
					+ searchResponseparams.size());
			break;
		case DOWNLOADUPDATE:
			//logger.info("implemented");
			hashSpaceManager.updateDownloadCount(ByteOperationUtil
					.convertBytesToString(((byte[]) req.getParameter()
							.getParamValue(EnumParamsType.FILEDIGEST))), req
					.getParameter().getParamValue(EnumParamsType.FILENAME)
					.toString());
			logger.info("Done processing download update request");
			response = new ACKResponse();
			IParameter ackParams = new Parameter();
			ackParams.add(EnumParamsType.STATUSCODE, BigInteger.ZERO);
			ackParams.add(EnumParamsType.MESSAGE, "Successfully updated download count for file ");
			response.createResponse(EnumOperationType.ACKRESPONSE, ackParams);
			break;
		case UNPUBLISH:
			logger.info("Executing unpublish files");
			hashSpaceManager.removePublishedFiles(req
					.getParameter().getParamValue(EnumParamsType.FILENAME)
					.toString(), ByteOperationUtil
					.convertBytesToString(((byte[]) req.getParameter()
							.getParamValue(EnumParamsType.FILEDIGEST))));
			logger.info("Done with unpublishing the files");
                        response = new ACKResponse();
			IParameter ackParam = new Parameter();
			ackParam.add(EnumParamsType.STATUSCODE, BigInteger.ZERO);
			ackParam.add(EnumParamsType.MESSAGE, "Successfully unpublished file");
			response.createResponse(EnumOperationType.ACKRESPONSE, ackParam);
		break;
		default:
			try {
				logger.error("Invalid request " + req.getRequestInXML());
			} catch (Exception e) {
				logger.error(" Invalid request: unable to create xml ");
			}
		}
		return response;
	}
}
