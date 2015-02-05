package lib.cisco.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;


import org.xml.sax.SAXException;

public class WebServiceValidation 
 {

	

	public static String invokeWebService(String webServiceURL, String requestFileContents)
			throws FileNotFoundException, Exception {
		PostMethod post = null;
		HttpClient client = new HttpClient();

		try {
			// Read the SOAP request from the file
			post = new PostMethod(webServiceURL);
			post.setRequestHeader("Accept", "application/soap+xml,application/dime,multipart/related,text/*");
			post.setRequestHeader("SOAPAction", "");

			// Request content will be retrieved directly from the input stream
			RequestEntity entity = new StringRequestEntity(requestFileContents.toString(), "text/xml", "utf-8");
			post.setRequestEntity(entity);

			// Returns a number indicating the status of response
			int result = client.executeMethod(post);
			String response = post.getResponseBodyAsString();
			return response;
		}

		finally {
			// Release current connection to the connection pool once you are
			post.releaseConnection();
		}
	}

	// Store the response in the disk
	public static void storeResponceInDisk(String actualResponse, String webServiceResponse, String encoding)
			throws IOException {

		File responseFile = new File(actualResponse);
		responseFile.createNewFile();
		OutputStreamWriter oSW = new OutputStreamWriter(new FileOutputStream(responseFile), encoding);
		oSW.write(webServiceResponse);
		oSW.flush();
		oSW.close();

	}

	// Compare the Actual Response with Expected Response
	public static boolean compareResponce(BufferedWriter writer, String actualString, String encoding,
			AppTestDTO appDTo) throws Exception {

		File file = new File(actualString);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		boolean flag=false;
		String line;
		boolean responceFlag = false;
		// AppTestDTO appDto = new AppTestDTO() ;

		String ScreenshotName = null;

		while ((line = br.readLine()) != null) {

			if (line.contains(appDTo.getExpectedXml())) {

				flag=true;
			} else {
				flag=false;
			}

			/*
			 * responceFlag = line.contains(appDTo.getExpectedXml()) ;
			 * if(responceFlag){
			 * 
			 * // responceFlag = true ; System.out.println("..Passed..");
			 * 
			 * //
			 * report.logMessage1(writer,appDTo.getExpectedXml(),actualString,
			 * "<b>Prerequisites</b> Value is matched", "Passed");
			 * report.logMessage(driver, writer,
			 * ScreenshotName,"WebServicde Validation", appDTo.getExpectedXml(),
			 * "Test Case Passed..Expected node is available in Responce XML..",
			 * "Passed");
			 * 
			 * }else if(!responceFlag){ // responceFlag = false ;
			 * System.out.println("..Failed.."); report.logMessage(driver,
			 * writer,
			 * ScreenshotName,"WebServicde Validation",appDTo.getExpectedXml(),
			 * "Test Case Failed..Expected node is not available in Responce XML.."
			 * , "Failed"); }
			 */
			// Assert.assertTrue(line.contains(appDTo.getExpectedXml()),
			// "Test Case Passed..Expected node is available in Responce XML..");

			// System.out.println("Test Case Passed..Expected node is available in Responce XML..");

		}
		br.close();
		fr.close();
		// return responceFlag;
		return flag;

	}

}