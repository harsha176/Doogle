/**
 * 
 */
package edu.ncsu.csc573.project.commlayer;

import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;

/**
 * This class is a factory class for configuring various implementations of
 * CommunicationService instance
 * 
 * @author doogle-dev
 * 
 */
public class CommunicationServiceFactory {
	/**
	 * This method is used to get an instance of ICommunicationService object.
	 * 
	 * @return
	 */
	public static ICommunicationService getInstance() {
		//Stub implementation
		return new ICommunicationService() {

			public void initialize(String BootStrapServer,
					IPublishHandler aPublishHandler) {
				// TODO Auto-generated method stub

			}

			public IResponse executeRequest(IRequest request) {
				// TODO Auto-generated method stub
				return null;
			}

			public void close() {
				// TODO Auto-generated method stub

			}

			public void publishRequest(IRequest request,
					IResponseListener listener) {
				// TODO Auto-generated method stub

			}

			public void subscribeRequestTopic(EnumOperationType operationType,
					IRequestListener reqListener) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
