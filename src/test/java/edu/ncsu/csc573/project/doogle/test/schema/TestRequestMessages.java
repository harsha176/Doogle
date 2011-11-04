package edu.ncsu.csc573.project.doogle.test.schema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import junit.framework.Assert;

import org.junit.Test;
import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.IParameter;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.Parameter;
import edu.ncsu.csc573.project.common.messages.RegisterRequestMessage;
import edu.ncsu.csc573.project.common.messages.RequestMessage;

/*
 import java.util.ArrayList;
 import javax.xml.bind.JAXBException;
 import edu.ncsu.csc573.project.common.messages.EnumOperationType;
 import edu.ncsu.csc573.project.common.messages.EnumParamsType;
 import edu.ncsu.csc573.project.common.messages.IParameter;
 import edu.ncsu.csc573.project.common.messages.IRequest;
 */

public class TestRequestMessages {

	/**
	 * @param args
	 * @throws JAXBException
	 */
	@Test
	public void testRegisterRequestMessageToXML() {
		IRequest regRequest = getRegisterRequest();

		try {
			System.out.println(regRequest.getRequestInXML());
			Assert.assertTrue("Successfully parsed xml", true);
		} catch (Exception e) {
			Assert.fail();
			// e.printStackTrace();
		}
	}

	public static IRequest getRegisterRequest() {
		IRequest regRequest = new RegisterRequestMessage();
		IParameter Regparams = new Parameter();
		Regparams.add(EnumParamsType.USERNAME, "harsha176");
		Regparams.add(EnumParamsType.PASSWORD, "abcdef");
		Regparams.add(EnumParamsType.EMAIL_ID, "harsha176@gmail.com");
		Regparams.add(EnumParamsType.DESIGNATION, "guest");
		regRequest.createRequest(EnumOperationType.REGISTER, Regparams);
		return regRequest;
	}
	
	@Test
	public void testRegisterRequestMessageParseXML() {
		InputStream is = ClassLoader
				.getSystemResourceAsStream("TestSampleRegisterRequest.xml");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();
		String temp;
		try {
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}

		RequestMessage regReq = new RegisterRequestMessage();
		regReq.parseXML(sb.toString());
		Assert.assertEquals(EnumOperationType.REGISTER,
				regReq.getOperationType());
		Assert.assertEquals("harsha176",
				regReq.getParameter().getParamValue(EnumParamsType.USERNAME)
						.toString());
		Assert.assertEquals("harsha176@gmail.com", regReq.getParameter()
				.getParamValue(EnumParamsType.EMAIL_ID).toString());
		Assert.assertEquals("abcdef",
				regReq.getParameter().getParamValue(EnumParamsType.PASSWORD)
						.toString());
		Assert.assertEquals("guest",
				regReq.getParameter().getParamValue(EnumParamsType.DESIGNATION)
						.toString());
	}

}
