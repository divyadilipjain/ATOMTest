import lib.cisco.util.variables;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
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
	String className="";
	String methodName="";
	String testdata="";
	@FunctionLibrary("ApplicationDBUtility") lib.cisco.util.ApplicationDBUtility applicationDBUtility;
	@FunctionLibrary("Scenario_Login") lib.cisco.testScripts.Scenario_Login scenario_Login;
	@FunctionLibrary("Scenario_Navigation") lib.cisco.testScripts.Scenario_Navigation scenario_Navigation;
	public void initialize() throws Exception {
	}
		
	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {
		
		System.out.println("==========In ParallelScriptCall============");
		
		className=getSettings().get("ClassName");
		methodName=getSettings().get("MethodName");
		System.out.println("classname:"+className);
		System.out.println("methodName:"+methodName);
		
		testdata=getSettings().get("TestData");
		System.out.println("Test Data:"+testdata);
		variables.setAppId(getSettings().get("AppId"));
		variables.setTableName(getSettings().get("TableName"));
		variables.setReleaseId(getSettings().get("ReleaseId"));
		variables.setTrackId(Integer.parseInt(getSettings().get("TrackId")));
		variables.setScriptTestData(getSettings().get("TestData"));
		System.out.println("=========Calling the testScript================");
		System.out.println("Getting variables in ParallelScriptCall:"+variables.getAppId()+":"+variables.getTableName());
		getScript(className).callFunction(methodName);

	}
	
	public void finish() throws Exception {
	}
}
