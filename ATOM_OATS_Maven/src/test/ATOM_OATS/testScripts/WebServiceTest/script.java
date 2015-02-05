import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lib.cisco.util.AppTestDTO;
import lib.cisco.util.ApplicationDB;
import lib.cisco.util.OutputBean;
import lib.cisco.util.variables;

import lib.cisco.util.Report;

import lib.cisco.util.WebServiceValidation;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.browser.api.BrowserSettings.BrowserType;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import lib.*;

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	Calendar startTime;
	Calendar endTime;	
	String strtDateTime;
	String endDateTime;
	BufferedWriter writer=null;
	public static long tcStartTime;
	public static long tcendTime;
	
	String ScreenshotName="";
	private Report report = new Report();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@FunctionLibrary("ApplicationDBUtility") lib.cisco.util.ApplicationDBUtility applicationDBUtility;
	public void initialize() throws Exception {
	}
		
	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}
	public void test() throws FileNotFoundException, Exception {
		
		/*---------------------ATOM CODE-----------------------------------*/
		System.out.println("variables in EBS_SCM_Scenario1_Login:"+variables.getTableName()+":"+variables.getReleaseId()+":"+variables.getAppId());
	 
		AppTestDTO appdata=null;
		ApplicationDB AppDBobj=new ApplicationDB();
		appdata=AppDBobj.getTestDataForDataId();
		OutputBean ob=new OutputBean();
		
		
		
			try
			{
				ScreenshotName=(appdata.getTestClassName()+"_"+appdata.getTestMethodName()+"-"+appdata.getDataId());
				writer=report.createlogfile(appdata.getTestMethodName(),appdata.getDataId());
				
			tcStartTime=System.currentTimeMillis();
			startTime= Calendar.getInstance();			
			strtDateTime= formatter.format(startTime.getTime());
			
			System.out.println("start time:"+tcStartTime);
			
			
			
			System.out.println("BrowserId:"+browser.getBrowserProcessID());
			
			// Web service URL
			final String wsURL = "http://173.39.25.253:7001/SoapUIServiceTest/SoapClassService?wsdl";

			// Request XML path
			System.out.println("input xml:" + appdata.getInputXml());
			final String requestXMLPath = appdata.getInputXml();

			// Expected response path
			final String expectedNode = appdata.getExpectedXml();
			System.out.println("Expected XML Data.." + expectedNode);

			// Actual response path
			final String actualXMLPath = "C:\\OracleATS\\OFT\\Sample_ATOM_OATS\\WebServiceXml\\Actualrespone-add.xml";

			// Encoding
			final String encoding = "UTF-8";
			// Invoke the web service
			String webServiceResponse = WebServiceValidation.invokeWebService(wsURL, requestXMLPath);
			System.out.println("Response: " + webServiceResponse);

			// Store the response in the disk
			WebServiceValidation.storeResponceInDisk(actualXMLPath, webServiceResponse, encoding);

			// Compare the responses

			boolean flag=WebServiceValidation.compareResponce(writer, actualXMLPath, encoding, appdata);
			if(flag==true)
			{
				ob.setStatus("Passed");
				report.logMessage( writer, ScreenshotName, "WebServicde Validation", appdata.getExpectedXml(),
					"Test Case Passed..Expected node is available in Responce XML..", "Passed");
			}
			else
			{
				report.logMessage( writer, ScreenshotName, "WebServicde Validation", appdata.getExpectedXml(),
					"Test Case Failed..Expected node is not available in Responce XML..", "Failed");
				ob.setStatus("Failed");
			}
			/*---------------------ATOM CODE-----------------------------------*/
			}
			catch(Exception e)
			{
				System.out.println("catch");
				e.printStackTrace();
				String errmsg=e.getMessage();
				if(errmsg.length() > 500){
					errmsg=errmsg.substring(0,500);
					
					
			}
		ob.setErrorMsg(errmsg);
			ob.setStatus("Failed");
				
				report.logMessage(writer,ScreenshotName,"Test Execution Failed","Exception Occurred", "Exception Occurred "+errmsg, "Failed");
			}
			
			
				finally
				{
					//browser.close();
					System.out.println("finally");
					
					tcendTime=System.currentTimeMillis();
					endTime = Calendar.getInstance();				
					endDateTime = formatter.format(endTime.getTime());	
					System.out.println(endDateTime);
				AppDBobj.ResultMapAndDBUpdate(ob, variables.getTrackId(), Integer.parseInt(variables.getReleaseId()),Integer.parseInt(variables.getAppId()), strtDateTime, endDateTime, appdata);
					
					report.after(writer,tcStartTime,tcendTime,appdata.getOsType(),appdata.getBrowserType(),appdata.getBrowserVersion());		
					report.closewriter(writer);
					
					
				}
				/*---------------------ATOM CODE-----------------------------------*/
	
		}
	public void finish() throws Exception {
	}
}
