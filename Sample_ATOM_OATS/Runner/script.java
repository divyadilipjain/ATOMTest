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
public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	public static String releaseId;
	public static String appId;
	public static String cycle;
	public static List<Integer> MyDataIdsList=new ArrayList<Integer>();
	
	Thread t = null;
	
	String className="";
	String ScriptclassObj[]=null;
	Set<String> methodSet=null;
	String methodName="";
	Iterator<AppTestDTO> methodDto=null;
	//OutputBean ob=null;
	static List<Thread> threadList=new ArrayList<Thread>();
	static int startExecId=0;
	static int endExecId=0;
	public static Map<String, List<AppTestDTO>> testDataMap = null;
	public static Map<String, List<AppTestDTO>> testDataMap1 = null;
	Map<Integer,Integer> countmap=null;
	ApplicationDB applicationDB=new lib.cisco.util.ApplicationDB();
	@FunctionLibrary("Scenario_Login") lib.cisco.testScripts.Scenario_Login scenario_Login;
	@FunctionLibrary("Scenario_Navigation") lib.cisco.testScripts.Scenario_Navigation scenario_Navigation;
	@FunctionLibrary("ApplicationDBUtility") lib.cisco.util.ApplicationDBUtility applicationDBUtility;
	/*
	 * Method:Initialize
	 * All the inputs required to trigger the execution of testcases, are hardcoded in this method
	 */
	public void initialize() throws Exception {
		
		appId="7001";
		releaseId="40001";
		cycle="CI";
		MyDataIdsList.clear();
		ApplicationDB applicationDB=new ApplicationDB();
	}
		
	/**
	 * Method: run
	 * This method gets the testDataMap and triggers the primary and Secondary execution of TestCases
	 */
	public void run() throws Exception {
		
		startExecId=DBUtill.getNextExecId();
		System.out.println("=============================Primary Execution Started=================================");
		System.out.println("Execution Id before primary execution:"+startExecId);
		Map<Integer,Integer> countmap=DBUtill.getSequenceParallelTestCases(releaseId,appId,cycle);
		testDataMap=applicationDB.getTestData(releaseId,appId,cycle);
		System.out.println("Count map in Primary:"+countmap);
		primaryExecution(countmap,testDataMap);
		endExecId=DBUtill.getNextExecId();
		System.out.println("=============================Primary Execution Completed=================================");
		System.out.println("Execution Id before primary execution:"+endExecId);
		threadList.clear();
		testDataMap.clear();
		countmap.clear();
		DBUtill.failedDataIdsList.clear();
		System.out.println("MyDataList after primary execution:"+MyDataIdsList);
		DBUtill.Update_Execution_Flag_To_Y(MyDataIdsList);
		DBUtill.failedDataIdsList=DBUtill.getFailedDataList(MyDataIdsList, startExecId,endExecId);
	    System.out.println("failedDataIdsList for Secondary Execution=="+DBUtill.failedDataIdsList);
	    if(DBUtill.failedDataIdsList.size() > 0){
	    	System.out.println("==============Secondary Execution Started===========");
	    	
	    	countmap=DBUtill.getSequenceParallelTestCases(releaseId,appId,cycle);
	    	System.out.println("Count map in Secondary:"+countmap);
			testDataMap=applicationDB.getTestData(releaseId,appId,cycle);
			secondaryExecution(countmap,testDataMap);
			System.out.println("==============Secondary Execution Completed===========");
	    }

	}
	/*
	 * Method: primaryExecution
	 * This method will get the sequence of testCases will all the data Ids for that testCase from the application data table 
	 * This triggers the sequential or parallel execution of testCases depending on the sequence count
	 */
	public  void primaryExecution(Map<Integer,Integer> countmap,Map<String, List<AppTestDTO>> testDataMap)throws Exception{
		
		Iterator it = countmap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        int parallelCount=(Integer)pairs.getValue();
	        int sequenceNumber=(Integer)pairs.getKey();
	        System.out.println("Sequnce Number=="+sequenceNumber + " and Corrsponding count = " +parallelCount);
	        if(parallelCount == 1){
	        	final Map<String, Set<String>> testCasesMap = DBUtill.getTestCaseNamesForPrimaryExecution(releaseId,appId,cycle,sequenceNumber);
	        	executeSequential(testCasesMap);
	        }else{
	        	System.out.println("=========Parallel Execution Started=======");
	        	final Map<String, Set<String>> testCasesMap = DBUtill.getTestCaseNamesForPrimaryExecution(releaseId,appId,cycle,sequenceNumber);
	        	executeParallel(testCasesMap,testDataMap);
				for(int i=0;i < threadList.size() ;i++){
					threadList.get(i).join();
				}
				System.out.println("==============Parallel Execution Completed===========");
	        }
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
	}
	/*
	 * Method: secondary Execution
	 * This method will start the secondary execution of failed dataIds
	 * This triggers the sequential or parallel execution of testCases depending on the sequence count
	 */
	public  void secondaryExecution(Map<Integer,Integer> countmap,Map<String, List<AppTestDTO>> testDataMap)throws Exception{
		
		Iterator it = countmap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        int parallelCount=(Integer)pairs.getValue();
	        int sequenceNumber=(Integer)pairs.getKey();
	        System.out.println("Sequnce Number=="+sequenceNumber + " and Corrsponding count = " +parallelCount);
	        if(parallelCount == 1){
	        	final Map<String, Set<String>> testCasesMap = DBUtill.getTestCaseNamesForSecondaryExecution(releaseId,appId,cycle,sequenceNumber);
	        	executeSequential(testCasesMap);
	        }else{
	        	System.out.println("=========Parallel Execution Started=======");
	        	final Map<String, Set<String>> testCasesMap = DBUtill.getTestCaseNamesForSecondaryExecution(releaseId,appId,cycle,sequenceNumber);
	        	executeParallel(testCasesMap,testDataMap);
				for(int i=0;i < threadList.size() ;i++){
					threadList.get(i).join();
				}
				System.out.println("==============Parallel Execution Completed===========");
	        }
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
	}
	
	/*
	 * Method : executeParallel
	 * To execute the testCases in parallel. 
	 * This method will create a thread for each testCase which have the same sequence number in the release planning table
	 * The thread's run() method will follow the command line execution of ParallelScriptCall script which inturn calls the corresponding script and method of testCase
	 */
public void executeParallel(Map<String, Set<String>> testCasesMap,Map<String, List<AppTestDTO>> testDataMap)throws Exception{
		
		int i=0;
		final Set<String> methodSet = new HashSet<String>();
		String ScriptclassObj[] = new String[testCasesMap.keySet().size()];
		for(Iterator iterator = testCasesMap.keySet().iterator(); iterator.hasNext(); i++)
		{
			
			String className = (String) iterator.next();
			ScriptclassObj[i] = className.trim();
			methodSet.addAll(testCasesMap.get(className));
			System.out.println(methodSet);
			
			if(methodSet.size() > 0){
				for (String methodName : methodSet) {
					System.out.println("Class Name=="+ScriptclassObj[i]+"==Method name=="+methodName);
					List<AppTestDTO> appDataList = null;
					List<AppTestDTO> testData = new ArrayList<AppTestDTO>();
					System.out.println("TEST data in parallel:"+testDataMap);
					if(null != methodName && !"".equalsIgnoreCase(methodName)){
						appDataList = new LinkedList<AppTestDTO>();
						if(null != testDataMap){
							appDataList = testDataMap.get(methodName);
							System.out.println("appDataList=="+appDataList.toString());
							for (AppTestDTO appDto : appDataList) {
								testData.add( appDto );
								
								System.out.println("Starting Main Thread...");
								System.out.println("Data Id in driver:"+appDto.getDataId());
								System.out.println("DbUtil table NAme:"+DBUtill.tableName);
								System.out.println("DBUtil track Id:"+DBUtill.trackId);
								
						        MyRunnableThread mrt = new MyRunnableThread(ScriptclassObj[i],methodName,appDto.getDataId(),DBUtill.tableName,Integer.toString(DBUtill.trackId),DBUtill.CurrentAppId,DBUtill.CurrentreleaseId);
						        System.out.println("my Runnable thread mrt created");
						        Thread t = new Thread(mrt);
						       System.out.println("new thread t created");
						        threadList.add(t);
						        
						        System.out.println("Thread added to list");
						        MyDataIdsList.add(Integer.parseInt(appDto.getDataId()));
						        t.setName(appDto.getDataId());
						        t.start();
								Thread.sleep(5000);
				}
			}
		}
				}
			}
		}
	}
	/*
	 * Method : executeSequential
	 * To execute the testCases in Sequential manner
	 * All the testCases with different sequence numbers will be executed in Sequential in the increasing order of Sequence number
	 */
	public void executeSequential(final Map<String, Set<String>> testCasesMap) throws Exception {
		
			final Set<String> methodSet = new HashSet<String>();	
			String ScriptclassObj[] = new String[testCasesMap.keySet().size()];
			System.out.println("In runSequential");
			System.out.println("test case MAp:"+testCasesMap);
			
			int i = 0;
			for (Iterator iterator = testCasesMap.keySet().iterator(); iterator.hasNext(); i++) {
				
				String className = (String) iterator.next();
				System.out.println("Class name:"+ className);
				ScriptclassObj[i] = className.trim();
				System.out.println("Script class:"+ScriptclassObj[i]);
				methodSet.addAll(testCasesMap.get(className));
				System.out.println(methodSet);
				
				System.out.println("MethodSet :"+ methodSet.size());
			
				if(methodSet.size() > 0){
					for (String methodName : methodSet) {
						System.out.println("Class Name=="+ScriptclassObj[i]+"==Method name=="+methodName);
						List<AppTestDTO> appDataList = null;
						List<AppTestDTO> testData = new ArrayList<AppTestDTO>();
						System.out.println("TEST data in Seq:"+testDataMap);
						if(null != methodName && !"".equalsIgnoreCase(methodName)){
							appDataList = new LinkedList<AppTestDTO>();
							if(null != testDataMap){
								appDataList = testDataMap.get(methodName);
								System.out.println("appDataList=="+appDataList.toString());
								for (AppTestDTO appDto : appDataList) {
									testData.add( appDto );
									variables.setAppId(appId);
									variables.setTableName(DBUtill.tableName);
									variables.setReleaseId(releaseId);
									variables.setTrackId(DBUtill.trackId);
									variables.setScriptTestData(appDto.getDataId());
									variables.setScriptClassName(ScriptclassObj[i]);
									MyDataIdsList.add(Integer.parseInt(appDto.getDataId()));
									System.out.println("Getting variables:"+variables.getAppId()+":"+variables.getTableName());
									getScript(className).callFunction(methodName);
									
			}
			
			}
		}
		
	}
}

}
	}
	

			
	public void finish() throws Exception {
	}
}
