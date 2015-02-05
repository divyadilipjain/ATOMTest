package lib.cisco.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportHomePage {
	// file paths
		public static String mainReportLoaction = ""; 
		public static String reportLogLoaction  = "";
		public static String dashBoardLocation = "";
		private final static Logger LOGGER = Logger.getLogger(ReportHomePage.class.getName());
		static Properties commonProperties = PropertiesFileReader.getInstance().readProperties("common.properties");
		public static String projectPath = commonProperties.getProperty("projectPath").trim();
	public static void createReportHomePage(String startTime, String endTime, long executionTime,String reportFolderDateTime) {
		mainReportLoaction = projectPath+"\\Reports("+reportFolderDateTime+")\\mainReport.html";
		System.out.println("mainReportLoaction=="+mainReportLoaction);
		reportLogLoaction  = projectPath+"\\Reports("+reportFolderDateTime+")\\logs";
		try{
			File report = new File(mainReportLoaction);
			if(report.exists()){
				report.delete();
			}else{
				report.createNewFile();
			}
			/*File dashBoard = new File(dashBoardLocation);
			if(dashBoard.exists()){
				dashBoard.delete();
			}else{
				dashBoard.createNewFile();
			}*/
			FileWriter fw = new FileWriter(mainReportLoaction,true); 
			fw.write(formatTheData(getRequiredDataFromDetailedLog(getTheRequiredDetailedReportName()),startTime, endTime, executionTime));
			fw.close();
			System.out.println("Sucess: Main report created sucessfully");
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error: Main report creation failed :",e);
			
		}
	}

	public static String formatTheData(ArrayList<ArrayList<String>> allData, String TestExecutionStartTime,String TestExecutionEndTime, long executionTime){
		//System.out.println("AllData: " + allData);
		ArrayList<String> reportHeader = new ArrayList<String>();
		ArrayList<ArrayList<String>> reportBody = new ArrayList<ArrayList<String>>();
		String finalReport = "";

		// **********************create the Header***************************//

		ArrayList<Date> TestExecutedOn = new ArrayList<Date>();
		Date testExecutedOn = new Date();
		int NumberOfPASSED = 0;
		int numberOfFailed=0;
		String TotalExecutionTime = "";

		// Test Execution on
		for(int i=0; i<allData.size(); i++){
			try{
				Date date=new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				date = dateFormat.parse(allData.get(i).get(0));
				TestExecutedOn.add(date);
			}catch(Exception e){
				LOGGER.log(Level.SEVERE, "Exception :",e);
			}
		}
		/*for(int i=1; i<TestExecutedOn.size(); i++){
			if(TestExecutedOn.get(i-1).compareTo(TestExecutedOn.get(i))>0){
				testExecutedOn = TestExecutedOn.get(i);
			}else if(TestExecutedOn.get(i-1).compareTo(TestExecutedOn.get(i))>0){
				testExecutedOn = TestExecutedOn.get(i-1);
			}else if(TestExecutedOn.get(i-1).compareTo(TestExecutedOn.get(i))>0){
				testExecutedOn = TestExecutedOn.get(i-1);
			}
		}*/
		SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = DATE_FORMAT1.format(new Date());
		//reportHeader.add(todayDate);
		//Start and end time we will get from runner
		reportHeader.add(TestExecutionStartTime);
		reportHeader.add(TestExecutionEndTime);

		//count the number of pass in the arraylist
		for(int i=0; i<allData.size(); i++){
			try{
				if(allData.get(i).get(3).equalsIgnoreCase("Passed")){
					NumberOfPASSED++;
				}else{
					numberOfFailed++;
				}
			}catch(Exception e){
				LOGGER.log(Level.SEVERE, "Exception :",e);
			}
		}
		/*long timeMillis = executionTime;
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		long timeMints = TimeUnit.MILLISECONDS.toMinutes(timeMillis);
		long timeHours = TimeUnit.MILLISECONDS.toHours(timeMillis);*/
		long seconds, minutes, hours,secRemaining;
		seconds = executionTime / 1000;
		hours = seconds / 3600;
		seconds = seconds % 3600;
		minutes = seconds / 60;
		minutes = minutes % 60;
		secRemaining=(seconds-(minutes*60));
		//hrsRemaining=(minutes-(hours*3600));
		
		//total execution time
		//float roubd = (float) (Math.round((float) (executionTime/(1000*60*60))*100.0)/100.0);
		TotalExecutionTime = String.valueOf(minutes+" mins :"+secRemaining+" secs");
		reportHeader.add(TotalExecutionTime);
		/*DecimalFormat decimalFormat = new DecimalFormat("#.##");
		Double passPer = (double) ((NumberOfPASSED / allData.size()) * 100);
		passPer = Double.parseDouble(decimalFormat.format(passPer));
		
		Double failedPer = (double) ((numberOfFailed / allData.size()) * 100);
		failedPer = Double.parseDouble(decimalFormat.format(failedPer));
		//Number of test cases 
		reportHeader.add(String.valueOf(allData.size()));
		reportHeader.add(String.valueOf(NumberOfPASSED+"("+passPer)+"%)");
		reportHeader.add(String.valueOf(numberOfFailed+"("+failedPer)+"%)");*/

		// **********************create the body***************************//

		for(int i=0; i<allData.size(); i++){
			//System.out.println("data counter: " + i);
			ArrayList<String> reportBodyLineLevel = new ArrayList<String>();
			reportBodyLineLevel.add(allData.get(i).get(7));
			reportBodyLineLevel.add(allData.get(i).get(4));
			reportBodyLineLevel.add(allData.get(i).get(5));
			reportBodyLineLevel.add(allData.get(i).get(6));
			if(allData.get(i).get(3).equalsIgnoreCase("Passed")){
				reportBodyLineLevel.add("green");
			}else{
				reportBodyLineLevel.add("red");
			}
			if(allData.get(i).get(3).equalsIgnoreCase("Passed")){
				reportBodyLineLevel.add("Passed");
			}else{
				reportBodyLineLevel.add("Failed");
			}
			//reportBodyLineLevel.add(allData.get(i).get(7));
			//System.out.println("reportBodyLineLevel : "+ reportBodyLineLevel);
			reportBody.add(reportBodyLineLevel);
		}

		finalReport = CreateMainReport(reportHeader,reportBody);
		//System.out.println(finalReport);
		return finalReport;
	}

	//===== Fetch data from log's report
	public static ArrayList<ArrayList<String>> getRequiredDataFromDetailedLog(LinkedHashSet<String> ListOfFiles){
		//System.out.println("test1 : "+ListOfFiles);
		Iterator<String> itr = ListOfFiles.iterator();
		String report = "";
		ArrayList<ArrayList<String>> dataSetPerTestReport = new ArrayList<ArrayList<String>>();

		do{
			String file = itr.next();
			ArrayList<String> dataSet = new ArrayList<String>();
			report = getReport(file);
			try{
				dataSet.add(getStringFromReport(report, "Test Executed On</td><td class ='summarybody' width=450>", "<"));
				dataSet.add(getStringFromReport(report, "Test Execution Start Time</td><td class ='summarybody' width=450>", "<"));
				dataSet.add(getStringFromReport(report, "Test Execution End Time</td><td class ='summarybody'>", "<"));
				if(!report.contains("cross.png")){
					dataSet.add("Passed");
				}else{
					dataSet.add("Failed");
				}
				dataSet.add(getStringFromReport(report, "<TD class='tsgen' width=155px>", "<"));
				dataSet.add(getStringFromReport(report, "Browser Type</td><td class ='summarybody'>", "<"));
				dataSet.add(getStringFromReport(report, "Total Execution Time</td><td class ='summarybody'>", "<"));
				//dataSet.add(getStringFromReport(report, "com.cisco.testscripts.", ".png"));
				dataSet.add(file);
			}catch(Exception e){
				LOGGER.log(Level.SEVERE, "Error occured : While fetching data from log.",e);
			}
			//System.out.println("test2 :" + dataSet);
			dataSetPerTestReport.add(dataSet);
			//System.out.println(itr.hasNext());
		}while(itr.hasNext());

		//System.out.println(dataSetPerTestReport);
		return dataSetPerTestReport;
	}


	//===== Get The Required detailed report.
	/**
	 * Get The Required detailed report names
	 * @return created reports name in the log folder 
	 */
	public static LinkedHashSet<String> getTheRequiredDetailedReportName(){
		ArrayList<String> reportNameList = new ArrayList<String>();
		ArrayList<String> ReportList = new ArrayList<String>();
		ArrayList<String> allReportNamesList = new ArrayList<String>();

		//==== get the list of file present
		File folder = new File(reportLogLoaction);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				reportNameList.add(listOfFiles[i].getName());
			} 
		}
		System.out.println("reportNameList=="+reportNameList);
		//==== Get the recent report log file
		for(int i=0; i < reportNameList.size(); i++){
			String dataId = "";
			int noOfReport = 0;
			String[] reportName = reportNameList.get(i).split("_DataId_");
			for(int j=0; j<reportNameList.size(); j++){
				if(reportNameList.get(j).contains(reportName[0])){
					noOfReport++;
					String[] getDataId = reportName[1].split("_");
					dataId = getDataId[0];
				}
			}

			if(noOfReport==2){
				ReportList.add(reportName[0]+"_DataId_"+dataId+"_2");
			}else{
				ReportList.add(reportName[0]+"_DataId_"+dataId+"_1");
			}
		}

		//==== remove the duplicate
		LinkedHashSet<String> finalReportList = new LinkedHashSet<String>();
		//finalReportList.addAll(ReportList);
		finalReportList.addAll(reportNameList);
		ReportList.clear();
		ReportList.addAll(reportNameList);

		//System.out.println("final Report List : "+finalReportList);
		return finalReportList;
	}

	//==== fetch the HTML report
	public static String getReport(String fileName){
		String filePath = reportLogLoaction+"\\"+fileName;
		System.out.println(filePath);
		String report = "";
		String line = "";
		try{
			FileReader inputFile = new FileReader(filePath);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			while ((line = bufferReader.readLine()) != null)   {
				report = report+line.trim();
			}
			bufferReader.close();
		}catch(Exception e){
			System.out.println("Error while reading file line by line:" + e.getMessage());                      
		}
		return report;
	}
	//==== fetch the data from report's HTML Tags
	public static String getStringFromReport(String report, String firstString, String secondString){
		String middleString = "";
		String[] splitReport = report.split(firstString);
		String[] splitAgainReport = splitReport[1].split(secondString);
		middleString = splitAgainReport[0];
		return middleString;
	}
	//===== Creating the final Report
	public static String CreateMainReport(ArrayList<String> reportHeader, ArrayList<ArrayList<String>> reportBody){
		int totalTcs=0;
		int noOfPassed=0;
		int noOfFailed=0;
		String createReportBody = "";
		String newFileName="";
		for(int i=0; i<reportBody.size(); i++){
			String reportbody = ReportBody;
			String fileName=reportBody.get(i).get(0);
			//System.out.println("fileName=="+fileName);
			if(fileName.contains(("_1."))){
				//System.out.println("filename==="+fileName.replace("_1.", "_PrimaryExecution."));
				newFileName=fileName.replace("_1.", "_PrimaryExecution.");
				totalTcs++;
			}else if(fileName.contains(("_2."))){
				//System.out.println("filename=222=="+fileName.replace("_2.", "_SecondayExecution."));
				newFileName=fileName.replace("_2.", "_SecondayExecution.");
			}
			if("Passed".equalsIgnoreCase(reportBody.get(i).get(5))){
				noOfPassed++;
			}
			
			createReportBody =  createReportBody 
					+ reportbody.replace("$$SNo$$", String.valueOf(i+1))
					.replace("$$DeatiledExecutionReportFileLink$$", reportBody.get(i).get(0))
					.replace("$$FileName$$", newFileName)
					.replace("$$TestCaseName$$", reportBody.get(i).get(1))
					.replace("$$BrowserType$$", reportBody.get(i).get(2))
					.replace("$$ExecutionTime$$", reportBody.get(i).get(3))
					.replace("$$StatusColor$$", reportBody.get(i).get(4)) // green, red
					.replace("$$Status$$", reportBody.get(i).get(5));     // Pass,fail
					//.replace("$$ScreenShotName$$", reportBody.get(i).get(6));
		}
		noOfFailed=totalTcs-noOfPassed;
		System.out.println("totalTcs=="+totalTcs);
		System.out.println("noOfPassed=="+noOfPassed);
		System.out.println("noOfFailed=="+noOfFailed);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		float passPercentage=(noOfPassed*100.0f)/totalTcs ;
		Double passPer = Double.parseDouble(decimalFormat.format(passPercentage));
		//System.out.println("passPer==="+passPer);
		float failPercentage=(noOfFailed*100.0f)/totalTcs ;
		Double failedPer = Double.parseDouble(decimalFormat.format(failPercentage));
		//System.out.println("failedPer"+failedPer);
		
		ReportHeader = ReportHeader.replace("$$TestExecutionStartTime$$", reportHeader.get(0))
				.replace("$$TestExecutionEndTime$$", reportHeader.get(1))
				.replace("$$TotalExecutionTime$$", reportHeader.get(2))
				.replace("$$NumberOfTestScript$$", String.valueOf(totalTcs))
				.replace("$$NumberOfPASSED$$", noOfPassed+"("+passPer+"%)")
				.replace("$$NumberOfFAILED$$", noOfFailed+"("+failedPer+"%)");
		String MainReport = ReportHeader+createReportBody+ReportFooter;

		return MainReport;

	}

	
	//HTML
	public static String ReportHeader = "<html>" +
			"<head>" +
			"<link href='..\\css\\style.css' rel='stylesheet' type='text/css' />" +
			"</head>" +
			"<hr class='divline'>" +
			"<table class='reportheader' width=100%>" +
			"<TR>" +
			"<td height=50px align=left>" +
			"<Table class='developer'>" +
			"<TR>" +
			"<td class='desc1'>" +
			"<Center>" +
			"<table>" +
			"<TR>" +
			"<TD class='desccpy'>Automation TestExecution Report</TD>" +
			"</TR>" +
			"<TR>" +
			"<TD class='dev'>Tool Used : Selenium WebDriver </TD>" +
			"</TR>" +
			"</Table>" +
			"</TD>" +
			"</TR>" +
			"</Table>" +
			"</TD>" +
			"<BR>" +
			"<td height=50pxalign=right><img src = '..\\Images\\cisco_logo.jpg'>" +
			"</td>" +
			"<BR>" +
			"</tr>" +
			"</table>" +
			"<hr class='divline'>" +
			"<BR>" +
			"<table class='subheader' width=100%>" +
			"<tr>" +
			"<td width=100% class='subheader'>Test Execution Summary</td>" +
			"</tr>" +
			"<tr>" +
			"<td width=100% class='subcontents'>" +
			"</td>" +
			"</tr>" +
			"</tr>" +
			"</table>" +
			"<hr class='divline'>" +
			"<br>" +
			"<table>" +
			"<tr>" +
			"<td>" +
			"<table class = 'releasesummary'>" +
			"<tr>" +
			"<td class='summaryheader'colspan=2>Execution Time Summary</td>" +
			"</tr>" +
			"<tr>" +
			"<td class ='summaryelement' width=100>Test Execution Start Time</td>" +
			"<td class ='summarybody'width=450>$$TestExecutionStartTime$$</td>" +
			"</tr>" +
			"<tr>" +
			"<td class ='summaryelement' width=100>Test Execution End Time</td>" +
			"<td class ='summarybody'width=450>$$TestExecutionEndTime$$</td>" +
			"</tr>" +
			"<tr>" +
			"<td class ='summaryelement'>Total Execution Time</td>" +
			"<td class ='summarybody'>$$TotalExecutionTime$$</td>" +
			"</tr>" +
			"</table>" +
			"</td>" +
			"<td>" +
			"</td>" +
			"<td>" +
			"<table class = 'releasesummary'>" +
			"<tr>" +
			"<td class='summaryheader'colspan=2>Execution Details Summary</td>" +
			"</tr>" +
			"<tr>" +
			"<td class ='summaryelement' width=100> # Total Test Scripts</td>" +
			"<td class ='summarybody'width=450>$$NumberOfTestScript$$</td>" +
			"</tr>" +
			"<tr>" +
			"<td class ='summaryelement'>TCs PASSED#(%)</td>" +
			"<td class ='summarybody'>$$NumberOfPASSED$$</td>" +
			"</tr>" +
			"<tr>" +
			"<td class ='summaryelement'>TCs Failed#(%)</td>" +
			"<td class ='summarybody'>$$NumberOfFAILED$$</td></tr></table>" +
			"</td>" +
			"</tr>" +
			"</table>" +
			"<br/><br/>"+
			"<hr class='divline'>" +
			"<BR>" +
			"<table class='subheader'>" +
			"<tr>" +
			"<td class='subheader' align=center >Total Scripts Execution</td>" +
			"</td>" +
			"<tr>" +
			"<td class='subcontents'>" +
			"</td>" +
			"</tr>" +
			"</table>" +
			"" +
			"<table class='teststeps'>" +
			"<tr>" +
			"<td class='tsheader' width=75px>S.No</td>" +
			"<td class='tsheader' width=100px>Test Case</td>" +
			"<td class='tsheader' width=100px>Browser Type</td>" +
			"<td class='tsheader' width=100px>Execution Time</td>" +
			"<td class='tsheader' width=100px>Status</td>" +			
			"</tr>" ;

	public static String ReportBody =
			"<tr>" +
			"<td class='summarybody' >$$SNo$$</td>" +
			"<td class='summarybody' ><a href='logs\\$$DeatiledExecutionReportFileLink$$'>$$FileName$$</a></td>" +
			"<td class='summarybody' >$$BrowserType$$</td>" +
			"<td class='summarybody' >$$ExecutionTime$$</td>" +
			"<td class='summarybody' ><p style=font-family:verdana;type:bold;color:$$StatusColor$$;><b>$$Status$$</b></p></td>" +
			"</tr>";

	public static String ReportFooter = "</table>" +
			"</br>" +
			"</br>" +
			"<hr class='divline'>" +
			"<br>" +
			"<center>" +
			"<span>CISCO CONFIDENTIAL - Copyright &copy; www.cisco.com </span></center></body></html>" ;
	
	
	public static String longToTime(long time){
		//			int mil = (int) ((time % 1000));
		int sec = (int) ((time/1000) % 60);
		int min = (int) ((time/1000) / 60);
		String timeTaken = "";
		if(sec/10 < 1 && min/10 < 1)
			timeTaken = "0"+min+":"+"0"+sec;
		else if(sec/10 < 1)
			timeTaken = min+":"+"0"+sec;
		else if(min/10 < 1)
			timeTaken = "0"+min+":"+sec;
		else
			timeTaken = min+":"+sec;

		return timeTaken;
	}
	
	
}