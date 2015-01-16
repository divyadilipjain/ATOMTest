//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.cisco.util;

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
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class CommonUtility extends FuncLibraryWrapper
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

	public void createPDFFolder(String path) throws AbstractScriptException {
		checkInit();
		callFunction("createPDFFolder", path);
	}

	public void deleteFile(String path) throws AbstractScriptException {
		checkInit();
		callFunction("deleteFile", path);
	}

	@SuppressWarnings("unused")
	public void copyFileUsingStream(File source, File dest)
			throws AbstractScriptException {
		checkInit();
		callFunction("copyFileUsingStream", source, dest);
	}

	public void createPDFFile(String filepath) throws AbstractScriptException {
		checkInit();
		callFunction("createPDFFile", filepath);
	}

	public void captureScreen(String filepath) throws AbstractScriptException {
		checkInit();
		callFunction("captureScreen", filepath);
	}

	public void clickImage(String linkProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickImage", linkProperties);
	}

	public void clickElement(String linkProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickElement", linkProperties);
	}

	public void clickLink(String linkProperties) throws AbstractScriptException {
		checkInit();
		callFunction("clickLink", linkProperties);
	}

	public void clickRadioButton(String linkProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickRadioButton", linkProperties);
	}

	public void clickTextBox(String linkProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickTextBox", linkProperties);
	}

	public void clickTextArea(String linkProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickTextArea", linkProperties);
	}

	public void clickCheckbox(String linkProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickCheckbox", linkProperties);
	}

	public void fillText(String textProperties, String textValue)
			throws AbstractScriptException {
		checkInit();
		callFunction("fillText", textProperties, textValue);
	}

	public void clickButton(String buttonProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("clickButton", buttonProperties);
	}

	public void dialogclickOkButton(String buttonProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("dialogclickOkButton", buttonProperties);
	}

	public void loginDialogClick(String buttonProperties, String userName,
			String passWord) throws AbstractScriptException {
		checkInit();
		callFunction("loginDialogClick", buttonProperties, userName, passWord);
	}

	public void blankTxnSearch(String searchButtonprop)
			throws AbstractScriptException {
		checkInit();
		callFunction("blankTxnSearch", searchButtonprop);
	}

	public void invalidTxnSearch(String textBoxProp, String searchButtonprop,
			String txnNumber) throws AbstractScriptException {
		checkInit();
		callFunction("invalidTxnSearch", textBoxProp, searchButtonprop,
				txnNumber);
	}

	public void leftClickMouse(String mouseProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("leftClickMouse", mouseProperties);
	}

	public void robotClickTextArea(String mouseProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("robotClickTextArea", mouseProperties);
	}

	public void robotClickImage(String mouseProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("robotClickImage", mouseProperties);
	}

	public void robotClickElement(String mouseProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("robotClickElement", mouseProperties);
	}

	public void robotClickLink(String mouseProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("robotClickLink", mouseProperties);
	}

	public void robotClickButton(String mouseProperties)
			throws AbstractScriptException {
		checkInit();
		callFunction("robotClickButton", mouseProperties);
	}

	/**
	 * To get the path to current Script 
	 */
	public String getCurrentFolder() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentFolder");
	}

	public Properties getProperty() throws AbstractScriptException {
		checkInit();
		return (Properties) callFunction("getProperty");
	}

	public void selectFromDropDownbyIndex(String dropDownProperties,
			int dropDownValue) throws AbstractScriptException {
		checkInit();
		callFunction("selectFromDropDownbyIndex", dropDownProperties,
				dropDownValue);
	}

	public void fillTextArea(String textProperties, String textValue)
			throws AbstractScriptException {
		checkInit();
		callFunction("fillTextArea", textProperties, textValue);
	}

	public void fillDate(String dateProperties, String dateValue)
			throws AbstractScriptException {
		checkInit();
		callFunction("fillDate", dateProperties, dateValue);
	}

	public void selectFromDropDown(String dropDownProperties,
			String dropDownValue) throws AbstractScriptException {
		checkInit();
		callFunction("selectFromDropDown", dropDownProperties, dropDownValue);
	}

	public String todayDate() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("todayDate");
	}

	public String nextYearDate() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("nextYearDate");
	}

	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

}

