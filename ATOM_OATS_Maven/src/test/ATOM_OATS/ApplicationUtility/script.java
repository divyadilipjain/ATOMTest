import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Calendar;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import lib.cisco.util.Report;
import lib.cisco.util.OutputBean;
import lib.cisco.util.AppTestDTO;
import static lib.cisco.util.Report.projectPath;
import lib.*; 
public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	String ScreenshotName="";
	Report report=new Report();
	@FunctionLibrary("CommonUtility") lib.cisco.util.CommonUtility commonUtility;
	@FunctionLibrary("ApplicationDBUtility") lib.cisco.util.ApplicationDBUtility applicationDBUtility;
	
	public void initialize() throws Exception {
	}
		
	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}
	/*-------------------Define application specific Utility functions ----------------------------*/
	public OutputBean CopyPDFToProjectFolder(OutputBean ob,BufferedWriter writer,AppTestDTO appDto) throws Exception
	{
		//browser.launch();
		System.out.println("Into ClickOnPDFlink");
		
		ScreenshotName=appDto.getTestClassName()+"_"+appDto.getTestMethodName()+"-"+appDto.getDataId();
		commonUtility.createPDFFolder(projectPath);
		System.out.println("PDF folder created in Project path");
		
		commonUtility.createPDFFile(appDto.getPDFPath());
		System.out.println("PDF file created in Project path");
		commonUtility.copyFileUsingStream(new File("C:\\OPDF.pdf"),new File(appDto.getPDFPath()));
		System.out.println("PDF file copied");
		commonUtility.deleteFile("C:\\OPDF.pdf");
		System.out.println("temporary PDF file deleted");
		ob.setStatus("Passed");
		report.logMessage(writer, ScreenshotName, "Copy PDF to project folder", "PDF file copied to project folder", "PDF file copied to project folder", "Passed");
	
	return ob;
}
	/*
	 * Method : userLogin
	 * This method is used to login to the application 
	 * Updates the OutputBean with the status, writes to log 
	 */
	public OutputBean ClickOnPDFlink(OutputBean ob,BufferedWriter writer,AppTestDTO appDto) throws Exception
	{
		//browser.launch();
		System.out.println("Into ClickOnPDFlink");
		
		ScreenshotName=appDto.getTestClassName()+"_"+appDto.getTestMethodName()+"-"+appDto.getDataId();
		
		web.window(15, "/web:window[@index='0' or @title='about:blank']").navigate("http://www.google.com/");
		web.window("/web:window[@index='0' or @title='Google']").waitForPage(null);
		think(1.908);
		web.textBox("/web:window[@index='0' or @title='Google']/web:document[@index='0']/web:form[@id='gbqf' or @name='gbqf' or @index='0']/web:input_text[@id='gbqfq' or @name='q' or @index='0']").click();
		think(1.47);
		web.textBox("/web:window[@index='0' or @title='Google']/web:document[@index='0']/web:form[@id='gbqf' or @name='gbqf' or @index='0']/web:input_text[@id='gbqfq' or @name='q' or @index='0']").click();
		think(2.947);
		web.textBox("/web:window[@index='0' or @title='Google']/web:document[@index='0']/web:form[@id='gbqf' or @name='gbqf' or @index='0']/web:input_text[@id='gbqfq' or @name='q' or @index='0']").setText("O");
		think(4.855);
		web.textBox("/web:window[@index='0' or @title='Google']/web:document[@index='0']/web:form[@id='gbqf' or @name='gbqf' or @index='0']/web:input_text[@id='gbqfq' or @name='q' or @index='0']").click();
		think(1.216);
		web.textBox("/web:window[@index='0' or @title='Google']/web:document[@index='0']/web:form[@id='gbqf' or @name='gbqf' or @index='0']/web:input_text[@id='gbqfq' or @name='q' or @index='0']").setText("Oracle application testing suite PDF");
		think(1.536);
		web.element("/web:window[@index='0' or @title='Google']/web:document[@index='0']/web:div[@text='Remove\r\noracle application testing suite pdf' or @index='119']").click();
		web.window("/web:window[@index='0' or @title='Google']").waitForPage(null);
		think(1.876);
		
		if(web.link("/web:window[@index='0' or @title='oracle application testing suite pdf - Google Search']/web:document[@index='0']/web:a[@text='Oracle® Application Testing Suite Getting Started Guide' or @href='http://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&frm=1&source=web&cd=1&ved=0CB0QFjAA&url=http%3A%2F%2Fdownload.oracle.com%2Fotndocs%2Fproducts%2Foem%2Fpdf%2FOATSGettingStartedGuide.pdf&ei=-wW1VMWwJMOLuwSFgYLwDg&usg=AFQjCNEnP2k0VA60NCpomBlVLF3U4SYNFw&sig2=pYjL_4Pa1vvPjaVvEo5W8Q&bvm=bv.83339334,d.c2E' or @index='65']").exists())
		{
			ob.setStatus("Passed");
			report.logMessage(writer, ScreenshotName, "Search for PDF", "PDF link exists", "PDF link exists", "Passed");
		web.link("/web:window[@index='0' or @title='oracle application testing suite pdf - Google Search']/web:document[@index='0']/web:a[@text='Oracle® Application Testing Suite Getting Started Guide' or @href='http://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&frm=1&source=web&cd=1&ved=0CB0QFjAA&url=http%3A%2F%2Fdownload.oracle.com%2Fotndocs%2Fproducts%2Foem%2Fpdf%2FOATSGettingStartedGuide.pdf&ei=-wW1VMWwJMOLuwSFgYLwDg&usg=AFQjCNEnP2k0VA60NCpomBlVLF3U4SYNFw&sig2=pYjL_4Pa1vvPjaVvEo5W8Q&bvm=bv.83339334,d.c2E' or @index='65']").mouseClick(0,0);
		}
		else
		{
			ob.setStatus("Failed");
			report.logMessage(writer, ScreenshotName, "Search for PDF", "PDF link exists", "PDF link does not exists", "Failed");
		}
		Thread.sleep(15000);
		return ob;
	}
	public OutputBean SavePDFinCdrive(OutputBean ob,BufferedWriter writer,AppTestDTO appDto) throws Exception
	{
		//browser.launch();
		System.out.println("Into ClickOnPDFlink");
		
		ScreenshotName=appDto.getTestClassName()+"_"+appDto.getTestMethodName()+"-"+appDto.getDataId();

		Robot robot=new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_S);
		// CTRL+SHIFT+S is now pressed 
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_S);
		// CTRL+SHIFT+S is now released
		Thread.sleep(10000);
		System.out.println("1");
		Thread.sleep(10000);
		robot.keyPress(KeyEvent.VK_O);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_O);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_P);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_P);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_D);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_D);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_F);
		System.out.println("1");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_F);
		System.out.println("1");
		Thread.sleep(2000);
		//Press 6 tabs
		for(int i=0;i<6;i++)
		{
			robot.keyPress(KeyEvent.VK_TAB);
			System.out.println(i);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(2000);
		}
		Thread.sleep(2000);
		//Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		//press c
		robot.keyPress(KeyEvent.VK_C);
		System.out.println("C");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_C);
		Thread.sleep(2000);
		//Press :
		robot.keyPress(KeyEvent.VK_SHIFT);  
        robot.keyPress(KeyEvent.VK_SEMICOLON);  
        robot.keyRelease(KeyEvent.VK_SEMICOLON);  
        robot.keyRelease(KeyEvent.VK_SHIFT);
        System.out.println("C");
		Thread.sleep(2000);
		//Press \
        robot.keyPress(KeyEvent.VK_BACK_SLASH);  
        robot.keyRelease(KeyEvent.VK_BACK_SLASH);
        System.out.println("C");
		Thread.sleep(2000);
		//Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		//Click on Save button
		web.dialog("/web:dialog_unknown[@text='Save As']").clickButton(0);
		
		ob.setStatus("Passed");
		report.logMessage(writer, ScreenshotName, "Save PDF to C: drive", "PDF saved to C: drive", "PDF Saved to C: drive", "Passed");
		
	return ob;
	}
	
	
	/*
	 * Method : userLogin
	 * This method is used to login to the application 
	 * Updates the OutputBean with the status, writes to log 
	 */
	public OutputBean userLogin(OutputBean ob,BufferedWriter writer,AppTestDTO appDto) throws Exception
	{
		//browser.launch();
		System.out.println("Into User login");
		System.out.println("appDto in user login:"+appDto.getUrl());
		ScreenshotName=appDto.getTestClassName()+"_"+appDto.getTestMethodName()+"-"+appDto.getDataId();
		
		web.window(commonUtility.getProperty().getProperty("window")).navigate(appDto.getUrl());
		//web.window(commonUtility.getProperty().getProperty("login_window")).waitForPage(null);
		Thread.sleep(10000);
		if(web.window(commonUtility.getProperty().getProperty("login_window")).exists())
		{
			report.logMessage(writer,ScreenshotName,"Navigate to EBS Url","Launch any Browser, type the URL "+appDto.getUrl()+" in the address bar and hit Enter  ", "Oracle Applications Login Screen should be displayed", "Passed");
		web.textBox(commonUtility.getProperty().getProperty("UserName_textBox")).setText(appDto.getUserid());
		Thread.sleep(2000);
		web.textBox(commonUtility.getProperty().getProperty("Password_textBox")).setPassword(appDto.getPassword());
		Thread.sleep(2000);
		web.button(commonUtility.getProperty().getProperty("login_btn")).click();
		web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
		if(web.window(commonUtility.getProperty().getProperty("oracleHomePage")).exists())
		{
			report.logMessage(writer,ScreenshotName,"Login to Application","Enter the credentials and Click on Login Button", "Oracle Applications home page should be displayed", "Passed");
			System.out.println("Logged in Successfully!!!");
			ob.setStatus("Passed");
			
			
		}
		else
		{
			report.logMessage(writer,ScreenshotName,"Login to Application","Enter the credentials and Click on Login Button", "Oracle Applications home page not displayed", "Failed");
			System.out.println("Login Error!!!");
			ob.setStatus("Failed");
			
		}
		}
		else
		{
			report.logMessage(writer,ScreenshotName,"Navigate to EBS Url","Launch any Browser, type the URL "+appDto.getUrl()+" in the address bar and hit Enter  ", "Oracle Applications Login Screen not displayed", "Failed");
			System.out.println("else");
			ob.setStatus("Failed");
		}
		System.out.println("returning from user login:ob:"+ob.getStatus()+":"+appDto.getDataId());
		return ob;
		
	}
	/*
	 * Method : NavigateToAusOMSuperUser
	 * This method is used to navigate to AUS OM Super User and open Sales Order form
	 * Updates the OutputBean with the status, writes to log 
	 */
	public OutputBean NavigateToAusOMSuperUser(OutputBean ob,BufferedWriter writer,AppTestDTO appDto) throws Exception
	{
		System.out.println("Into navigation");
		String status="";
		
		
			//OutputBean temp=userLogin(ob, writer, appDto);
			if(ob.getStatus().equalsIgnoreCase("Passed"))
			{
				System.out.println("ScreenShotName:"+ScreenshotName);
				//ob.setStatus("Passed");
				//report.logMessage(writer,ScreenshotName,"Check if EBS Home page exists ","Login to the application", "Oracle EBS home page exists", "Passed");
				
		Thread.sleep(3000);
		if(web.window(commonUtility.getProperty().getProperty("oracleHomePage")).exists())
		{
			report.logMessage(writer,ScreenshotName,"Check if EBS Home page exists ","Login to the application", "Oracle EBS home page exists", "Passed");
				web.image(commonUtility.getProperty().getProperty("AusOMSuperUser")).click();
			
				//web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(10000);
				web.image(commonUtility.getProperty().getProperty("Aus_OrderReturns")).click();
				web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(2000);
				web.link(commonUtility.getProperty().getProperty("Aus_SalesOrder")).click();
				web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				//Thread.sleep(2000);
				//web.window(commonUtility.getProperty().getProperty("OracleR12Page")).close();
				//Thread.sleep(3000);
				//web.window(commonUtility.getProperty().getProperty("oracleHomePage")).refresh();
				//Thread.sleep(3000);
				//web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(3000);
				//web.element(commonUtility.getProperty().getProperty("Aus_viewSalesOrderOrganizer")).clickContextMenu("Open",0);			
			
				//web.window(commonUtility.getProperty().getProperty("OracleR12Page")).waitForPage(null);
				if(web.window(commonUtility.getProperty().getProperty("OracleR12Page")).exists())
				{
					report.logMessage(writer,ScreenshotName,"Open the Sales order Form","Navigate to AUS OM Super User and click on Sales Order", "Sales order Form Opened", "Passed");
					System.out.println("Navigated to Aus OM SuperUser!!");
					ob.setStatus("Passed");
					
				}
				else
				{
					report.logMessage(writer,ScreenshotName,"Open the Sales order Form","Navigate to AUS OM Super User and click on Sales Order", "Sales order Form not Opened", "Failed");
				System.out.println("else");
				ob.setStatus("Failed");
				}
					
			
		}
		else
		{
			report.logMessage(writer,ScreenshotName,"Check if EBS Home page exists ","Login to the application", "Oracle EBS home page not exists", "Failed");
			System.out.println("Oracle Home page doesnt exist");
			ob.setStatus("Failed");
			
		}
		
			}
		else
		{
			ob.setStatus("Failed");
			ob.setErrorMsg("Failed During Login");
		}
	
		
		System.out.println("Returning from navigation");
		
		return ob;
	}
	/*
	 * Method : NavigateToOMSuperUser
	 * This method is used to navigate to OM Super User and open Sales Order form
	 * Updates the OutputBean with the status, writes to log 
	 */
	public  OutputBean NavigateToOMSuperUser(OutputBean ob,BufferedWriter writer,AppTestDTO appDto) throws Exception
	{
		System.out.println("into NavigateToOMSuperUser");
		String status="";
		
		Thread.sleep(3000);
		//OutputBean temp=userLogin(ob, writer, appDto);
		/*if(temp.getStatus().equalsIgnoreCase("Passed"))
		{*/
			System.out.println("ScreenShotName:"+ScreenshotName);
			//report.logMessage(writer,ScreenshotName,"Check if EBS Home page exists ","Login to the application", "Oracle EBS home page exists", "Passed");
			ob.setStatus("Passed");
		if(web.window(commonUtility.getProperty().getProperty("oracleHomePage")).exists())
		{
			System.out.println("ScreenShotName:"+ScreenshotName);
			report.logMessage(writer,ScreenshotName,"Check if EBS Home page exists ","Login to the application", "Oracle EBS home page exists", "Passed");
				web.image(commonUtility.getProperty().getProperty("OMSuperUser")).click();
			
				//web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(10000);
				web.image(commonUtility.getProperty().getProperty("OM_OrderReturns")).click();
				web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(2000);
				web.link(commonUtility.getProperty().getProperty("OM_SalesOrder")).click();
				web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(2000);
				web.window(commonUtility.getProperty().getProperty("OracleR12Page")).close();
				Thread.sleep(3000);
				web.window(commonUtility.getProperty().getProperty("oracleHomePage")).refresh();
				Thread.sleep(3000);
				web.window(commonUtility.getProperty().getProperty("oracleHomePage")).waitForPage(null);
				Thread.sleep(3000);
				web.element(commonUtility.getProperty().getProperty("OM_OrderCopy")).clickContextMenu("Open",0);			
			
				web.window(commonUtility.getProperty().getProperty("OracleR12Page")).waitForPage(null);
				if(web.window(commonUtility.getProperty().getProperty("OracleR12Page")).exists())
				{
					report.logMessage(writer,ScreenshotName,"Open the Sales order Form","Navigate to AUS OM Super User and click on Sales Order", "Sales order Form Opened", "Passed");
					System.out.println("Navigated to OM SuperUser!!");
					ob.setStatus("Passed");
					
				}
				else
				{
					report.logMessage(writer,ScreenshotName,"Open the Sales order Form","Navigate to AUS OM Super User and click on Sales Order", "Sales order Form not Opened", "Failed");
					System.out.println("else");
					ob.setStatus("Failed");
				}
			
		}
		else
		{
			report.logMessage(writer,ScreenshotName,"Check if EBS Home page exists ","Login to the application", "Oracle EBS home page not exists", "Failed");
			ob.setStatus("Failed");
			System.out.println("Oracle Home page doesnt exist");
			
		}
		
		//}
	/*	else
		{
			ob.setStatus("Failed");
			ob.setErrorMsg("Failed During Login");
		}*/
		
		System.out.println("Returning from navigation");
		return ob;
		
		
	}
	public void finish() throws Exception {
	}
}
