package lib.cisco.util;

import java.io.Serializable;

public class OutputBean implements Serializable {

	private static final long serialVersionUID = 1L;
    private String testScriptId=null;
    private String status=null;
    private String startDate=null;
    private String endDate=null;
    private String dataId = null;
    private String errorMsg=null;
    private String executedBy=null;
    private int trackId=0;
    private int executeId=0;
    private String errorLogPath=null;
    private String executionType=null;
    private String cycle=null;
    private String screenshotPath=null;
    private String browserType=null;
    private String invocationMethod=null;
    private String browserVersion=null;
    private String osType=null;
    private String gridUrl=null;
    private int appId=0;
    private int releaseId=0;
    
	public String getTestScriptId() {
		return testScriptId;
	}
	public void setTestScriptId(String testScriptId) {
		this.testScriptId = testScriptId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getExecutedBy() {
		return executedBy;
	}
	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}
	public int getTrackId() {
		return trackId;
	}
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}
	public int getExecuteId() {
		return executeId;
	}
	public void setExecuteId(int executeId) {
		this.executeId = executeId;
	}
	public String getErrorLogPath() {
		return errorLogPath;
	}
	public void setErrorLogPath(String errorLogPath) {
		this.errorLogPath = errorLogPath;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getScreenshotPath() {
		return screenshotPath;
	}
	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}
	public String getBrowserType() {
		return browserType;
	}
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	public String getInvocationMethod() {
		return invocationMethod;
	}
	public void setInvocationMethod(String invocationMethod) {
		this.invocationMethod = invocationMethod;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getGridUrl() {
		return gridUrl;
	}
	public void setGridUrl(String gridUrl) {
		this.gridUrl = gridUrl;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}
	
	
    
	
}
