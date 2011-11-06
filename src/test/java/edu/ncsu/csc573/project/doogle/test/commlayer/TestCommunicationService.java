/**
 * 
 */
package edu.ncsu.csc573.project.doogle.test.commlayer;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import edu.ncsu.csc573.project.commlayer.CommunicationServiceFactory;
import edu.ncsu.csc573.project.commlayer.ICommunicationService;
import edu.ncsu.csc573.project.doogle.test.schema.TestRequestMessages;

/**
 * @author doogle-dev
 *
 */
public class TestCommunicationService {

	//private static ICommunicationService CommService;
	/**
	 * @throws java.lang.Exception
	 *
	 */
	
	@BeforeClass 
	public static void classSetup () throws Exception {
		
	}
	
	@Before
	public void setUp() throws Exception {
		//CommService = CommunicationServiceFactory.getInstance();
	}

	/*@Test
	public void testInitializeForUnknownHost() {
		ICommunicationService CommService = CommunicationServiceFactory.getInstance();
		try {
			CommService.initialize("192.168.1.1", null);
			Assert.assertFalse("Connected to invalid host", true);
		} catch (UnknownHostException e) {
			Assert.assertTrue("UnknownHostExceptionThrown", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	/*
	@Test
	public void testInitializeForKnownHost() {
		
		try {
			
			Assert.assertTrue("Connected to google.com", true);
		} catch (UnknownHostException e) {
			Assert.assertFalse("Unable to connect to google.com", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void testExecuteRequest() throws Exception{
		//Assert.fail("Execute request functionality not implemented"); 
		//ICommunicationService commService = CommunicationServiceFactory.getInstance();
		ICommunicationService CommService = CommunicationServiceFactory.getInstance();
		CommService.initialize("localhost", null);
		CommService.executeRequest(TestRequestMessages.getRegisterRequest());
		CommService.executeRequest(TestRequestMessages.getRegisterRequest());
		CommService.executeRequest(TestRequestMessages.getRegisterRequest());
		
		while(true) {
			Thread.sleep(1000);
		}
	}
	
	@Test
	public void testPublishRequest() {
		Assert.fail("Publish request functionality not implemented");
	}
	
	@Test 
	public void testSubscribeRequestTopic() {
		Assert.fail("Subscribe request topic functionality not implemented");
	}
	
	
	@Test
	public void testClose() {
		Assert.fail("Close functionality not implemented");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
