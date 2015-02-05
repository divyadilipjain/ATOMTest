package lib.cisco.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;




public abstract class DBUtill {
	
	public static String tableName=null;
	public static String testCaseScriptIds="";
	public static List<String> failedDataIdsList=new ArrayList<String>();
	public static List<String> failedTestScriptIdsList=new LinkedList<String>();
	public static String dataIds="";
	public static String CurrentAppId;
	public static String CurrentreleaseId;
	public static int trackId;
	public static Map<String, OutputBean> resultMap = new HashMap<String, OutputBean>();
	public static String releasePlanTable="";
	public static String mastertrackTable="";
	
	private final static Logger LOGGER = Logger.getLogger(DBUtill.class.getName()); 
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  getTestData
	 * @Description : Abstract Method, need to override in ApplicationDB.java file
	 * @Description : This method is used to get the application data from the Application table and storing in AppTestDTO.
	 * @return Map<String, List<AppTestDTO>>
	 * @version 1.0
	 ********************************************************************************************/
	public abstract Map<String, List<AppTestDTO>>getTestData(String releaseId,String appId,String cycle);
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  getSequenceParallelTestCases
	 * @Description : This will fetch the Sequence and count for sequence and parallel execution 
	 * @return Map<Integer, Integer>
	 * @version 1.0
	 ********************************************************************************************/	
	public static Map<Integer, Integer> getSequenceParallelTestCases(String releaseId,String appId,String cycle) {
		
		ResultSet resultSet = null;
		int count = 0;
		int sequenceNumber =0;
		Connection connection = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		ResultSet resultSet1=null;
		Map<Integer, Integer> appTestDataMap = new LinkedHashMap<Integer, Integer>();
		String query="";
		
		try {
			
			connection = DBConnectionManager.getConnection();
			releasePlanTable=PropertiesFileReader.getProperty("releasePlaningtable");
			mastertrackTable=PropertiesFileReader.getProperty("mastertracktable");
			System.out.println("releasePlanTable=="+releasePlanTable);
			if (null != connection) {
				String tableQuery="select EXECUTION_TABLE,TRACK_ID from "+mastertrackTable+" where APP_ID=?";
				pstmt1= connection.prepareStatement(tableQuery);
				pstmt1.setInt(1, Integer.parseInt(appId));
				resultSet1=pstmt1.executeQuery();
				if (resultSet1.next()) {
					tableName=resultSet1.getString("EXECUTION_TABLE");
					trackId=resultSet1.getInt("TRACK_ID");
				}
				if(!DBUtill.failedDataIdsList.isEmpty()){
					dataIds="";
					for(String dataId:failedDataIdsList){
						dataIds +=dataId+",";
					}
					if(dataIds.length() > 0){
						dataIds=dataIds.substring(0, dataIds.length()-1);
					}
					
					if("ALL".equalsIgnoreCase(cycle)){
						query="select  EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where TEST_SCRIPT_ID in (select Distinct(TEST_SCRIPT_ID) from "+tableName+" where DATA_ID in ("+dataIds+")) AND RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") group by EXECUTION_SEQUENCE order by EXECUTION_SEQUENCE";
						//System.out.println("Secodary Sequence wise query :" + query);
					}else{
						query="select  EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where TEST_SCRIPT_ID in (select Distinct(TEST_SCRIPT_ID) from "+tableName+" where DATA_ID in ("+dataIds+")) AND RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") and CYCLE in ('"+cycle+"') group by EXECUTION_SEQUENCE order by EXECUTION_SEQUENCE";
						//System.out.println("Secodary Sequence wise query :" + query);
					}
					
				}else{
					if("ALL".equalsIgnoreCase(cycle)){
						query="select  EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where  RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") group by EXECUTION_SEQUENCE  order by EXECUTION_SEQUENCE";
						System.out.println("Primary Sequence wise query :" + query);
					}else{
						query="select EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where  RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") and CYCLE IN ('"+cycle+"') group by EXECUTION_SEQUENCE  order by EXECUTION_SEQUENCE";
						System.out.println("Primary Sequence wise query :" + query);
					}
				}
				//Get ResultSet
				pstmt=connection.prepareStatement(query);
				resultSet = pstmt.executeQuery();
			
				while (resultSet.next()) {
					sequenceNumber = resultSet.getInt("EXECUTION_SEQUENCE");
					count = resultSet.getInt("EXECUTION_SEQUENCE2");
					if (count !=0 ) {
						appTestDataMap.put(sequenceNumber, count);
					}
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		} finally {
			DBConnectionManager.close(connection);
			DBConnectionManager.close(pstmt);
			DBConnectionManager.close(pstmt1);
			DBConnectionManager.close(resultSet);
			DBConnectionManager.close(resultSet1);
			
		}
		return appTestDataMap;

	}
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  getSequenceParallelTestCasesForTestScriptIds
	 * @Description : This will fetch the Sequence and count for sequence and parallel execution 
	 * @return Map<Integer, Integer>
	 * @version 1.0
	 ********************************************************************************************/	
	public static Map<Integer, Integer> getSequenceParallelTestCasesForTestScriptIds(String releaseId,String appId,String cycle,String testScriptIds) {
		
		ResultSet resultSet = null;
		int count = 0;
		int sequenceNumber =0;
		Connection connection = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		ResultSet resultSet1=null;
		Map<Integer, Integer> appTestDataMap = new LinkedHashMap<Integer, Integer>();
		String query="";
		releasePlanTable=PropertiesFileReader.getProperty("releasePlaningtable");
		mastertrackTable=PropertiesFileReader.getProperty("mastertracktable");
		try {
			
			connection = DBConnectionManager.getConnection();
			
			if (null != connection) {
				String tableQuery="select EXECUTION_TABLE,TRACK_ID from "+mastertrackTable+" where APP_ID=?";
				pstmt1= connection.prepareStatement(tableQuery);
				pstmt1.setString(1, appId);
				resultSet1=pstmt1.executeQuery();
				if (resultSet1.next()) {
					tableName=resultSet1.getString("EXECUTION_TABLE");
					trackId=resultSet1.getInt("TRACK_ID");
				}
				if(!DBUtill.failedDataIdsList.isEmpty()){
					dataIds="";
					for(String dataId:failedDataIdsList){
						dataIds +=dataId+",";
					}
					if(dataIds.length() > 0){
						dataIds=dataIds.substring(0, dataIds.length()-1);
					}
					if("ALL".equalsIgnoreCase(cycle)){
						query="select  EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where TEST_SCRIPT_ID in (select Distinct(TEST_SCRIPT_ID) from "+tableName+" where DATA_ID in ("+dataIds+")) AND RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") group by EXECUTION_SEQUENCE order by EXECUTION_SEQUENCE";
						System.out.println("Secodary Sequence wise query :" + query);
					}else{
						query="select  EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where TEST_SCRIPT_ID in (select Distinct(TEST_SCRIPT_ID) from "+tableName+" where DATA_ID in ("+dataIds+")) AND RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") and CYCLE in ('"+cycle+"') group by EXECUTION_SEQUENCE order by EXECUTION_SEQUENCE";
						System.out.println("Secodary Sequence wise query :" + query);
					}
					
				}else{
					if("ALL".equalsIgnoreCase(cycle)){
						query="select  EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where  RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") group by EXECUTION_SEQUENCE  order by EXECUTION_SEQUENCE";
						System.out.println("Primary Sequence wise query :" + query);
					}else{
						query="select EXECUTION_SEQUENCE,count(1) as EXECUTION_SEQUENCE2  from "+releasePlanTable+" where  RELEASE_ID in ("+releaseId+") and APP_ID in ("+appId+") and CYCLE IN ('"+cycle+"') and TEST_SCRIPT_ID IN ("+testScriptIds+") group by EXECUTION_SEQUENCE  order by EXECUTION_SEQUENCE";
						System.out.println("Primary Sequence wise query :" + query);
					}
				}
				//Get ResultSet
				pstmt=connection.prepareStatement(query);
				resultSet = pstmt.executeQuery();
			
				while (resultSet.next()) {
					sequenceNumber = resultSet.getInt("EXECUTION_SEQUENCE");
					count = resultSet.getInt("EXECUTION_SEQUENCE2");
					if (count !=0 ) {
						appTestDataMap.put(sequenceNumber, count);
					}
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		} finally {
			DBConnectionManager.close(connection);
			DBConnectionManager.close(pstmt);
			DBConnectionManager.close(pstmt1);
			DBConnectionManager.close(resultSet);
			DBConnectionManager.close(resultSet1);
			
		}
		return appTestDataMap;

	}


	public  static ResultSet getResultSet(Connection connection,String query) throws SQLException{
		Statement statement = null;
		ResultSet resultSet = null;
			if (null != connection) {
				if (null != query && !"".equalsIgnoreCase(query)) {
					statement = connection.createStatement();
					resultSet = statement.executeQuery(query);
				}
				statement = null;
			}
		return resultSet;
	}
	
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  getTestCaseNamesForPrimaryExecution
	 * @Description : This method will provide the sequence number and the count will provide from the release planning table
	 * @Description : This method is used to get the application data from the Application table and storing in AppTestDTO.
	 * @return Map<String, Set<String>>
	 * @version 1.0
	 ********************************************************************************************/
	
	public static Map<String, Set<String>> getTestCaseNamesForPrimaryExecution(String releaseId,String appId,String cycle,int sequenceNumber){
		ResultSet testCaseNameResultSet = null;
		ResultSet resultSet1=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		Connection connection = null;		
		Set<String> testMethodSet = null;
		String testClassName = null;
		String executionQuery="";
		CurrentAppId=appId;
		CurrentreleaseId=releaseId;
		Map<String, Set<String>> appTestCaseNamesMap = new LinkedHashMap<String, Set<String>>();
	
		try {
			connection = DBConnectionManager.getConnection();
			if (null != connection) {
					List<String> testScriptIds=getTestScriptIdsSequence(connection,releaseId,appId,cycle,sequenceNumber);
					for(String id:testScriptIds){
						testCaseScriptIds +=id+",";
					}
				/*mastertrackTable=PropertiesFileReader.getProperty("mastertracktable");
				String tableQuery="select EXECUTION_TABLE,TRACK_ID from "+mastertrackTable+" where APP_ID="+appId;
				pstmt= connection.prepareStatement(tableQuery);
				resultSet1=pstmt.executeQuery();
				if (resultSet1.next()) {
					tableName=resultSet1.getString("EXECUTION_TABLE");
					trackId=resultSet1.getInt("TRACK_ID");
				}*/
				if(testCaseScriptIds.length() > 0){
					testCaseScriptIds=testCaseScriptIds.substring(0, testCaseScriptIds.length()-1);
				}
				executionQuery="select DISTINCT TEST_CLASS_NAME,TEST_METHOD_NAME,DATA_ID from "+tableName+" where  TEST_SCRIPT_ID in ("+testCaseScriptIds+") and EXECUTION='Y' order by DATA_ID";
				
				pstmt1=connection.prepareStatement(executionQuery);
				System.out.println("Testcase Names for Primary execution Query :" + executionQuery);
				testCaseNameResultSet = pstmt1.executeQuery();
				while (null != testCaseNameResultSet && testCaseNameResultSet.next()) {
					if (null != testCaseNameResultSet.getString(1)
							&& !"".equalsIgnoreCase(testCaseNameResultSet.getString(1))
							&& null != testCaseNameResultSet.getString(2)
							&& !"".equalsIgnoreCase(testCaseNameResultSet.getString(2))) {

						testClassName = testCaseNameResultSet.getString(1);
						
						if (appTestCaseNamesMap.get(testClassName) == null) {
							testMethodSet = new HashSet<String>();
							testMethodSet.add(testCaseNameResultSet.getString(2));

							appTestCaseNamesMap.put(testClassName, testMethodSet);
						} else {
							testMethodSet = (HashSet<String>) appTestCaseNamesMap.get(testClassName);
							testMethodSet.add(testCaseNameResultSet.getString(2));
							
						}
					}
				}
				System.out.println(" TestCaseNamesMap to run the scripts   : "  + appTestCaseNamesMap.size());
			}
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally {
				DBConnectionManager.close(connection);
				DBConnectionManager.close(pstmt1);
				DBConnectionManager.close(pstmt);
				DBConnectionManager.close(testCaseNameResultSet);
				DBConnectionManager.close(resultSet1);
				testCaseScriptIds="";
				
		}
		
		return appTestCaseNamesMap;
	}
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  getTestCaseNamesForSecondaryExecution
	 * @Description : This method will provide the sequence number and the count will provide from the release planning table
	 * @Description : This method is used to get the application data from the Application table and storing in AppTestDTO.
	 * @return Map<String, Set<String>>
	 * @version 1.0
	 ********************************************************************************************/
	
	public static Map<String, Set<String>> getTestCaseNamesForSecondaryExecution(String releaseId,String appId,String cycle,int sequenceNumber){
		ResultSet testCaseNameResultSet = null;
		ResultSet resultSet1=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		Connection connection = null;		
		Set<String> testMethodSet = null;
		String testClassName = null;
		String executionQuery="";
		CurrentAppId=appId;
		CurrentreleaseId=releaseId;
		Map<String, Set<String>> appTestCaseNamesMap = new LinkedHashMap<String, Set<String>>();
	
		try {
			connection = DBConnectionManager.getConnection();
			if (null != connection) {
					List<String> testScriptIds=getTestScriptIdsSequence(connection,releaseId,appId,cycle,sequenceNumber);
					for(String id:testScriptIds){
						testCaseScriptIds +=id+",";
					}
				/*mastertrackTable=PropertiesFileReader.getProperty("mastertracktable");
				String tableQuery="select EXECUTION_TABLE,TRACK_ID from "+mastertrackTable+" where APP_ID="+appId;
				pstmt= connection.prepareStatement(tableQuery);
				resultSet1=pstmt.executeQuery();
				if (resultSet1.next()) {
					tableName=resultSet1.getString("EXECUTION_TABLE");
					trackId=resultSet1.getInt("TRACK_ID");
				}*/
				if(testCaseScriptIds.length() > 0){
					testCaseScriptIds=testCaseScriptIds.substring(0, testCaseScriptIds.length()-1);
				}
				if(failedDataIdsList.size() > 0){
					System.out.println("failed dataIds in secondary Execution==="+dataIds);
					executionQuery="select DISTINCT TEST_CLASS_NAME,TEST_METHOD_NAME,DATA_ID from "+tableName+" where  TEST_SCRIPT_ID in ("+testCaseScriptIds+") and DATA_ID IN ("+dataIds+") and EXECUTION='Y' order by DATA_ID";
				}
				pstmt1=connection.prepareStatement(executionQuery);
				System.out.println("Testcase Names secondary execution Query :" + executionQuery);
				testCaseNameResultSet = pstmt1.executeQuery();
				while (null != testCaseNameResultSet && testCaseNameResultSet.next()) {
					if (null != testCaseNameResultSet.getString(1)
							&& !"".equalsIgnoreCase(testCaseNameResultSet.getString(1))
							&& null != testCaseNameResultSet.getString(2)
							&& !"".equalsIgnoreCase(testCaseNameResultSet.getString(2))) {

						testClassName = testCaseNameResultSet.getString(1);
						
						if (appTestCaseNamesMap.get(testClassName) == null) {
							testMethodSet = new HashSet<String>();
							testMethodSet.add(testCaseNameResultSet.getString(2));

							appTestCaseNamesMap.put(testClassName, testMethodSet);
						} else {
							testMethodSet = (HashSet<String>) appTestCaseNamesMap.get(testClassName);
							testMethodSet.add(testCaseNameResultSet.getString(2));
							
						}
					}
				}
				System.out.println(" TestCaseNamesMap to run the scripts   : "  + appTestCaseNamesMap.size());
			}
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally {
				DBConnectionManager.close(connection);
				DBConnectionManager.close(pstmt1);
				DBConnectionManager.close(pstmt);
				DBConnectionManager.close(testCaseNameResultSet);
				DBConnectionManager.close(resultSet1);
				testCaseScriptIds="";
				
		}
		
		return appTestCaseNamesMap;
	}

	/********************************************************************************************
	 *  
	 * @Function_Name :  getTestScriptIdsSequence
	 * @Description : This method will provide the TestscriptIds need to execute base on the  sequence number and the inputs provided
	 * @return List<String>
	 * @version 1.0
	 ********************************************************************************************/
	public static List<String> getTestScriptIdsSequence(Connection con,String releaseId,String appId,String cycle,int sequnce){
		List<String> testcaseIdsList=new LinkedList<String>();
		Statement stmt=null;
		ResultSet resultSet=null;
		String testScriptId="";
		String sequenceQuery="";
		String testScriptIds="";
		try{
			stmt=con.createStatement();
			
			if("ALL".equalsIgnoreCase(cycle)){
				sequenceQuery="select * from "+releasePlanTable+" e1 where RELEASE_ID ="+releaseId+" and APP_ID="+appId+" and EXECUTION_SEQUENCE="+sequnce;
				//System.out.println("SequenceQuery Query=="+sequenceQuery);
			}else{
				sequenceQuery="select * from "+releasePlanTable+" e1 where RELEASE_ID ="+releaseId+" and APP_ID="+appId+" and cycle in ('"+cycle+"') and EXECUTION_SEQUENCE="+sequnce;
				//System.out.println("SequenceQuery Query=="+sequenceQuery);
			}
			
			resultSet= stmt.executeQuery(sequenceQuery);
			while (resultSet.next()) {
				testScriptId = resultSet.getString("TEST_SCRIPT_ID");
				
				if (null != testScriptId && !"".equalsIgnoreCase(testScriptId)) {
					testcaseIdsList.add(testScriptId);
					testScriptIds +=testScriptId+",";
				}
			}
			System.out.println("testScriptIds=="+testScriptIds);
		}catch(Exception e){
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally{
				DBConnectionManager.close(stmt);
				DBConnectionManager.close(resultSet);
		}
		return testcaseIdsList;
	}
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  getTestScriptIdsForAllExecution
	 * @Description : This method will provide the all TestscriptIds for the current execution
	 * @return List<String>
	 * @version 1.0
	 ********************************************************************************************/	
	public static List<String> getTestScriptIdsForAllExecution(Connection con,String releaseId,String appId,String cycle){
		List<String> testcaseIdsList=new LinkedList<String>();
		Statement stmt=null;
		ResultSet resultSet=null;
		String testScriptId="";
		String sequenceQuery="";
		String testScriptIds="";
		try{
			stmt=con.createStatement();
			
			if(cycle.equalsIgnoreCase("ALL")){
				sequenceQuery="select * from "+releasePlanTable+"  e1 where RELEASE_ID ="+releaseId+" and APP_ID="+appId;
				//System.out.println("TestScriptIdsForAllExecution Query=="+sequenceQuery);
			}else{
				sequenceQuery="select * from "+releasePlanTable+"  e1 where RELEASE_ID ="+releaseId+" and APP_ID="+appId+" and cycle in ('"+cycle+"')";
				//System.out.println("TestScriptIdsForAllExecution Query=="+sequenceQuery);
			}
			
			resultSet= stmt.executeQuery(sequenceQuery);
			while (resultSet.next()) {
				testScriptId = resultSet.getString("TEST_SCRIPT_ID");
				
				if (null != testScriptId && !"".equalsIgnoreCase(testScriptId)) {
					testcaseIdsList.add(testScriptId);
					testScriptIds +=testScriptId+",";
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally{
				DBConnectionManager.close(stmt);
				DBConnectionManager.close(resultSet);
			
		}
		return testcaseIdsList;
	}
	
	/********************************************************************************************
	 *  
	 * @Function_Name :  addLogResultToMap
	 * @Description : This method is used to create a map to store all the results for the execution
	 * * @param applicationDataId - Data id of the test script
	 * * @param testScriptId - Tests script id
	 * * @param browserType - BrowserType
	 * * @param browserType - BrowserVersion
	 * * @param browserType - OsType
	 * * @param status - Status of the test case
	 * * @param startTime - Start Time
	 * * @param startTime - End Time
	 * @version 1.0
	 ********************************************************************************************/
     
	public synchronized static void addLogResultToMap(String applicationDataId,String testScriptId,String browserType,String browserVersion,String osType,String status,String startTime,String endTime,String errorMsg) {
 		try {
				OutputBean  outputBean = new OutputBean();
				String executedBy = System.getProperty("user.name");
				System.out.println("ExecutedBy=="+executedBy);
				outputBean.setDataId(applicationDataId);
				outputBean.setTestScriptId(testScriptId);
				outputBean.setExecutedBy(executedBy);
				outputBean.setStartDate(startTime);
				outputBean.setEndDate(endTime);
				if (status.equalsIgnoreCase("Passed")) {
					errorMsg = "";
				} else {					
					if(errorMsg.length() > 2000){
						errorMsg=errorMsg.substring(0,2000);
					}
				}
				outputBean.setStatus(status);
				outputBean.setErrorMsg(errorMsg);
				outputBean.setBrowserType(browserType);
				outputBean.setBrowserVersion(browserVersion);
				outputBean.setOsType(osType);
				outputBean.setAppId(Integer.parseInt(CurrentAppId));
				outputBean.setTrackId(trackId);
				outputBean.setReleaseId(Integer.parseInt(CurrentreleaseId));
				
				resultMap.put(applicationDataId, outputBean);
 		} catch (Exception e) {
 			//e.printStackTrace();
 			LOGGER.log(Level.SEVERE, "Exception :",e);
 		}
 	}
	
	@SuppressWarnings("unchecked")
 	public static void updateTestResults(Map<String, OutputBean> resultMap) {
		Connection con=null;
		String query1="";
		PreparedStatement preparedStmt=null;
		Statement stmt1=null;
		ResultSet resultSet1=null;
		int execId=0;
		String executionTable="";
 		try {
 			con = DBConnectionManager.getConnection();
			if(null != con ){
	 			executionTable=PropertiesFileReader.getProperty("executionTable");
				System.out.println("executionTable=="+executionTable);
				query1="insert into "+executionTable+"(EXEC_ID,TRACK_ID,APP_ID,TEST_SCRIPT_ID,O_EXECUTION_STATUS,START_DATE_TIME,END_DATE_TIME,RELEASE_ID,CYCLE,EXECUTION_TYPE,EXECUTED_BY,ERROR_LOG_PATH,I_INPUTDATA,O_OUTPUTDATA,BROWSER_TYPE,BROWSER_VERSION,OS_TYPE) values (?,?,?,?,?,?,?,?,'TCB','REG',?,'git/automation/portfolio/track/app/error.log',?,?,?,?,?)";
				stmt1 =con.createStatement();
 				//for (int i=0;i< resultMap.size();i++) {
 					Iterator iterator = resultMap.entrySet().iterator();
 					while(iterator.hasNext()){
 						resultSet1 = stmt1.executeQuery("SELECT atom_gen_seq.NEXTVAL FROM DUAL");	
 						if(resultSet1.next()){
 							execId=resultSet1.getInt(1);
 							System.out.println("execId=="+execId);
 						}
 						Map.Entry mapEntry=(Map.Entry)iterator.next();
 						String key = (String) mapEntry.getKey();
 						OutputBean outputBean =(OutputBean) mapEntry.getValue();
 						System.out.println("data Id=="+outputBean.getDataId());
 						System.out.println("Status=="+outputBean.getStatus());

 						SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
 						Date date1 = formater.parse(outputBean.getStartDate());
 						Date date2= formater.parse(outputBean.getEndDate());
 						java.sql.Timestamp tcStartTime = new java.sql.Timestamp(date1.getTime());
 						java.sql.Timestamp tcEndTime = new java.sql.Timestamp(date2.getTime());

 						if("Failed".equalsIgnoreCase(outputBean.getStatus())){
 							failedDataIdsList.add(outputBean.getDataId());
 							failedTestScriptIdsList.add(outputBean.getTestScriptId());
 						}
 						preparedStmt = con.prepareStatement(query1);

 						preparedStmt.setInt (1, execId);
 						preparedStmt.setInt (2, outputBean.getTrackId());
 						preparedStmt.setInt (3, outputBean.getAppId());
 						preparedStmt.setString (4, outputBean.getTestScriptId());
 						preparedStmt.setString (5, outputBean.getStatus());
 						preparedStmt.setTimestamp(6, tcStartTime);
 						preparedStmt.setTimestamp(7, tcEndTime);
 						preparedStmt.setInt(8, outputBean.getReleaseId());
 						preparedStmt.setString(9, outputBean.getExecutedBy());
 						preparedStmt.setString(10, outputBean.getDataId());
 						preparedStmt.setString(11,outputBean.getErrorMsg());
 						preparedStmt.setString(12,outputBean.getBrowserType());
 						preparedStmt.setString(13,outputBean.getBrowserVersion());
 						preparedStmt.setString(14,outputBean.getOsType());
 						preparedStmt.addBatch();
 						int[] counts = preparedStmt.executeBatch();
 						con.commit();
 						System.out.println("Total " + counts.length	+ " Records had been updated in Execution Table");
 						DBConnectionManager.close(preparedStmt);
 						iterator.remove(); // avoids a ConcurrentModificationException
 					}
 				//}
 				System.out.println("failedDataIdsList=="+failedDataIdsList);
 				
 			}
 		} catch (Exception e) {
 			//e.printStackTrace();
 			LOGGER.log(Level.SEVERE, "Exception :",e);
 		}finally {
 				DBConnectionManager.close(con);
 				DBConnectionManager.close(preparedStmt);
 				DBConnectionManager.close(stmt1);
 				DBConnectionManager.close(resultSet1);
 		}
 	}//End of Method
	
	/********************************************************************************************
	 * @Function_Name :  updateTestResults
	 * @Description : This method is used to Update the test results into execution table for each exection.
	 * * @param Map<String, OutputBean> resultMap , map contain Application dataId and corresponding OutputBean
	 * @return void
	 ********************************************************************************************/
	/*@SuppressWarnings("unchecked")
 	public static void updateTestResults(Map<String, OutputBean> resultMap) {
		Connection con=null;
		String query1="";
		PreparedStatement preparedStmt=null;
		Statement stmt1=null;
		ResultSet resultSet1=null;
		int execId=0;
		String executionTable="";
 		try {
 			
 			con = DBConnectionManager.getConnection();
 			
 			executionTable=PropertiesFileReader.getProperty("executionTable");
			System.out.println("executionTable=="+executionTable);
			query1="insert into "+executionTable+"(EXEC_ID,TRACK_ID,APP_ID,TEST_SCRIPT_ID,O_EXECUTION_STATUS,START_DATE_TIME,END_DATE_TIME,RELEASE_ID,CYCLE,EXECUTION_TYPE,EXECUTED_BY,ERROR_LOG_PATH,I_INPUTDATA,O_OUTPUTDATA,BROWSER_TYPE,BROWSER_VERSION,OS_TYPE) values (?,?,?,?,?,?,?,?,'TCB','REG',?,'git/automation/portfolio/track/app/error.log',?,?,?,?,?)";
			if(null!=con){
				System.out.println("resultMap=="+resultMap);
				stmt1 =con.createStatement();
 				for (Iterator iterator = resultMap.keySet().iterator(); iterator.hasNext();) {
 					resultSet1 = stmt1.executeQuery("SELECT atom_gen_seq.NEXTVAL FROM DUAL");	
 					if(resultSet1.next()){
 						execId=resultSet1.getInt(1);
 						System.out.println("execId=="+execId);
 					}
 					String methodName = (String) iterator.next();
 					OutputBean outputBean = resultMap.get(methodName);
 					System.out.println("data Id=="+outputBean.getDataId());
 					System.out.println("Status=="+outputBean.getStatus());
 					
 					SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
 					Date date1 = formater.parse(outputBean.getStartDate());
 					Date date2= formater.parse(outputBean.getEndDate());
 					java.sql.Timestamp tcStartTime = new java.sql.Timestamp(date1.getTime());
 					java.sql.Timestamp tcEndTime = new java.sql.Timestamp(date2.getTime());
 					
 					if("Failed".equalsIgnoreCase(outputBean.getStatus())){
				    	failedDataIdsList.add(outputBean.getDataId());
				    	failedTestScriptIdsList.add(outputBean.getTestScriptId());
				    }
 					preparedStmt = con.prepareStatement(query1);
					preparedStmt.setInt (1, execId);
				    preparedStmt.setInt (2, outputBean.getTrackId());
				    preparedStmt.setInt (3, outputBean.getAppId());
				    preparedStmt.setString (4, outputBean.getTestScriptId());
				    preparedStmt.setString (5, outputBean.getStatus());
				    preparedStmt.setTimestamp(6, tcStartTime);
				    preparedStmt.setTimestamp(7, tcEndTime);
				    preparedStmt.setInt(8, outputBean.getReleaseId());
				    preparedStmt.setString(9, outputBean.getExecutedBy());
				    preparedStmt.setString(10, outputBean.getDataId());
				    preparedStmt.setString(11,outputBean.getErrorMsg());
				    preparedStmt.setString(12,outputBean.getBrowserType());
				    preparedStmt.setString(13,outputBean.getBrowserVersion());
				    preparedStmt.setString(14,outputBean.getOsType());
				    
				    preparedStmt.addBatch();
				    int[] counts = preparedStmt.executeBatch();
	 				con.commit();
	 				System.out.println("failedDataIdsList=="+failedDataIdsList);
	 				System.out.println("Total " + counts.length	+ " Records had been updated in Execution Table");
	 				DBConnectionManager.close(preparedStmt);
 				}
 				int[] counts = preparedStmt.executeBatch();
 				con.commit();
 				System.out.println("failedDataIdsList=="+failedDataIdsList);
 				System.out.println("Total " + counts.length	+ " Records had been updated in Execution Table");
 				DBConnectionManager.close(preparedStmt);
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally {
 				DBConnectionManager.close(con);
 				if(preparedStmt != null){
 					DBConnectionManager.close(preparedStmt);
 				}
 				DBConnectionManager.close(stmt1);
 				DBConnectionManager.close(resultSet1);
 		}
 	}*///End of Method

/*
 * This method is used to get the next value of execution ID from the ATOM sequence of execution
 */
public static int getNextExecId(){
		
		ResultSet resultSet=null;
		Statement stmt=null;
		Connection connection = null;		
		int nextExecId=0;
		String executionQuery="";

		try {
			connection = DBConnectionManager.getConnection();
				stmt= connection.createStatement();
				resultSet = stmt.executeQuery("SELECT atom_gen_seq.NEXTVAL FROM DUAL");
				if (resultSet.next()) {
					nextExecId=resultSet.getInt(1);
				}
			
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally {
				DBConnectionManager.close(connection);
				DBConnectionManager.close(stmt);
				DBConnectionManager.close(resultSet);
				
		}
		
		return nextExecId;
	}

	/*
	 * Method : getMaxExecId
	 * This method is used to get the maximum of all execution IDs from the Execution table
	 */
	public static int getMaxExecId(){
		
		ResultSet resultSet=null;
		PreparedStatement pstmt=null;
		
		Connection connection = null;		
		int execId=0;
		String executionQuery="";
		String executionTable=PropertiesFileReader.getProperty("executionTable");
		String tableQuery="select max(Exec_Id) as Max_Id from "+executionTable;
		try {
				connection = DBConnectionManager.getConnection();
				pstmt= connection.prepareStatement(tableQuery);
				resultSet=pstmt.executeQuery();
				if (resultSet.next()) {
					execId=Integer.parseInt(resultSet.getString("Max_Id"));
					
				}
				
			
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally {
				DBConnectionManager.close(connection);
				DBConnectionManager.close(pstmt);
				DBConnectionManager.close(resultSet);
				
		}
		
		return execId;
	}

	/*
	 * Method : getFailedDataList
	 * This method is used to get the failed data Ids which will go  for secondary execution
	 * This will get the data from the execution table
	 */
	public static List<String> getFailedDataList(List<Integer> MyDataIdsList,int startExecId,int endExecId){
		
		ResultSet resultSet=null;
		PreparedStatement pstmt=null;
		dataIds="";
		Connection connection = null;		
		List<String> failedTestScriptIdsList=new LinkedList<String>();
		String failedId = null;
		String executionQuery="";
		
		try {
			connection = DBConnectionManager.getConnection();
			String executionTable=PropertiesFileReader.getProperty("executionTable");
			if (null != connection) {
				dataIds="";
				if(MyDataIdsList.size() > 0){
					
					for(int dataId:MyDataIdsList){
						dataIds +=dataId+",";
					}
					
					if(dataIds.length() > 0){
						dataIds=dataIds.substring(0, dataIds.length()-1);
					}
					System.out.println("dataIds in getFailedDataList=="+dataIds);
					executionQuery="select Distinct(I_INPUTDATA) from "+executionTable+" where I_INPUTDATA in ("+dataIds+") and O_EXECUTION_STATUS in ('Failed') and EXEC_ID>="+startExecId+" and EXEC_ID<="+endExecId;
				}
				pstmt=connection.prepareStatement(executionQuery);
				
				
				System.out.println("getFailed Sql Query :" + executionQuery);
				resultSet = pstmt.executeQuery();
				while (null != resultSet && resultSet.next()) {
					if (null != resultSet.getString("I_INPUTDATA")
							&& !"".equalsIgnoreCase(resultSet.getString("I_INPUTDATA"))
							) {

						failedTestScriptIdsList.add(resultSet.getString("I_INPUTDATA"));
						
						
					}
				}
				System.out.println(" Failed Data List in getFailedList   : "  + failedTestScriptIdsList);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally {
				DBConnectionManager.close(connection);
				DBConnectionManager.close(pstmt);
				DBConnectionManager.close(resultSet);
		}
		
		return failedTestScriptIdsList;
	}
	
    public static void Update_Execution_Flag_To_Y(List<Integer> MyDataIdsList) throws Exception
    {
		System.out.println("----------------------In update flag to y---------------------");
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		dataIds = "";
		Connection connection = null;
		String failedId = null;
		String executionQuery = "";
		try {
			connection = DBConnectionManager.getConnection();

			if (null != connection) {
				dataIds = "";
				if (MyDataIdsList.isEmpty()) {

					for (int dataId : MyDataIdsList) {
						dataIds += dataId + ",";
					}

					if (dataIds.length() > 0) {
						dataIds = dataIds.substring(0, dataIds.length() - 1);
					}
					System.out.println("Failed data Ids for updating flag=="
							+ dataIds);
					executionQuery = "Update " + tableName
							+ " set Execution='Y' where DATA_ID in (" + dataIds
							+ ")";
				}
				pstmt = connection.prepareStatement(executionQuery);

				System.out.println("getFailed Sql Query :" + executionQuery);
				int i = pstmt.executeUpdate();
				System.out.println("------------------------updated to y-------------------i:"+ i);

			}

		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		} finally {
			DBConnectionManager.close(connection);
			DBConnectionManager.close(pstmt);
			DBConnectionManager.close(resultSet);

		}

	}

    /********************************************************************************************
	 *  
	 * @Function_Name :  getTestCaseNamesForAdhocTesting
	 * @Description : This method will provide the testCase details required for Adhoc testing 
	 * @Description : It takes the portfolio,Application,trackId for Adhoc testing
	 * @return Map<String, Set<String>>
	 * @version 1.0
	 ********************************************************************************************/
	
	public static Map<String, Set<String>> getTestCaseNamesForAdhocTesting(String portfolio,String appId,String trackId,String testScriptIdsList){
		ResultSet testCaseNameResultSet = null;
		ResultSet resultSet1=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		Connection connection = null;		
		Set<String> testMethodSet = null;
		String testClassName = null;
		String executionQuery="";
		
		Map<String, Set<String>> appTestCaseNamesMap = new LinkedHashMap<String, Set<String>>();
		mastertrackTable=PropertiesFileReader.getProperty("mastertracktable");
		System.out.println("Master table in getTestCaseNamesForAdhocTesting:"+mastertrackTable);
		try {
			connection = DBConnectionManager.getConnection();
			
			if (null != connection) {
					
					String tableQuery="select Execution_TABLE from "+mastertrackTable+" where TRACK_ID="+trackId+" and PORTFOLIO='"+portfolio+"' and APP_ID='"+appId+"'";
					pstmt= connection.prepareStatement(tableQuery);
					resultSet1=pstmt.executeQuery();
					if (resultSet1.next()) {
						tableName=resultSet1.getString("EXECUTION_TABLE");
					}
			
				executionQuery="select DISTINCT TEST_CLASS_NAME,TEST_METHOD_NAME,DATA_ID from "+tableName+" where  TEST_SCRIPT_ID in ("+testScriptIdsList+") order by DATA_ID";
				
				pstmt1=connection.prepareStatement(executionQuery);
				System.out.println("Testcase Names for adhoc testing Query :" + executionQuery);
				testCaseNameResultSet = pstmt1.executeQuery();
				while (null != testCaseNameResultSet && testCaseNameResultSet.next()) {
					if (null != testCaseNameResultSet.getString(1)
							&& !"".equalsIgnoreCase(testCaseNameResultSet.getString(1))
							&& null != testCaseNameResultSet.getString(2)
							&& !"".equalsIgnoreCase(testCaseNameResultSet.getString(2))) {

						testClassName = testCaseNameResultSet.getString(1);
						
						if (appTestCaseNamesMap.get(testClassName) == null) {
							testMethodSet = new HashSet<String>();
							testMethodSet.add(testCaseNameResultSet.getString(2));

							appTestCaseNamesMap.put(testClassName, testMethodSet);
						} else {
							testMethodSet = (HashSet<String>) appTestCaseNamesMap.get(testClassName);
							testMethodSet.add(testCaseNameResultSet.getString(2));
							
						}
					}
				}
				System.out.println(" TestCaseNamesMap to run the scripts   : "  + appTestCaseNamesMap.size());
			}
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}finally {
				DBConnectionManager.close(connection);
				DBConnectionManager.close(pstmt1);
				DBConnectionManager.close(pstmt);
				DBConnectionManager.close(testCaseNameResultSet);
				DBConnectionManager.close(resultSet1);
				testCaseScriptIds="";
				
		}
		
		return appTestCaseNamesMap;
	}
	
	
}//End of class.


