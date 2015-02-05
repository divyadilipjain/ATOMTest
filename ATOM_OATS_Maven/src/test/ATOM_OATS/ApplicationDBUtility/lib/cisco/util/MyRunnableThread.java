package lib.cisco.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import lib.cisco.util.PropertiesFileReader;
import oracle.oats.scripting.modules.basic.api.FunctionLibrary;
import oracle.oats.scripting.modules.basic.api.ScriptService;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class MyRunnableThread implements Runnable {
	
	String className="";
	 String methodName="";
	String testData=null;
	String tableName=null;
	String AppId=null;
	String ReleaseId=null;
	String trackId="";
	
	public MyRunnableThread(String TClassName,String TMethodName,String TtestData,String TtableName,String TtrackId,String TAppId,String TReleaseId ){
		System.out.println("Details:");
		System.out.println(TClassName);
		System.out.println(TMethodName);
		System.out.println(TtestData);
		this.className=TClassName;
		this.methodName=TMethodName;
		this.testData=TtestData;
		this.AppId=TAppId;
		this.tableName=TtableName;
		this.trackId=TtrackId;
		this.ReleaseId=TReleaseId;
    	
    	
    	
    }
	/*
	 * Executing the ParallelScriptCall sccipt from command line by providing the TestScriptName and function it needs to call
	 
	 */
	@Override public void run() {
		
		System.out.println("Thread run");
		
		try {
			
			
			System.out.println("commandline Execution");
			
			Runtime rt = Runtime.getRuntime();
			
			 Properties commonProperties = PropertiesFileReader.getInstance().readProperties("common.properties");
			 String projectPath = commonProperties.getProperty("projectPath").trim();
			String bat="C:\\OracleATS\\openScript\\runScript.bat "+projectPath+"\\ParallelScriptCall\\ParallelScriptCall.jwg -ClassName  "+this.className+"  -MethodName  "+this.methodName+"  -TestData  "+this.testData+" -TableName "+this.tableName+" -TrackId "+this.trackId+" -ReleaseId "+this.ReleaseId+" -AppId "+this.AppId+"";
			System.out.println("Batch command:"+bat);
			Process proc = rt.exec(bat);
			
			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));

			// read the output from the command
			System.out.println("Standard output for the command line execution:\n");
			String s5 = null;
			while ((s5 = stdInput.readLine()) != null) {
			    System.out.println(s5);
			}

			// read any errors from the attempted command
			System.out.println("Standard error for the command line execution(if any):\n");
			while ((s5 = stdError.readLine()) != null) {
			    System.out.println(s5);
			}
			
			
			
			
			System.out.println("call method returned");

		} catch (Exception e) {
			System.out.println("Exception in calling script");
			e.printStackTrace();
		}
		
		
		
	}

}
