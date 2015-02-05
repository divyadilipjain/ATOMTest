//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.cisco.testScripts;

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
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class Scenario_Navigation extends FuncLibraryWrapper
{

	public void initialize() throws AbstractScriptException {
		checkInit();
		callFunction("initialize");
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws AbstractScriptException {
		checkInit();
		callFunction("run");
	}

	public void EBS_SCM_Navigate_AUSOMSuperUserSalesOrder()
			throws AbstractScriptException {
		checkInit();
		callFunction("EBS_SCM_Navigate_AUSOMSuperUserSalesOrder");
	}

	public void EBS_SCM_Navigate_OMSuperUserSalesOrder()
			throws AbstractScriptException {
		checkInit();
		callFunction("EBS_SCM_Navigate_OMSuperUserSalesOrder");
	}

	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

}

