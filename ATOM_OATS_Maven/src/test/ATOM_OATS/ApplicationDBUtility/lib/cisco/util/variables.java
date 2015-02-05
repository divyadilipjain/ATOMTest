package lib.cisco.util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lib.cisco.util.AppTestDTO;
@SuppressWarnings("serial") public class variables implements Serializable{
	private static String ScriptClassName="";
	private static String ScriptmethodName="";
	private static String ScriptTestData="";
	private static String releaseId="";
	private static String AppId=null;
	
	private static String tableName=null;
	private static String Status=null;
	private static int TrackId=0;
	public static int getTrackId() {
		return TrackId;
	}
	public static void setTrackId(int trackId) {
		TrackId = trackId;
	}
	public static String getScriptClassName() {
		return ScriptClassName;
	}
	public static void setScriptClassName(String scriptClassName) {
		ScriptClassName = scriptClassName;
	}
	public static String getScriptmethodName() {
		return ScriptmethodName;
	}
	public static void setScriptmethodName(String scriptmethodName) {
		ScriptmethodName = scriptmethodName;
	}
	public static String getScriptTestData() {
		return ScriptTestData;
	}
	public static void setScriptTestData(String scriptTestData) {
		ScriptTestData = scriptTestData;
	}
	public static String getReleaseId() {
		return releaseId;
	}
	public static void setReleaseId(String releaseId) {
		variables.releaseId = releaseId;
	}
	public static String getAppId() {
		return AppId;
	}
	public static void setAppId(String appId) {
		AppId = appId;
	}
	public static String getTableName() {
		return tableName;
	}
	public static void setTableName(String tableName) {
		variables.tableName = tableName;
	}
	public static String getStatus() {
		return Status;
	}
	public static void setStatus(String status) {
		Status = status;
	}
	


}
