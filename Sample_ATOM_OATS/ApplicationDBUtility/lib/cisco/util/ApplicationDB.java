package lib.cisco.util;

import static lib.cisco.util.DBUtill.resultMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.cisco.util.AppTestDTO;
import lib.cisco.util.DBConnectionManager;
import lib.cisco.util.variables;

public class ApplicationDB extends DBUtill{
	
	/*
	 * Method : getTestData
	 * This method is used to get the test Data of each of the testCase
	 * Returns a map with test Method name and the input data
	 */
	
	public Map<String, List<AppTestDTO>>getTestData(String releaseId,String appId,String cycle) {

		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		String testMethodName = null;
		Connection connection = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		Map<String, List<AppTestDTO>> appTestDataMap = new HashMap<String, List<AppTestDTO>>();
		List<AppTestDTO> appDTOListData = null;
		AppTestDTO appDTO = null;
		String query="";
		String testScriptIds="";
		String mastertrackTable="";
		try {
			connection = DBConnectionManager.getConnection();
			if (null != connection) {
				if(failedDataIdsList.size() == 0){
					List<String> testIds=getTestScriptIdsForAllExecution(connection,releaseId,appId,cycle);
					for(String id:testIds){
						testScriptIds +=id+",";
					}
				}
				if(testScriptIds.length() > 0){
					testScriptIds=testScriptIds.substring(0, testScriptIds.length()-1);
				}
				mastertrackTable=PropertiesFileReader.getProperty("mastertracktable");
				String tableQuery="select EXECUTION_TABLE,TRACK_ID from "+mastertrackTable+" where APP_ID="+appId;
				pstmt1= connection.prepareStatement(tableQuery);
				resultSet1=pstmt1.executeQuery();
				if (resultSet1.next()) {
					tableName=resultSet1.getString("EXECUTION_TABLE");
				}
				System.out.println("TestScript IDs to fetech all the testdata==="+testScriptIds);
				dataIds="";
				if(failedDataIdsList.size() > 0){
					for(String dataId:failedDataIdsList){
						dataIds +=dataId+",";
					}
					if(dataIds.length() > 0){
						dataIds=dataIds.substring(0, dataIds.length()-1);
					}
					System.out.println("Failed dataIds=="+dataIds);
					query="select * from "+tableName+" where DATA_ID in ("+dataIds+") and EXECUTION='Y' order by DATA_ID";
					System.out.println("Secondary DATA Sql Query :" + query);
				}else{
					query="select * from "+tableName+" where TEST_SCRIPT_ID in ("+testScriptIds+") and EXECUTION='Y' order by DATA_ID";
					System.out.println("Primary DATA Sql Query :" + query);
				}
				
				//Get ResultSet
				pstmt=connection.prepareStatement(query);
				//pstmt.setString(1, testCaseScriptIds);
				resultSet = pstmt.executeQuery();
				
				while (resultSet.next()) {
					
					testMethodName = resultSet.getString("TEST_METHOD_NAME");
					if (null != testMethodName && !"".equalsIgnoreCase(testMethodName)) {

						if (appTestDataMap.get(testMethodName) == null) {
							appDTOListData = new ArrayList<AppTestDTO>();
							appDTO = setAndGetTestDataDTO(resultSet);
							appDTOListData.add(appDTO);
							appTestDataMap.put(testMethodName, appDTOListData);

						} else {
							appDTOListData = (List<AppTestDTO>) appTestDataMap.get(testMethodName);
							appDTO = setAndGetTestDataDTO(resultSet);
							appDTOListData.add(appDTO);
						}
					}
				}
				//System.out.println("TestData Map Size   : "  + appTestDataMap.size());
			
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
				//testDataResultSet.close();
			DBConnectionManager.close(connection);
			DBConnectionManager.close(pstmt);
			DBConnectionManager.close(pstmt1);
			DBConnectionManager.close(resultSet);
			DBConnectionManager.close(resultSet1);
			testCaseScriptIds="";
		}
		return appTestDataMap;

	}


	/**
	 * @param testDataResultSet
	 * @return data object which the input data of a particular testCase
	 * @throws SQLException
	 */
	public AppTestDTO setAndGetTestDataDTO(ResultSet resultSet)throws SQLException {
		AppTestDTO appDto;
		appDto = new AppTestDTO();
		/*---------------Mandatory columns---------------*/
		appDto.setDataId(resultSet.getString("DATA_ID"));
        appDto.setTestScriptId(resultSet.getString("TEST_SCRIPT_ID"));
        appDto.setTestClassName(resultSet.getString("TEST_CLASS_NAME"));
        appDto.setTestCaseName(resultSet.getString("TEST_CLASS_NAME"));
        appDto.setTestMethodName(resultSet.getString("TEST_METHOD_NAME"));
        appDto.setBrowserVersion(resultSet.getString("BROWSER_VERSION"));
        appDto.setOsType(resultSet.getString("OS_TYPE"));
        appDto.setBrowserType(resultSet.getString("BROWSER_TYPE"));
        appDto.setInvocationMethod(resultSet.getString("INVOCATION_METHOD"));
        /*---------------Add your Application specific input columns here-------------*/
        appDto.setUrl(resultSet.getString("I_URL"));
        appDto.setUserid(resultSet.getString("I_USERNAME"));
        appDto.setPassword(resultSet.getString("I_PASSWORD"));
        appDto.setPDFSearchkey(resultSet.getString("I_PDFSEARCHKEY"));
        
        
		return appDto;
	}
	/*
	 * Method : getTestDataForDataId
	 * This method is used to get the test Data for a particular data Id from the application data table
	 */
	public synchronized AppTestDTO getTestDataForDataId(){
		ResultSet testdataResultSet = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		Connection connection = null;		
		String executionQuery="";
		AppTestDTO appData=null;
		
		try {
			try {
				
				connection = DBConnectionManager.getConnection();
			
			if (null != connection) {
				System.out.println("tableName=="+variables.getTableName());
				System.out.println("dataIds=="+variables.getScriptTestData());
				executionQuery="select * from "+variables.getTableName()+" where DATA_ID='"+variables.getScriptTestData()+"'";
				pstmt1=connection.prepareStatement(executionQuery);
				System.out.println("getting test Input data from dataId : Sql Query :" + executionQuery);
				testdataResultSet = pstmt1.executeQuery();
				if(testdataResultSet!=null)
					System.out.println("Result set not null");
				while (testdataResultSet.next()) {
				appData=setAndGetTestDataDTO(testdataResultSet);
				}
			}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}finally {
			try {
				testdataResultSet.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
				DBConnectionManager.close(testdataResultSet);
				DBConnectionManager.close(connection);
				DBConnectionManager.close(pstmt1);
				DBConnectionManager.close(pstmt);
				
		}
		
		return appData;
	}
	/*
	 * Method : ResultMapAndDBUpdate
	 * This method gets the OutputBean form the testMethod and updates the resultMap with all the Execution Results
	 * The resultMap will update the DB Execution table
	 * 
	 */
	public synchronized void ResultMapAndDBUpdate(OutputBean ob,int trackId,int releaseId,int appId,String startTime,String EndTime,AppTestDTO appdata){
		ob.setStartDate(startTime);
		ob.setEndDate(EndTime);
		ob.setAppId(appId);
		ob.setReleaseId(releaseId);
		ob.setTrackId(trackId);
		
		ob.setDataId(appdata.getDataId());
		ob.setTestScriptId(appdata.getTestScriptId());
		ob.setExecutedBy(System.getProperty("user.name"));
		ob.setBrowserType(appdata.getBrowserType());
		ob.setBrowserVersion(appdata.getBrowserVersion());
		ob.setOsType(appdata.getOsType());
		System.out.println("Updating results");
		resultMap.clear();
		resultMap.put(appdata.getDataId().toString(),ob);
		System.out.println("result map updated as:"+resultMap);
		System.out.println("Updating DB for :"+appdata.getDataId());
		DBUtill.updateTestResults(resultMap);
		System.out.println("DB updated");
	}
	
}
