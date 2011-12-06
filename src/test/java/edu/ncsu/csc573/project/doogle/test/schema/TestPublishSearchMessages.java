package edu.ncsu.csc573.project.doogle.test.schema;

import edu.ncsu.csc573.project.common.ByteOperationUtil;
import edu.ncsu.csc573.project.common.ConfigurationManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;
import edu.ncsu.csc573.project.common.messages.LoginRequestMessage;
import edu.ncsu.csc573.project.common.messages.PublishRequestMessage;
import edu.ncsu.csc573.project.common.messages.PublishSearchParameter;
import edu.ncsu.csc573.project.common.messages.RequestMessage;
import edu.ncsu.csc573.project.common.messages.ResponseMessage;
import edu.ncsu.csc573.project.common.messages.SearchResponseMessage;
import edu.ncsu.csc573.project.common.schema.FileParamType;
import edu.ncsu.csc573.project.common.schema.MatchMetricFileParamType;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.DigestAdaptor;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.IDigest;

public class TestPublishSearchMessages {

	@Test
	public void testPublishRequestMessageToXML() {

		try {
			IRequest PublishRequest = PublishRequestMessage.getPublishRequest();
			System.out.println(PublishRequest.getRequestInXML());
			Assert.assertTrue("Successfully parsed xml", true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(false);
			// e.printStackTrace();
		}
	}

	@Test
	public void testPublisRequestMessageParsing() throws Exception {
		InputStream is = ClassLoader
				.getSystemResourceAsStream("TestPublishRequest.xml");
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

		IRequest loginReq = RequestMessage.createRequest(sb.toString());
		loginReq.parseXML(sb.toString());
		Assert.assertEquals(EnumOperationType.PUBLISH,
				loginReq.getOperationType());

		PublishRequestMessage pubRequest = (PublishRequestMessage) loginReq;
		List<FileParamType> filelist = pubRequest.getFileList();
		Assert.assertEquals(3, filelist.size());
		// for(FileParamType fpt : filelist) {
		Assert.assertEquals("ip.txt", filelist.get(0).getFilename());
		Assert.assertEquals("Metallica.txt", filelist.get(1).getFilename());
		Assert.assertEquals("dance_of_death.txt", filelist.get(2).getFilename());

		// }
	}
	
	
	@Test
	public void testSearchResponse() {
		SearchResponseMessage srm = new SearchResponseMessage();
		List<MatchMetricFileParamType> results = srm.getFiles();
		
		/**
		 *  <File id="0">
                    <filename>ip.txt</filename>
                    <abstract>Classless IP addressingCSC/ECE 573 Internet Protocols</abstract>
                    <filesize>7482</filesize>
                    <ipaddress>127.0.0.1</ipaddress>
                    <filedigest></filedigest>
                    <MatchMetric>12.3</MatchMetric>
                </File>
		 */
		MatchMetricFileParamType ipFile = new MatchMetricFileParamType();
		ipFile.setAbstract("Classless IP addressingCSC/ECE 573 Internet Protocols");
		ipFile.setFiledigest("dsa");
		ipFile.setFilename("ip.txt");
		ipFile.setIpaddress("127.0.0.1");
		ipFile.setFilesize("7483");
		ipFile.setMatchMetric(0.67);
		
		MatchMetricFileParamType metallicaFile = new MatchMetricFileParamType();
		metallicaFile.setAbstract("Metallica at the 02 Arena in 2008");
		metallicaFile.setFiledigest("abc");
		metallicaFile.setFilename("Metallica.txt");
		metallicaFile.setIpaddress("127.0.0.1");
		metallicaFile.setFilesize("1489");
		metallicaFile.setMatchMetric(0.97);
		
		results.add(ipFile);
		results.add(metallicaFile);
		srm.createResponse(EnumOperationType.SEARCHRESPONSE, null);
		
		try {
			System.out.println(srm.getRequestInXML());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testSearchResponseParseXML() {
		InputStream is = ClassLoader
				.getSystemResourceAsStream("TestSearchResponse.xml");
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
		
		try {
			IResponse response = ResponseMessage.createResponse(sb.toString());
			Assert.assertEquals(EnumOperationType.SEARCHRESPONSE, response.getOperationType());
			SearchResponseMessage srm = (SearchResponseMessage)response;
			List<MatchMetricFileParamType> results = srm.getFiles();
			Assert.assertEquals(2, results.size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * private IRequest getPublishRequest() { IRequest PublishRequest = new
	 * PublishRequestMessage(); PublishSearchParameter publishParams = new
	 * PublishSearchParameter();
	 * 
	 * publishParams.add(EnumParamsType.FILENAME, "abc.txt");
	 * publishParams.add(EnumParamsType.FILEDIGEST, "0847");
	 * publishParams.add(EnumParamsType.FILESIZE, "20");
	 * publishParams.add(EnumParamsType.IPADDRESS, "10.12.2.1");
	 * publishParams.add(EnumParamsType.ABSTRACT, "This is a sample file");
	 * publishParams.add(EnumParamsType.DELIMITER, null);
	 * 
	 * publishParams.add(EnumParamsType.FILENAME, "abc.txt");
	 * publishParams.add(EnumParamsType.FILEDIGEST, "0847");
	 * publishParams.add(EnumParamsType.FILESIZE, "20");
	 * publishParams.add(EnumParamsType.IPADDRESS, "10.12.2.1");
	 * publishParams.add(EnumParamsType.ABSTRACT, "This is a sample file");
	 * publishParams.add(EnumParamsType.DELIMITER, null);
	 * 
	 * publishParams.add(EnumParamsType.FILENAME, "abc.txt");
	 * publishParams.add(EnumParamsType.FILEDIGEST, "0847");
	 * publishParams.add(EnumParmsType.FILESIZE, "20");
	 * publishParams.add(EnumParamsType.IPADDRESS, "10.12.2.1");
	 * publishParams.add(EnumParamsType.ABSTRACT, "This is a sample file");
	 * publishParams.add(EnumParamsType.DELIMITER, null);
	 * 
	 * publishParams.add(EnumParamsType.FILENAME, "abc.txt");
	 * publishParams.add(EnumParamsType.FILEDIGEST, "0847");
	 * publishParams.add(EnumParamsType.FILESIZE, "20");
	 * publishParams.add(EnumParamsType.IPADDRESS, "10.12.2.1");
	 * publishParams.add(EnumParamsType.ABSTRACT, "This is a sample file");
	 * publishParams.add(EnumParamsType.DELIMITER, null);
	 * 
	 * PublishRequest.createRequest(EnumOperationType.PUBLISH, publishParams);
	 * return PublishRequest; }
	 */
	/*
	 * private IRequest testPublishFolderRequest() throws Exception { URL url =
	 * ClassLoader.getSystemResource("samplePublishFolder");
	 * Assert.assertNotNull(url); System.out.println(url.getFile()); File pubDir
	 * = new File(url.getFile()); // File pubDir = //
	 * ConfigurationManager.getInstance().getPublishDirectory();
	 * Assert.assertEquals(pubDir.isDirectory(), true); FilenameFilter
	 * textFilter = new FilenameFilter() {
	 * 
	 * public boolean accept(File dir, String name) { if (name.endsWith(".txt"))
	 * return true; else return false; } }; List<File> files =
	 * Arrays.asList(pubDir.listFiles(textFilter)); IRequest PublishRequest =
	 * new PublishRequestMessage(); PublishSearchParameter publishParams = new
	 * PublishSearchParameter(); String localIPAddress =
	 * ConfigurationManager.getInstance().getHostInterface(); try {
	 * localIPAddress = InetAddress.getLocalHost().getHostAddress(); } catch
	 * (UnknownHostException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * IDigest digestUtil = null;
	 * 
	 * try { digestUtil = DigestAdaptor.getInstance(); } catch (Exception e) {
	 * //logger.error("Unable to initialize digest utility", e); return null; }
	 * for (File file : files) {
	 * 
	 * publishParams.add(EnumParamsType.FILENAME, file.getName());
	 * publishParams.add(EnumParamsType.FILEDIGEST,
	 * ByteOperationUtil.convertBytesToString(digestUtil.getDigest(file)));
	 * publishParams.add(EnumParamsType.FILESIZE,
	 * String.valueOf(file.length()));
	 * publishParams.add(EnumParamsType.IPADDRESS, localIPAddress);
	 * publishParams.add(EnumParamsType.ABSTRACT, getAbstract(file));
	 * publishParams.add(EnumParamsType.DELIMITER, null);
	 * 
	 * } PublishRequest.createRequest(EnumOperationType.PUBLISH, publishParams);
	 * return PublishRequest; }
	 * 
	 * public String getAbstract(File file) { StringBuffer sb = new
	 * StringBuffer();
	 * 
	 * BufferedReader br = null; try { br = new BufferedReader(new
	 * FileReader(file)); sb.append(br.readLine()); //
	 * sb.append(System.lineSeparator()); sb.append(br.readLine()); //
	 * sb.append(System.lineSeparator()); } catch (FileNotFoundException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } catch (IOException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } return
	 * sb.toString(); }
	 */

	/*
	 * @Test public void testPublishRequestMessageParseXML() { InputStream is =
	 * ClassLoader .getSystemResourceAsStream("TestPublishRequest.xml");
	 * BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 * 
	 * StringBuffer sb = new StringBuffer(); String temp; try { while ((temp =
	 * br.readLine()) != null) { sb.append(temp); } } catch (IOException e) {
	 * e.printStackTrace(); Assert.fail(); }
	 * 
	 * RequestMessage publishReq = new PublishRequestMessage();
	 * publishReq.parseXML(sb.toString());
	 * Assert.assertEquals(EnumOperationType.PUBLISH,
	 * publishReq.getOperationType()); PublishSearchParameter pubSearchParam =
	 * (PublishSearchParameter) publishReq.getParameter();
	 * pubSearchParam.resetCounter(); int i = 0;
	 * //while(pubSearchParam.getParamCount() < pubSearchParam.getSize()) {
	 * Assert
	 * .assertEquals(pubSearchParam.getParamValue(EnumParamsType.DELIMITER), i);
	 * Assert
	 * .assertEquals(pubSearchParam.getParamValue(EnumParamsType.FILENAME),
	 * "abc.txt"); pubSearchParam.setNextParam();
	 * Assert.assertEquals(pubSearchParam
	 * .getParamValue(EnumParamsType.DELIMITER), 1);
	 * Assert.assertEquals(pubSearchParam
	 * .getParamValue(EnumParamsType.FILENAME), "xyz.txt");
	 * pubSearchParam.setNextParam();
	 * Assert.assertEquals(pubSearchParam.getParamValue
	 * (EnumParamsType.DELIMITER), 2);
	 * Assert.assertEquals(pubSearchParam.getParamValue
	 * (EnumParamsType.IPADDRESS), "10.12.2.4");
	 * 
	 * }
	 */

}
