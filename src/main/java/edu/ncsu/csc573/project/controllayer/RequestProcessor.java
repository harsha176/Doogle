package edu.ncsu.csc573.project.controllayer;

import edu.ncsu.csc573.project.common.messages.ChangePasswordResponseMessage;
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
import edu.ncsu.csc573.project.common.messages.PublishResponseMessage;
import edu.ncsu.csc573.project.common.messages.RegisterResponseMessage;
//import edu.ncsu.csc573.project.controllayer.usermanagement.IUsersManager;
//import edu.ncsu.csc573.project.controllayer.usermanagement.User;

public class RequestProcessor {

    private Logger logger;
    //private IUsersManager usermanager;

    public RequestProcessor() {
        /*try {
        usermanager = IUsersManager.getInstance();
        } catch (Exception e) {
        logger.error("Unable to initialize UserManager module", e);
        }*/
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
                /*User newUser = new User();
                newUser.setUsername(req.getParameter().getParamValue(EnumParamsType.USERNAME).toString());
                newUser.setPassword(req.getParameter().getParamValue(EnumParamsType.PASSWORD).toString());
                newUser.setFirstName(req.getParameter().getParamValue(EnumParamsType.PASSWORD).toString());
                usermanager.addUser(user)*/
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
            case CHANGEPASSWORD:
                response = new ChangePasswordResponseMessage();
                params = new Parameter();
                params.add(EnumParamsType.STATUSCODE, new BigInteger(String.valueOf(0)));
                params.add(EnumParamsType.MESSAGE, "Password successfully updated");
                response.createResponse(EnumOperationType.CHANGEPASSWORDRESPONSE, params);
                break;
            case PUBLISH:
                response = new PublishResponseMessage();
                params = new Parameter();
                params.add(EnumParamsType.STATUSCODE, new BigInteger(String.valueOf(0)));
                params.add(EnumParamsType.MESSAGE, "Successfully published folder on server");
                response.createResponse(EnumOperationType.PUBLISHRESPONSE, params);
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
