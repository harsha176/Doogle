/**
 * 
 */
package edu.ncsu.csc573.project.viewlayer.cli;

import edu.ncsu.csc573.project.commlayer.CommunicationServiceFactory;
import edu.ncsu.csc573.project.commlayer.ICommunicationService;

/**
 * @author doogle-dev
 *
 */
public class BootStrap {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ICommunicationService service = CommunicationServiceFactory.getInstance();
		service.initialize("localhost", null);
	}
}
