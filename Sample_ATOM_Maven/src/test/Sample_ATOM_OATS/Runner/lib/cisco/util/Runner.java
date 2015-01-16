//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.cisco.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import static lib.cisco.util.DBUtill.*;
import lib.cisco.util.variables;
import lib.cisco.util.MyRunnableThread;
import lib.cisco.util.AppTestDTO;
import lib.cisco.util.ApplicationDB;
import lib.cisco.util.DBUtill;
import lib.*;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class Runner extends FuncLibraryWrapper
{

	public void initialize() throws AbstractScriptException {
		checkInit();
		callFunction("initialize");
	}

	/**
	 * Method: run This method gets the testDataMap and triggers the primary and Secondary execution of TestCases
	 */
	public void run() throws AbstractScriptException {
		checkInit();
		callFunction("run");
	}

	public void primaryExecution(Map<Integer, Integer> countmap,
			Map<String, List<AppTestDTO>> testDataMap)
			throws AbstractScriptException {
		checkInit();
		callFunction("primaryExecution", countmap, testDataMap);
	}

	public void secondaryExecution(Map<Integer, Integer> countmap,
			Map<String, List<AppTestDTO>> testDataMap)
			throws AbstractScriptException {
		checkInit();
		callFunction("secondaryExecution", countmap, testDataMap);
	}

	public void executeParallel(Map<String, Set<String>> testCasesMap,
			Map<String, List<AppTestDTO>> testDataMap)
			throws AbstractScriptException {
		checkInit();
		callFunction("executeParallel", testCasesMap, testDataMap);
	}

	public void executeSequential(final Map<String, Set<String>> testCasesMap)
			throws AbstractScriptException {
		checkInit();
		callFunction("executeSequential", testCasesMap);
	}

	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

}

