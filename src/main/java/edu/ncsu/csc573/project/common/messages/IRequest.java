/**
 * 
 */
package edu.ncsu.csc573.project.common.messages;

/**
 * This interface allows peer to createRequest objects.
 * @author doogle-dev
 *
 */
public interface IRequest {
	/**
	 * This method creates request based on operationType and parameter object.
	 * @param opType operation type of the request to be created.
	 * @param params List of parameters to be sent along with the request.
	 */
	void createRequest(EnumOperationType opType, IParameter parameter);
	
	/**
	 * This method allows to configure parameters in the request. 
	 * 
	 * @param paramType paramType to be configured 
	 * @param value paramType value
	 */
	void setParam(EnumParamsType paramType, String value);
	
	/**
	 * This method get value of the parameter based on the parameter type
	 * 
	 * @param paramType parameter for which value will be fetched
	 * @return
	 */
	String getParam(EnumParamsType paramType);
	
	/**
	 * This method gets the list of all the parameters configured in the request.
	 * 
	 * @return
	 */
	String getAllParams();
	
	/**
	 * This method allows user to get configured request in XML.
	 *  
	 * @return
	 */
	public String getRequestInXML();
}