//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.cisco.util;

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
import lib.*;
import static lib.cisco.util.Report.projectPath;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class ApplicationUtility extends FuncLibraryWrapper
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

	public OutputBean CopyPDFToProjectFolder(OutputBean ob,
			BufferedWriter writer, AppTestDTO appDto)
			throws AbstractScriptException {
		checkInit();
		return (OutputBean) callFunction("CopyPDFToProjectFolder", ob, writer,
				appDto);
	}

	public OutputBean ClickOnPDFlink(OutputBean ob, BufferedWriter writer,
			AppTestDTO appDto) throws AbstractScriptException {
		checkInit();
		return (OutputBean) callFunction("ClickOnPDFlink", ob, writer, appDto);
	}

	public OutputBean SavePDFinCdrive(OutputBean ob, BufferedWriter writer,
			AppTestDTO appDto) throws AbstractScriptException {
		checkInit();
		return (OutputBean) callFunction("SavePDFinCdrive", ob, writer, appDto);
	}

	public OutputBean userLogin(OutputBean ob, BufferedWriter writer,
			AppTestDTO appDto) throws AbstractScriptException {
		checkInit();
		return (OutputBean) callFunction("userLogin", ob, writer, appDto);
	}

	public OutputBean NavigateToAusOMSuperUser(OutputBean ob,
			BufferedWriter writer, AppTestDTO appDto)
			throws AbstractScriptException {
		checkInit();
		return (OutputBean) callFunction("NavigateToAusOMSuperUser", ob,
				writer, appDto);
	}

	public OutputBean NavigateToOMSuperUser(OutputBean ob,
			BufferedWriter writer, AppTestDTO appDto)
			throws AbstractScriptException {
		checkInit();
		return (OutputBean) callFunction("NavigateToOMSuperUser", ob, writer,
				appDto);
	}

	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

}

