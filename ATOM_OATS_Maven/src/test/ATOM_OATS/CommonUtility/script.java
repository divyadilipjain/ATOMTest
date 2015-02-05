import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.imageio.ImageIO;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	Properties p;
	FileInputStream fis;
	Robot r=null;
	public void initialize() throws Exception {
	}
		
	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}
	public void createPDFFolder(String path)
	{
		 File dir = new File(path+"\\PDF");
		 if(!dir.exists())
		 {
	        dir.mkdir();
	        System.out.println(dir+" created");
		 }
		 else
			 System.out.println("Directory already exists!!");
		
	}
	public void deleteFile(String path)
	{
		try{
			 
    		File file = new File(path);
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
 
    	}catch(Exception e){
 
    		e.printStackTrace();
 
    	}
		
	}
	@SuppressWarnings("unused")
	public void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	        System.out.println("File copied");
	    } 
	    catch(IOException ie){
	    	System.out.println("Exception in file copy");
	    	
	    }
	    finally {
	    
	        is.close();
	        os.close();
	    }
	}
	public void createPDFFile(String filepath) {
		try {
			 
		      File file = new File(filepath);
	 
		      if (file.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
	 
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
		
	}
	public void captureScreen(String filepath) throws Exception{

		 try
		    {      
		     Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		     Robot robot = new Robot();
		     BufferedImage img = robot.createScreenCapture(new Rectangle(size));
		     File save_path=new File(filepath);
		     ImageIO.write(img, "JPG", save_path);
		    }
		    catch(Exception e)
		    {
		         System.out.println("Failed to capture screen error occured:"+e);
		    }

	}
	
	public void clickImage(String linkProperties) throws Exception{

		if(web.image(linkProperties).exists()){
			web.image(linkProperties).click();
		}
		else
		{
			warn("Image not Clicked");
		}
	}
	
	public void clickElement(String linkProperties) throws Exception{

		if(web.element(linkProperties).exists()){
			web.element(linkProperties).click();
		}
		else
		{
			warn("Element not Clicked");
		}
	}
	
	public void clickLink(String linkProperties) throws Exception{

		if(web.link(linkProperties).exists()){
			web.link(linkProperties).click();
		}
		else
		{
			warn("Link not Clicked");
		}
	}
	
	public void clickRadioButton(String linkProperties) throws Exception{

		if(web.radioButton(linkProperties).exists()){
			web.radioButton(linkProperties).click();
		}
		else
		{
			warn("RadioButton not Clicked");
		}
	}
	
	public void clickTextBox(String linkProperties) throws Exception{

		if(web.textBox(linkProperties).exists()){
			web.textBox(linkProperties).click();
		}
		else
		{
			warn("TexBox not Clicked");
		}
	}
	
	public void clickTextArea(String linkProperties) throws Exception{

		if(web.textArea(linkProperties).exists()){
			web.textArea(linkProperties).click();
		}
		else
		{
			warn("TextArea not clicked");
		}
	}
	
	public void clickCheckbox(String linkProperties) throws Exception{

		if(web.checkBox(linkProperties).exists()){
		
			web.checkBox(linkProperties).check(true);
		}
		else
		{
			warn("CheckBox not clicked");
		}
	}

	
	public void fillText(String textProperties,String textValue) throws Exception{

		if(web.textBox(textProperties).exists())
		{web.textBox(textProperties).setText(textValue);
		
		}
		else
		{
			warn("TextBox not Filled");
		}

	}
	
	public void clickButton(String buttonProperties) throws Exception{
		if(web.button(buttonProperties).exists())
		{
			web.button(buttonProperties).click();
			
		}
		else
		{
			warn("Button not clicked");
		}
	}
   
	public void dialogclickOkButton(String buttonProperties) throws Exception{
		if(web.alertDialog(buttonProperties).exists())
		web.alertDialog(buttonProperties).clickOk();	
		else
			warn("Dialog OK Button not clicked");
	}
	
	public void loginDialogClick(String buttonProperties, String userName, String passWord) throws Exception
	{
		System.out.println("logindialog exists:"+web.loginDialog(buttonProperties).exists());
		if(web.loginDialog(buttonProperties).exists())
		{
			web.loginDialog(buttonProperties).clickOk(userName, passWord);
		}
		else
			warn("Login Dialog OK Button not clicked");
	}

  public void blankTxnSearch(String searchButtonprop) throws Exception {
		
		System.out.println("func: BlankTxnSearch");
		clickButton(p.getProperty(searchButtonprop));
		Thread.sleep(5000);
		if(web.alertDialog(p.getProperty("blankTxnAlertBox")).exists())
		{
			web.alertDialog(p.getProperty("blankTxnAlertBox")).clickOk();
			info("Blank search alert box popped up");
		}
		else
		{
			warn("No blank search alert box popped up");
		}
	}

	public void invalidTxnSearch(String textBoxProp,String searchButtonprop,String txnNumber) throws Exception {

		System.out.println("func: InvalidTxnSearch");

		Thread.sleep(4000);
		fillText(p.getProperty(textBoxProp),txnNumber );
		Thread.sleep(3000);
		clickButton(p.getProperty(searchButtonprop));
		Thread.sleep(5000);
		if(web.element(p.getProperty("noResultMsg")).exists())
		{
			info("No such transaction exists");
		}
		else
		{
			warn("Transaction exists");
		}
	}
	
    public void leftClickMouse(String mouseProperties) throws Exception{
       
		if(web.link(mouseProperties).exists()){
			web.link(mouseProperties).mouseOver();
			r = new Robot();
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		else
		{
			warn("Link not clicked.");
		}
	 }
    
    public void robotClickTextArea(String mouseProperties) throws Exception{
        
 		if(web.textArea(mouseProperties).exists()){
 			web.textArea(mouseProperties).mouseOver();
 			r = new Robot();
 			r.mousePress(InputEvent.BUTTON1_MASK);
 			r.mouseRelease(InputEvent.BUTTON1_MASK);
 		}
 		else
		{
			warn("TextArea not clicked.");
		}
 	 }
    
    public void robotClickImage(String mouseProperties) throws Exception{
        
		if(web.image(mouseProperties).exists()){
			web.image(mouseProperties).mouseOver();
			r = new Robot();
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		else
		{
			warn("Image not clicked.");
		}
	 }
    public void robotClickElement(String mouseProperties) throws Exception{
        if(web.element(mouseProperties).exists()){
        web.element(mouseProperties).mouseOver();
        r = new Robot();
        r.mousePress(InputEvent.BUTTON1_MASK);
      r.mouseRelease(InputEvent.BUTTON1_MASK);
        }
        else
   {
   warn("Element not clicked.");
   }
   }
    
  
    public void robotClickLink(String mouseProperties) throws Exception{
   	 if(web.link(mouseProperties).exists()){
   		 web.link(mouseProperties).mouseOver();
   		 r = new Robot();
   		 r.mousePress(InputEvent.BUTTON1_MASK);
 			 r.mouseRelease(InputEvent.BUTTON1_MASK);
   	 }
   	 else
		{
			warn("Link not clicked.");
		}
    }
    
    public void robotClickButton(String mouseProperties) throws Exception{
   	 System.out.println("prop:"+mouseProperties);
   	 if(web.button(mouseProperties).exists()){
   		 System.out.println("in robot click");
   		 web.button(mouseProperties).mouseOver();
   		 r = new Robot();
   		 r.mousePress(InputEvent.BUTTON1_MASK);
 			 r.mouseRelease(InputEvent.BUTTON1_MASK);
   	 }
   	 else
		 {
			warn("Button not clicked.");
		 }
    }
	
	/**
	 * To get the path to current Script 
	 */
	public String getCurrentFolder() throws Exception {
		String path=getScriptPackage().getScriptPath().substring(0,getScriptPackage().getScriptPath().indexOf(getScriptPackage().getWorkspace()));
		path+=getScriptPackage().getWorkspace();
		System.out.println("Current Script path:"+path);
		return path;
	}

	/*
	 * Method: getProperty
	 * This method is used to get the property from the properties file
	 */
	public Properties getProperty() throws Exception{
		p=new Properties();
		//System.out.println("inside property");
		fis=new FileInputStream("C:\\OracleATS\\OFT\\Sample_ATOM_OATS\\ObjectDefinitionLibrary\\ApplicationObjectLibrary.properties");

		p.load(fis);
		
		//System.out.println("in getProperty,p:"+p);
		//System.out.println(getCurrentFolder()+"\\ATOMObjectLibrary.properties");
		return p;
		}
	 public void selectFromDropDownbyIndex(String dropDownProperties,int dropDownValue) throws Exception{
	        if(web.selectBox(dropDownProperties).exists())
	        {
			web.selectBox(dropDownProperties).selectOptionByIndex(dropDownValue);
		    }
	        else
			{
				warn("Dropdown option not selected.");
			}
		
		}
	    
		public void fillTextArea(String textProperties,String textValue) throws Exception{
	       if(web.textArea(textProperties).exists())
			web.textArea(textProperties).setText(textValue);
	       else
	    	   warn("TextArea not filled.");
		}

		public void fillDate(String dateProperties,String dateValue) throws Exception{
		  if(web.textBox(dateProperties).exists())
			web.textBox(dateProperties).setText(dateValue);
		  else
			  warn("Date TextBox not filled.");
		}

		

		public void selectFromDropDown(String dropDownProperties,String dropDownValue) throws Exception{

			  if(web.selectBox(dropDownProperties).exists())
		        {
				web.selectBox(dropDownProperties).selectOptionByText(dropDownValue);
			    }
		        else
				{
					warn("Dropdown option not selected.");
				}
		}

		public String todayDate() {

			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
			String dateNow = formatter.format(currentDate.getTime());
			System.out.println("Now the date is :=>  " + dateNow);
			return dateNow;
		}

		public String nextYearDate() {

			Calendar currentDate = Calendar.getInstance();
			currentDate.add(Calendar.MONTH, 18);
			SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
			String dateNow = formatter.format(currentDate.getTime());
			System.out.println("Now the date is :=>  " + dateNow);

			return dateNow;
		}	
		
	public void finish() throws Exception {
	}
}
