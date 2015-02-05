import static lib.cisco.util.DBUtill.resultMap;


import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lib.cisco.util.AppTestDTO;
import lib.cisco.util.ApplicationDB;
import lib.cisco.util.DBUtill;
import lib.cisco.util.OutputBean;
import lib.cisco.util.Report;
import lib.cisco.util.variables;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.browser.api.BrowserSettings.BrowserType;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import static lib.cisco.util.Report.projectPath;
import lib.*;
public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	String status="";
	String error_message="";
	String RegLink="";
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
	@FunctionLibrary("ApplicationUtility") lib.cisco.util.ApplicationUtility applicationUtility;
	@FunctionLibrary("CommonUtility") lib.cisco.util.CommonUtility commonUtility;
	@FunctionLibrary("ApplicationDBUtility") lib.cisco.util.ApplicationDBUtility applicationDBUtility;
	//projectPath=commonUtility.getCurrentFolder();
	public void initialize() throws Exception {
	}
		
	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}
	/*
	 * Method : EBS_SCM_Navigate_AUSOMSuperUserSalesOrder
	 * This method is used to navigate to the AUS OM SuperUser and open the Sales Order form
	 * The execution results will be updated in Execution table
	 */
	public void EBS_SCM_Navigate_AUSOMSuperUserSalesOrder() throws Exception{
		
		System.out.println("variables in EBS_SCM_Navigate_AUSOMSuperUserSalesOrder:"+variables.getTableName()+":"+variables.getReleaseId()+":"+variables.getAppId());
		AppTestDTO appdata=new AppTestDTO();
		
		ApplicationDB AppDBobj=new ApplicationDB();
		System.out.println("AppDBobj:"+AppDBobj);
		appdata=AppDBobj.getTestDataForDataId();
		System.out.println("appdata:"+appdata);
			OutputBean ob=new OutputBean();
			
			try
			{
				ScreenshotName=(appdata.getTestClassName()+"_"+appdata.getTestMethodName()+"-"+appdata.getDataId());
				writer=report.createlogfile(appdata.getTestMethodName(),appdata.getDataId());
			tcStartTime=System.currentTimeMillis();
			startTime= Calendar.getInstance();			
			strtDateTime= formatter.format(startTime.getTime());
		
			System.out.println("start time:"+tcStartTime);
		
			if(appdata.getBrowserType().equalsIgnoreCase("iexplorer") || appdata.getBrowserType().equalsIgnoreCase("IE") || appdata.getBrowserType().equalsIgnoreCase("InternetExplorer"))
			{
				browser.setBrowserType(BrowserType.InternetExplorer);
			}
			else if(appdata.getBrowserType().equalsIgnoreCase("ff") || appdata.getBrowserType().equalsIgnoreCase("firefox"))
			{
				browser.setBrowserType(BrowserType.Chrome);
			}
			browser.launch();
			Thread.sleep(10000);
			//ob.setStatus("Passed");
			System.out.println("Navigation");
				ob=applicationUtility.userLogin(ob, writer, appdata);
			/*}
			catch(Exception e)
			{
				System.out.println("catch for userLogin");
				ob.setStatus("Failed");
				e.printStackTrace();
				String errmsg=e.getMessage();
				
				if(errmsg.length() > 500){
					errmsg=errmsg.substring(0,500);
				}
				ob.setErrorMsg(errmsg);
				
				ob.setStatus("Failed");
				report.logMessage(writer,ScreenshotName,"Login To Application","Oracle Applications home page is displayed", "Exception Occurred: "+errmsg, "Failed");
					
					
			}
			finally
			{*/
			if(ob.getStatus().equalsIgnoreCase("Passed"))
			{
				ob=applicationUtility.NavigateToAusOMSuperUser(ob, writer, appdata);
			}
		//	}
			}
			catch(Exception e)
			{
				System.out.println("catch for Main try");
				
				//e.printStackTrace();
				String errmsg=e.getMessage();
				
				if(errmsg.length() > 500){
					errmsg=errmsg.substring(0,500);
					
					
			}
				ob.setErrorMsg(errmsg);
				
				ob.setStatus("Failed");
				report.logMessage(writer,ScreenshotName,"Navigate to Aus OM Super user","Sales order page opened", "Exception Occurred: "+errmsg, "Failed");
			}
				finally
				{
					browser.close();
					System.out.println("finally");
					tcendTime=System.currentTimeMillis();
					endTime = Calendar.getInstance();				
					endDateTime = formatter.format(endTime.getTime());	
					System.out.println(endDateTime);
					AppDBobj.ResultMapAndDBUpdate(ob, variables.getTrackId(), Integer.parseInt(variables.getReleaseId()),Integer.parseInt(variables.getAppId()), strtDateTime, endDateTime, appdata);
				report.after(writer,tcStartTime,tcendTime,appdata.getOsType(),appdata.getBrowserType(),appdata.getBrowserVersion());		
					report.closewriter(writer);
					
					
				}
			
		
	
	}
	/*
	 * Method : EBS_SCM_Navigate_OMSuperUserSalesOrder
	 * This method is used to navigate to the OM SuperUser and open the Sales Order form
	 * The execution results will be updated in Execution table
	 */
	public void  EBS_SCM_Navigate_OMSuperUserSalesOrder() throws Exception{
		System.out.println("EBS_SCM_Navigate_OMSuperUserSalesOrder:"+variables.getTableName()+":"+variables.getReleaseId()+":"+variables.getAppId());
		AppTestDTO appdata=new AppTestDTO();
		
		ApplicationDB AppDBobj=new ApplicationDB();
		System.out.println("AppDBobj:"+AppDBobj);
		appdata=AppDBobj.getTestDataForDataId();
		System.out.println("appdata:"+appdata.getDataId());
			OutputBean ob=new OutputBean();
			
			try
			{
				ScreenshotName=(appdata.getTestClassName()+"_"+appdata.getTestMethodName()+"-"+appdata.getDataId());
				System.out.println("Screenshot:"+ScreenshotName);
				writer=report.createlogfile(appdata.getTestMethodName(),appdata.getDataId());
			tcStartTime=System.currentTimeMillis();
			startTime= Calendar.getInstance();			
			strtDateTime= formatter.format(startTime.getTime());
			
			System.out.println("start time:"+tcStartTime);
		
			if(appdata.getBrowserType().equalsIgnoreCase("iexplorer") || appdata.getBrowserType().equalsIgnoreCase("IE") || appdata.getBrowserType().equalsIgnoreCase("InternetExplorer"))
			{
				browser.setBrowserType(BrowserType.InternetExplorer);
			}
			else if(appdata.getBrowserType().equalsIgnoreCase("ff") || appdata.getBrowserType().equalsIgnoreCase("firefox"))
			{
				browser.setBrowserType(BrowserType.Firefox);
			}
			System.out.println("Browser launched for DAta:"+appdata.getDataId());
			browser.launch();
			Thread.sleep(10000);
			//ob.setStatus("Passed");
			
			ob=applicationUtility.userLogin(ob, writer, appdata);
			
			/*catch(Exception e)
			{
				System.out.println("catch for userLogin");
				
				e.printStackTrace();
				String errmsg=e.getMessage();
				
				if(errmsg.length() > 500){
					errmsg=errmsg.substring(0,500);
				}
				ob.setErrorMsg(errmsg);
				
				ob.setStatus("Failed");
				report.logMessage(writer,ScreenshotName,"Login To Application","Oracle Applications home page is displayed", "Exception Occurred: "+errmsg, "Failed");
					
					
			}
			finally
			{*/
			System.out.println("After Login call:"+ob.getStatus());
			if(ob.getStatus().equalsIgnoreCase("Passed"))
			{
				ob=applicationUtility.NavigateToOMSuperUser(ob, writer, appdata);
			}
			//}
			
			}
			catch(Exception e)
			{
				System.out.println("catch for navigation");
				
				//e.printStackTrace();
				String errmsg=e.getMessage();
				
				if(errmsg.length() > 500){
					errmsg=errmsg.substring(0,500);
					
					
			}
				ob.setErrorMsg(errmsg);
				
				ob.setStatus("Failed");
				report.logMessage(writer,ScreenshotName,"Navigate to OM Super user","Sales order page opened", "Exception Occurred: "+errmsg, "Failed");
			}
				finally
				{
					browser.close();
					System.out.println("finally");
					
					tcendTime=System.currentTimeMillis();
					endTime = Calendar.getInstance();				
					endDateTime = formatter.format(endTime.getTime());	
					System.out.println(endDateTime);
					AppDBobj.ResultMapAndDBUpdate(ob, variables.getTrackId(), Integer.parseInt(variables.getReleaseId()),Integer.parseInt(variables.getAppId()), strtDateTime, endDateTime, appdata);
					report.after(writer,tcStartTime,tcendTime,appdata.getOsType(),appdata.getBrowserType(),appdata.getBrowserVersion());		
					report.closewriter(writer);
					
					
				}
			
		
	}
	public void finish() throws Exception {
	}
}
