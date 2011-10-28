/**
 * 
 */
package edu.ncsu.csc573.project.common.messages;

/**
 * This interface abstracts Response object received from communication layer.
 * 
 * @author doogle-dev
 *
 */
public interface IResponse {
	/**
	 * This method returns Status of the request.
	 * 
	 * @see IStatus
	 * @return
	 */
	public IStatus getStatus();
	
	/**
	 * This method returns response id of the response.
	 * @return
	 */
	public long getResponseID();
	
	/**
	 * This method returns Message object for the response. 
	 * @return
	 * 
	 * @see IMessage
	 */
	public IMessage getMessage();
}
