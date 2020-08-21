package queryLibrary;

public class Queries {

	/**
	 ******************************************************************************************************************************************************************************************* 
	 * 
	 * 
	 * Public QUERIES
	 * 
	 * 
	 ********************************************************************************************************************************************************************************************
	 */
	// Application ID By name
	public static String getAppIdByName(String appname) {
		String s = "select Application_ID from Application_Master where Application_Name = '"+appname+"'";
		return s;
	}

	// Application Queries
	public static String getAllApplications = "select * from Application_Master";
	public static String getAppIds(String appName) {
		String getAllApplicationname = "select Application_ID from Application_Master where Application_Name='"+appName+"'";
		return getAllApplicationname;
	}
	public static String getAppIds1(String appName) {
		String getAllApplicationname = "select * from Application_Master where Application_Name='"+appName+"'";
		return getAllApplicationname;
	}
	public static String getAllApplications(int UID) {
		String getAllApplication = "select a.Application_Name, a.Application_Id from Application_Master a left join Application_User_Mapper b on a.Application_ID=b.App_Application_ID where b.App_user_Reg_ID="+UID;
		return getAllApplication;
	}
	public static String addNewApplicationRequest = "insert into [performancelighthouseserver].[dbo].[Application_Request_Mapper] ([Request_App_Name],[Request_App_By_Reg_UserID], [Request_App_ApprovedBy_Reg_UserID], [Request_Status]) values (?,?,?,?)";
	public static String getAppNameWithStatus(int UID) {
		String q = "Select  Application_Name,Status from (select Application_ID, Application_Name,Status from ("+
				"select Application_ID, Application_Name,Status, ROW_NUMBER() over (partition by Application_Name  order by Status) as row_num  from (SELECT a.application_id,a.application_name,"+
				"case when (Request_App_By_Reg_UserID="+UID+") then "+
				"b.request_status else 'Take Access'"+
				"end as status from application_master a "+
				"left join Application_Request_Mapper b on "+
				"a.Application_Name=b.Request_App_Name) x )b where row_num=1 ) app union select Request_App_Name,Request_Status from Application_Request_Mapper where Request_App_By_Reg_UserID="+UID;
		return q;
	}
	public static String requestButton(int uID, String apName) {
		String s = "select * from Application_Request_Mapper where Request_App_By_Reg_UserID="+uID+" and Request_App_Name='"+ apName +"'";
		return s;
	}

	// Test cases
	public static String getTestCaseNamesByApplicationName = "select * from TestScenario_Master where TS_Application_ID=";
	public static String addTestCases = "insert into TestScenario_Master ([TS_Name], [TS_Application_ID], [TS_Reg_UserID], [TS_CreationTime]) values(?,?,?,?)";
	public static String insertIntoTestScenarioHistory = "insert into TestScenario_Master_History ([TS_U_TS_ID], [TS_U_TS_Name], [TS_U_TS_Application_ID], [TS_U_TS_Reg_UserID], [TS_U_Status], [TS_U_CreationTime]) values (?,?,?,?,?,?)";
	public static String getTestScenarioId(String timestamp, int uid, int appId, String Tsname) {
		String Id = "select TS_ID from TestScenario_Master where TS_Application_ID="+appId+ " and TS_Reg_UserID="+uid+ " and TS_CreationTime='"+timestamp+"' and TS_Name='"+Tsname+"'";
		return Id;
	}
	public static String getTestScenarioId(int appId, String Tsname) {
		String Id = "select TS_ID from TestScenario_Master where TS_Application_ID="+appId+" and TS_Name='"+Tsname+"'";
		return Id;
	}
	public static String updateTsMaster(int appId, int uid) {
		String ts = "update TestScenario_Master set TS_Reg_UserID="+uid+" where TS_ID="+appId;
		return ts;
	}
	public static String populateTableId(int Aid) {
		String r = "select a.TS_ID, a.TS_Name, b.Reg_UserName, a.TS_CreationTime, a.TS_Reg_UserID from TestScenario_Master a left join User_Registration b on a.TS_Reg_UserID = b.Reg_UserID where a.TS_Application_ID ="+Aid+" and TS_ID_FLAG=1";
		return r;
	}
	public static String ifUserHasAccessWhileBulkUpload = "select * from Application_User_Mapper where App_user_Reg_ID=";

	// update Test scenario
	public static String updateTestScenarioByUser(int tsID, String oldTSName, String newTSName, int UserID) {
		String s = "update TestScenario_Master set TS_Name='"+newTSName+"' , TS_Reg_UserID="+UserID+" where TS_ID = "+ tsID +" and TS_Name='"+oldTSName+"'";
		return s;
	}
	public static String authenticateUserName(int id) {
		String getUserId = "select * from [performancelighthouseserver].[dbo].[User_Registration] where Reg_UserID="+id;
		return getUserId;
	}
	// delete Test scenario
	public static String deleteTestScenarioByUser(int tsID) {
		String s = "update TestScenario_Master set TS_ID_FLAG=0 where TS_ID="+tsID;
		return s;
	}
	public static String authTS = "select * from [performancelighthouseserver].[dbo].[TestScenario_Master]";
	public static String authTS1 = "select * from [performancelighthouseserver].[dbo].[TestScenario_Master]";
	//public static String userNameById = "select Reg_UserName from User_Registration where Reg_UserID=";

	// BasePage Queries
	public static String baseTableOnload() {
		String authenticationTest = "select a.TestScenarioName, b.Application_NAME from TestScenario a inner join Application_Name b on a.TestScenarioID=b.TestScenarioID";
		return authenticationTest;
	}


	// Security Questions
	public static String askSecurityQuestion = "select * from [performancelighthouseserver].[dbo].[Security_Questions]";

	// Register an User
	public static String askRegisterUser = "select * from [performancelighthouseserver].[dbo].[User_Registration]";
	public static String registerUser() {
		String r = "INSERT INTO [performancelighthouseserver].[dbo].[User_Registration] ([Reg_F_Name],[Reg_L_Name],[Reg_UserName],[Reg_Email],[Reg_Password],[Reg_API_KEY],[Reg_Security_Qus_ID],[Reg_Security_Qus_Ans], [Reg_User_Type])VALUES(?,?,?,?,?,?,?,?,?)";
		return r;
	}

	// Login an User
	public static String loginUser(String email, String password) {
		String r = "select * from [performancelighthouseserver].[dbo].[User_Registration] where Reg_Email='"+email+"' and Reg_Password='"+password+"'";
		return r;
	}
	public static String loginUser1(int uid) {
		String r = "select * from [performancelighthouseserver].[dbo].[User_Registration] where Reg_UserId="+uid;
		return r;
	}

	// DropDown Queries
	public static String askApplicationname = "select * from [performancelighthouseserver].[dbo].[Application_Master]";
	public static String askPageName(int apId) {
		String s = "select  x.Page_ID, x.Page_Name from Page_Master x right join (select distinct a.Nav_page_ID as PGID from Navigation_Master a left join Application_Master b on b.Application_ID = a.Nav_Application_ID where b.Application_ID="+apId+" and b.Application_ID_Flag=1) y on y.PGID = x.Page_ID";
		return s;
	}
	public static String askTestScenerioName(int pgID, int appId) {
		String s = "select x.TS_ID, x.TS_Name from TestScenario_Master x right join (select distinct a.Nav_TS_ID as NVTS, a.Nav_Application_ID  from Navigation_Master a left join Page_Master b on a.Nav_Page_ID=b.Page_ID where b.Page_ID = "+pgID+" and a.Nav_Application_ID="+appId+") y on y.NVTS=x.TS_ID where x.TS_ID_FLAG=1";
		return s;
	}
	public static String astTestScenarioAll(String pgId, int appId) {
		String s = "select x.TS_ID, x.TS_Name, y.Nav_Application_ID from TestScenario_Master x right join (select distinct a.Nav_TS_ID as NVTS, a.Nav_Application_ID  from Navigation_Master a left join Page_Master b on a.Nav_Page_ID=b.Page_ID where b.Page_ID in ("+pgId+") and a.Nav_Application_ID="+appId+") y on y.NVTS=x.TS_ID where x.TS_ID_FLAG=1";
		return s;
	}
	// TC Graph with respect to Time on y axis
	public static String TcPageLoadGraphQuery(String applicationNo, String testCsNO,String dtStart, String dtEnd) {

		String q = "select page_name,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000,timedate from (select case when len(substring(page_name, 0,charindex('=', page_name, charindex('=',page_name,0)+1)))<>0 then substring(page_name, 0,charindex('=', page_name, charindex('=',page_name,0)+1)) else Substring(b.page_name, 0, len(b.Page_name)+1) end as Page_name, CONVERT(date, Nav_EntrySyetemTimes) as timedate, Nav_PageLoad from Navigation_Master n inner join Page_Master b on b.Page_ID=n.Nav_Page_ID where Nav_TS_ID in ("+testCsNO+") and CONVERT(date, Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Application_ID= "+applicationNo+" )a group by timedate,Page_name order by timedate asc";
		return q ;
	}
	public static String TcPageDateLoadGraphQuery(String applicationNo, String testCsNO,String dtStart, String dtEnd) {

		String q = "select CONVERT(date, Nav_EntrySyetemTimes) from Navigation_Master n inner join Page_Master b on b.Page_ID=n.Nav_Page_ID where Nav_TS_ID="+testCsNO+" and CONVERT(date, Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Application_ID= "+applicationNo+" group by CONVERT(date, Nav_EntrySyetemTimes) ";
		return q ;
	}
	// TEST CASE GRAPH
//	public static String actualTCGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart) {
//		String q = "select AVG(CONVERT(FLOAT,Nav_UnloadEvent))/1000, AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000, AVG(CONVERT(FLOAT,Nav_AppCache))/1000, AVG(CONVERT(FLOAT,Nav_TTFB))/1000,AVG(CONVERT(FLOAT,Nav_Processing))/1000,AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000,AVG(CONVERT(FLOAT,Nav_DomComplete))/1000,AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) = '" + dtStart +"'";
//		return q;
//	}
//	public static String baseTCGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart) {
//		String q = "select AVG(CONVERT(FLOAT,Nav_UnloadEvent))/1000, AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000, AVG(CONVERT(FLOAT,Nav_AppCache))/1000, AVG(CONVERT(FLOAT,Nav_TTFB))/1000,AVG(CONVERT(FLOAT,Nav_Processing))/1000,AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000,AVG(CONVERT(FLOAT,Nav_DomComplete))/1000,AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) = '" + dtStart +"'";
//		return q;
//	}
	//--------------------------------------------Part1--------------------------------------------------------------------------
	public static String askFusion(String startDate, String endDate, String page, String tcname) {
		//String askFusion = "select b.TS_Name,convert(date,a.Nav_EntrySyetemTimes), avg(convert(float,a.Nav_Processing)/1000) from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID where convert(date,a.Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.TS_ID in ("+tcname+") and a.Nav_Page_ID in ("+page+") group by convert(date,a.Nav_EntrySyetemTimes),b.TS_Name";
		String askFusion = "select b.TS_Name,convert(date,a.Nav_EntrySyetemTimes), avg(convert(float,a.Nav_Processing)/1000) from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID where convert(date,a.Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.TS_ID in ("+tcname+") and a.Nav_Page_ID in ("+page+") group by convert(date,a.Nav_EntrySyetemTimes),b.TS_Name";
		return askFusion;
	}
	
	public static String askFusionTS(String startDate, String endDate, String page, String tcname) {
		//String askFusion = "select b.TS_Name,convert(date,a.Nav_EntrySyetemTimes), avg(convert(float,a.Nav_Processing)/1000) from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID where convert(date,a.Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.TS_ID in ("+tcname+") and a.Nav_Page_ID in ("+page+") group by convert(date,a.Nav_EntrySyetemTimes),b.TS_Name";
		String askFusion = "select distinct b.TS_Name from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID where convert(date,a.Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.TS_ID in ("+tcname+") and a.Nav_Page_ID in ("+page+") and b.Ts_ID_Flag=1";
		return askFusion;
	}

	public static String askFusion1(String startDate, String endDate, String page, String tcname) {
		String askFusion = "select b.TS_Name,convert(date,a.Nav_EntrySyetemTimes), avg(convert(float,a.Nav_Processing)/1000) from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID where convert(date,a.Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.TS_ID in ("+tcname+") and a.Nav_Page_ID in ("+page+") group by convert(date,a.Nav_EntrySyetemTimes),b.TS_Name";

		return askFusion;
	}
	public static String askFusion2(String startDate, String endDate, String page, String tcname) {
		//String askFusion = "with src as(select b.TS_Name as TSName, avg(convert(float, Nav_Processing)/1000) as Nav_Processing, CONVERT(date,Nav_EntrySyetemTimes) as Nav_EntrySyetemTimes from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID   where  CONVERT(date,Nav_EntrySyetemTimes) >'2020-06-18' and b.TS_ID in ("+tcname+") and a.Nav_Page_ID in ("+page+")  group by b.TS_Name, CONVERT(date,Nav_EntrySyetemTimes)) select src.TSName, MIN(src.Nav_Processing) from src group by src.TSName";
		String askFusion = "with src as(select b.TS_Name as TSName, (convert(float, Nav_Processing)/1000) as Nav_Processing, CONVERT(date,Nav_EntrySyetemTimes) as Nav_EntrySyetemTimes from Navigation_Master a inner join TestScenario_Master b on b.TS_ID=a.Nav_TS_ID   where  CONVERT(date,Nav_EntrySyetemTimes) >'2020-06-18' and b.TS_ID in ("+tcname+") ) select src.TSName, MIN(src.Nav_Processing) from src group by src.TSName";
		return askFusion;
	}
	//--------------------------------------------Part1--------------------------------------------------------------------------
	//--------------------------------------------Part2--------------------------------------------------------------------------
	public static String actualTCGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart) {
		String q = "select AVG(CONVERT(FLOAT,Nav_Processing)/1000) from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+")";
		return q;
	}
	
	public static String baseTCGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart) {
		String q = "select AVG(CONVERT(FLOAT,Nav_Processing)/1000) from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in("+pageNO+")";
		return q;
	}
	public static String baseTCGraphQuery2(String applicationNo, String pageNO, String testCsNO,String dtStart) {
		// String q = "with src as(select avg(convert(float, Nav_Processing)/1000) as Nav_Processing, CONVERT(date,Nav_EntrySyetemTimes) as Nav_EntrySyetemTimes from Navigation_Master where CONVERT(date,Nav_EntrySyetemTimes) >'2020-06-18' and Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in("+pageNO+")  group by CONVERT(date,Nav_EntrySyetemTimes)) select MIN(src.Nav_Processing) from src";
		String q = "with src as(select (convert(float, Nav_Processing)/1000) as Nav_Processing, CONVERT(date,Nav_EntrySyetemTimes) as Nav_EntrySyetemTimes from Navigation_Master where CONVERT(date,Nav_EntrySyetemTimes) >'2020-06-18' and Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" ) select MIN(src.Nav_Processing) from src";
		return q;
	}
	
	//--------------------------------------------Part2--------------------------------------------------------------------------
	// Navigation Graph query
	public static String askNavGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select AVG(CONVERT(FLOAT,Nav_UnloadEvent))/1000, AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000, AVG(CONVERT(FLOAT,Nav_AppCache))/1000, AVG(CONVERT(FLOAT,Nav_TTFB))/1000,AVG(CONVERT(FLOAT,Nav_Processing))/1000,AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000,AVG(CONVERT(FLOAT,Nav_DomComplete))/1000,AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID="+pageNO+" and convert(Date,Nav_EntrySyetemTimes) between '" + dtStart +"' and '"+dtEnd+"'";
		return q;
	}
	public static String askNavAllGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select AVG(CONVERT(FLOAT,Nav_UnloadEvent))/1000, AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000, AVG(CONVERT(FLOAT,Nav_AppCache))/1000, AVG(CONVERT(FLOAT,Nav_TTFB))/1000,AVG(CONVERT(FLOAT,Nav_Processing))/1000,AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000,AVG(CONVERT(FLOAT,Nav_DomComplete))/1000,AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID = "+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '" + dtStart +"' and '"+dtEnd+"'";
		return q;
	}
	public static String AVGOFaskNavAllGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "with src as (select Nav_TS_ID, Nav_Application_ID, Nav_Page_ID, convert(Date,Nav_EntrySyetemTimes) as nav_entry, Avg(CONVERT(FLOAT,Nav_UnloadEvent))/1000 as avgs,  AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000 as avgs1, AVG(CONVERT(FLOAT,Nav_AppCache))/1000 as avgs2 , AVG(CONVERT(FLOAT,Nav_TTFB))/1000 as avgs3 , AVG(CONVERT(FLOAT,Nav_Processing))/1000 as avgs4 , AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000 as avgs5 , AVG(CONVERT(FLOAT,Nav_DomComplete))/1000 as avgs6, AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000 as avgs7, AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 as avgs8 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID = "+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' group by Nav_TS_ID, Nav_Application_ID, Nav_Page_ID, convert(Date,Nav_EntrySyetemTimes))select avg(avgs), avg(avgs1), avg(avgs2), avg(avgs3), avg(avgs4), avg(avgs5), avg(avgs6), avg(avgs7), avg(avgs8) from src";
		return q;
	}

	// Page Load Event Queries
	public static String askAveragePageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'";
		return q;
	}
	public static String askAverageAllPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'";
		return q;
	}
	public static String askMaximumPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select MAX(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'";
		return q;
	}
	public static String askMaximumAllPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select MAX(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'";
		return q;
	}
	public static String askMinimumPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select MIN(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'";
		return q;
	}
	public static String askMinimumAllPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd) {
		String q = "select MIN(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'";
		return q;
	}

	// WebElements/ Resources Query
	public static String askResources(String pgID, String tsID, String appID, String start, String end) {
		String s = "select distinct rm.Res_Name, avg(convert(float,rmh.RS_Res_Duration))/1000 as a from Resource_Master rm inner join Resource_Mapper_History rmh on rmh.RS_Res_ID = rm.Res_ID inner join  (select Nav_Id as NVIDS from Navigation_Master where Nav_Page_ID="+pgID+" and Nav_TS_ID in ("+tsID+") and Nav_Application_ID="+appID+" and convert(Date,Nav_EntrySyetemTimes) between '"+ start +"' and '"+end+"') y on y.NVIDS = rmh.RS_Nav_ID group by rm.Res_Name order by a desc";
		return s;
	}
	public static String askAllResources(String pgID, String tsID, String appID, String start, String end) {
		String s = "select distinct rm.Res_Name, avg(convert(float,rmh.RS_Res_Duration))/1000 as a from Resource_Master rm inner join Resource_Mapper_History rmh on rmh.RS_Res_ID = rm.Res_ID inner join  (select Nav_Id as NVIDS from Navigation_Master where Nav_Page_ID in ("+pgID+") and Nav_TS_ID in ("+tsID+") and Nav_Application_ID="+appID+" and convert(Date,Nav_EntrySyetemTimes) between '"+ start +"' and '"+end+"') y on y.NVIDS = rmh.RS_Nav_ID group by  rm.Res_Name order by a desc";
		return s;
	}
	public static String avgLine(String pgID, String tsID, String appID, String start, String end) {
		String s = "select avg(convert(float,x.a)) from (select rm.Res_ID, rm.Res_Name, convert(float,rmh.RS_Res_Duration)/1000 as a from Resource_Master rm inner join Resource_Mapper_History rmh on rmh.RS_Res_ID = rm.Res_ID inner join (select Nav_Id as NVIDS from Navigation_Master where Nav_Page_ID in ("+pgID+") and Nav_TS_ID in ("+tsID+") and Nav_Application_ID="+ appID +" and convert(Date,Nav_EntrySyetemTimes) between '"+start+"' and '"+end+"') y on y.NVIDS = rmh.RS_Nav_ID) x";
		return s;
	}

	// Page elements graph query
	public static String getPageElements(String pgID, String tsID, String appID, String start, String end) {
		
//		String s ="select page_name,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from (select case when CHARINDEX('?',b.Page_name)<>0 then Substring(b.page_name, 0, CHARINDEX('?',b.Page_name)) else Substring(b.page_name, 0, len(b.Page_name)+1) end as Page_name, CONVERT(date, Nav_EntrySyetemTimes) as timedate, Nav_PageLoad from Navigation_Master n inner join Page_Master b on b.Page_ID=n.Nav_Page_ID where Nav_TS_ID in ("+tsID+") and CONVERT(date, Nav_EntrySyetemTimes) between '"+start+"' and '"+end+"' and Nav_Application_ID= "+appID+" )a group by Page_name";
		String s = "select a.Page_Name, AVG(CONVERT(FLOAT,b.Nav_PageLoad))/1000 from Page_Master a right join Navigation_Master b on b.Nav_Page_ID=a.Page_ID where b.Nav_Application_ID="+appID+" and b.Nav_Page_ID in ("+pgID+") and b.Nav_TS_ID in ("+tsID+")  and convert(Date,b.Nav_EntrySyetemTimes) between '"+start+"' and '"+end+"' group by a.Page_Name";
		return s;
	}
	public static String dependentPage(String name) {
		String askFusion = "select avg(convert(float,Nav_PageLoad)/1000), convert(date,Nav_EntrySyetemTimes) as date from Navigation_Master m inner join Page_Master n on n.Page_ID=m.Nav_Page_ID where n.Page_Name like '%"+ name +"%' group by convert(date,Nav_EntrySyetemTimes)";
		return askFusion;
	}

	// Base Actual Graph
	public static String askFusion(String startDate, String endDate, String page) {
		//String askFusion = "select name, payment from DependentTable where convert(date,Dates) between '"+startDate+"' and '"+endDate+"' and name='"+custNm+"' group by name, Payment";
		String askFusion = "select b.Page_Name, avg(Convert(float, Nav_PageLoad))/1000 from Navigation_Master a inner join Page_Master b on b.Page_Id=a.Nav_Page_ID where convert(date,Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.Page_Id in ("+page+") group by b.Page_Name";

		return askFusion;
	}

	public static String askFusion1(String startDate, String endDate, String page) {
		//String askFusion = "select name, payment from DependentTable where convert(date,Dates) between '"+startDate+"' and '"+endDate+"' and name='"+custNm+"' group by name, Payment";
		String askFusion = "select b.Page_Name, avg(Convert(float, Nav_PageLoad))/1000 from Navigation_Master a inner join Page_Master b on b.Page_Id=a.Nav_Page_ID where convert(date,Nav_EntrySyetemTimes) between '"+startDate+"' and '"+endDate+"' and b.Page_Id in ("+page+") group by b.Page_Name";

		return askFusion;
	}
	/**
	 *******************************************************************************************************************************************************************************************  
	 * 
	 * 
	 * Public Login Controller Queries
	 * 
	 * 
	 ******************************************************************************************************************************************************************************************* 
	 * */

	// DropDown Queries
	public static String askPageName(int apId, int uid) {
		String s = "select  x.Page_ID, x.Page_Name from Page_Master x right join (select distinct a.Nav_page_ID as PGID from Navigation_Master a left join Application_Master b on b.Application_ID = a.Nav_Application_ID where b.Application_ID="+apId+" and a.Nav_Reg_ID="+uid+" and b.Application_ID_Flag=1) y on y.PGID = x.Page_ID";
		return s;
	}
	public static String askTestScenerioName1(int pgID, int appId, int uid) {
		String s = "select x.TS_ID, x.TS_Name from TestScenario_Master x right join (select distinct a.Nav_TS_ID as NVTS, a.Nav_Application_ID  from Navigation_Master a left join Page_Master b on a.Nav_Page_ID=b.Page_ID where b.Page_ID = "+pgID+" and a.Nav_Application_ID="+appId+" and a.Nav_Reg_ID="+uid+") y on y.NVTS=x.TS_ID where x.TS_ID_FLAG=1";
		return s;
	}
	public static String astTestScenarioAll1(String pgId, int appId, int uid) {
		String s = "select x.TS_ID, x.TS_Name, y.Nav_Application_ID from TestScenario_Master x right join (select distinct a.Nav_TS_ID as NVTS, a.Nav_Application_ID  from Navigation_Master a left join Page_Master b on a.Nav_Page_ID=b.Page_ID where b.Page_ID in ("+pgId+") and a.Nav_Application_ID="+appId+" and a.Nav_Reg_ID="+uid+") y on y.NVTS=x.TS_ID where x.TS_ID_FLAG=1";
		return s;
	}
	// WebElements/ Resources Query
	public static String askResources(String pgID, String tsID, String appID, String start, String end, int uid) {
		String s = "select distinct rm.Res_Name, avg(convert(float,rmh.RS_Res_Duration))/1000 as a from Resource_Master rm inner join Resource_Mapper_History rmh on rmh.RS_Res_ID = rm.Res_ID inner join (select Nav_Reg_ID as nv, Nav_Id as NVIDS from Navigation_Master where Nav_Page_ID="+pgID+" and Nav_TS_ID in ("+tsID+") and Nav_Application_ID="+appID+" and convert(Date,Nav_EntrySyetemTimes) between '"+ start +"' and '"+end+"') y on y.NVIDS = rmh.RS_Nav_ID where y.nv="+uid+" group by  rm.Res_Name order by a desc";
		return s;
	}
	public static String askAllResources(String pgID, String tsID, String appID, String start, String end, int uid) {
		String s = "select distinct rm.Res_Name, avg(convert(float,rmh.RS_Res_Duration))/1000 as a from Resource_Master rm inner join Resource_Mapper_History rmh on rmh.RS_Res_ID = rm.Res_ID inner join (select Nav_Reg_ID as nv, Nav_Id as NVIDS from Navigation_Master where Nav_Page_ID in ("+pgID+") and Nav_TS_ID in ("+tsID+") and Nav_Application_ID="+appID+" and convert(Date,Nav_EntrySyetemTimes) between '"+ start +"' and '"+end+"') y on y.NVIDS = rmh.RS_Nav_ID where y.nv="+uid+" group by  rm.Res_Name order by a desc";
		return s;
	}
	public static String avgLine(String pgID, String tsID, String appID, String start, String end, int uid) {
		String s = "select avg(convert(float,x.a))/1000 from (select rm.Res_ID, rm.Res_Name, rmh.RS_Res_Duration as a from Resource_Master rm inner join Resource_Mapper_History rmh on rmh.RS_Res_ID = rm.Res_ID inner join (select Nav_Reg_ID as nv, Nav_Id as NVIDS from Navigation_Master where Nav_Page_ID in ("+pgID+") and Nav_TS_ID in ("+tsID+") and Nav_Application_ID="+ appID +" and convert(Date,Nav_EntrySyetemTimes) between '"+start+"' and '"+end+"') y on y.NVIDS = rmh.RS_Nav_ID where y.nv="+uid+") x";
		return s;
	}
	// Navigation Graph query
	public static String askNavGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select AVG(CONVERT(FLOAT,Nav_UnloadEvent))/1000, AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000, AVG(CONVERT(FLOAT,Nav_AppCache))/1000, AVG(CONVERT(FLOAT,Nav_TTFB))/1000,AVG(CONVERT(FLOAT,Nav_Processing))/1000,AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000,AVG(CONVERT(FLOAT,Nav_DomComplete))/1000,AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID="+pageNO+" and convert(Date,Nav_EntrySyetemTimes) between '" + dtStart +"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String askNavAllGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select AVG(CONVERT(FLOAT,Nav_UnloadEvent))/1000, AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000, AVG(CONVERT(FLOAT,Nav_AppCache))/1000, AVG(CONVERT(FLOAT,Nav_TTFB))/1000,AVG(CONVERT(FLOAT,Nav_Processing))/1000,AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000,AVG(CONVERT(FLOAT,Nav_DomComplete))/1000,AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000,AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID = "+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '" + dtStart +"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String AVGOFaskNavAllGraphQuery(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "with src as (select Nav_TS_ID, Nav_Application_ID, Nav_Page_ID, convert(Date,Nav_EntrySyetemTimes) as nav_entry, Avg(CONVERT(FLOAT,Nav_UnloadEvent))/1000 as avgs,  AVG(CONVERT(FLOAT,Nav_RedirectEvent))/1000 as avgs1, AVG(CONVERT(FLOAT,Nav_AppCache))/1000 as avgs2 , AVG(CONVERT(FLOAT,Nav_TTFB))/1000 as avgs3 , AVG(CONVERT(FLOAT,Nav_Processing))/1000 as avgs4 , AVG(CONVERT(FLOAT,Nav_DomInteractive))/1000 as avgs5 , AVG(CONVERT(FLOAT,Nav_DomComplete))/1000 as avgs6, AVG(CONVERT(FLOAT,Nav_ContentLoad))/1000 as avgs7, AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 as avgs8 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+testCsNO+") and Nav_Application_ID = "+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+" and Nav_Reg_ID='"+uid+" group by Nav_TS_ID, Nav_Application_ID, Nav_Page_ID, convert(Date,Nav_EntrySyetemTimes))select avg(avgs), avg(avgs1), avg(avgs2), avg(avgs3), avg(avgs4), avg(avgs5), avg(avgs6), avg(avgs7), avg(avgs8) from src";
		return q;
	}
	// Page Load Event Queries
	public static String askAveragePageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String askAverageAllPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select AVG(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String askMaximumPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select MAX(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String askMaximumAllPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select MAX(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String askMinimumPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select MIN(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"' and Nav_Reg_ID="+uid;
		return q;
	}
	public static String askMinimumAllPageLoad(String applicationNo, String pageNO, String testCsNO,String dtStart,String dtEnd, int uid) {
		String q = "select MIN(CONVERT(FLOAT,Nav_PageLoad))/1000 from [performancelighthouseserver].[dbo].[Navigation_Master] where Nav_TS_ID in ("+ testCsNO+") and Nav_Application_ID="+applicationNo+" and Nav_Page_ID in ("+pageNO+") and convert(Date,Nav_EntrySyetemTimes) between '"+dtStart+"' and '"+dtEnd+"'  and Nav_Reg_ID="+uid;
		return q;
	}

	/**
	 ******************************************************************************************************************************************************************************************* 
	 * 
	 * 
	 * ADMIN QUERIES
	 * 
	 * 
	 ********************************************************************************************************************************************************************************************
	 */

	public static String getUserCountForAdmin = "select COUNT(*) from User_Registration";
	public static String getApplicationCountForAdmin = "select COUNT(*) from Application_Master";
	public static String getPageCountForAdmin = "select COUNT(*) from Page_Master";
	public static String getTSCountForAdmin = "select COUNT(*) from TestScenario_Master";
	public static String getResCountForAdmin = "select COUNT(*) from Resource_Master";

	public static String countAppUser(int id) {
		String x = "select count(*) from Application_Master a inner join Application_User_Mapper b on a.Application_ID=b.App_Application_ID where b.App_user_Reg_ID = " + id;
		return x;
	}
	public static String countpageUser(int id) {
		String x = "select count(distinct(a.Nav_Page_ID)) from Navigation_Master a inner join Page_Master b on a.Nav_Page_ID = b.Page_ID where a.Nav_Reg_ID="+ id;
		return x;
	}
	public static String countElementUser(int id) {
		String x = "select count(*) from Resource_Master where Res_Reg_ID="+ id;
		return x;
	}


	// Admin Application Request Mapper
	public static String getUserIdToAcceptRequest(String em, String uname) {
		String s = "select Reg_UserID from User_Registration where Reg_Email='"+em+"' and Reg_UserName='"+uname+"'";
		return s;
	}
	public static String updateRequestMapper(int approvedBYRegId, int reqID, String AppName, int reqBYRegID, String status) {
		String s = "update Application_Request_Mapper set Request_App_ApprovedBy_Reg_UserID="+ approvedBYRegId +", Request_Status='"+status+"' where Request_ID="+ reqID +" and Request_App_Name='"+AppName+"' and Request_App_By_Reg_UserID="+reqBYRegID;
		return s;
	}
	public static String GiveAccess = "insert into Application_User_Mapper (App_Application_ID, App_user_Reg_ID) values (?,?)";
	public static String getrequestForAdmin = "(select b.Request_ID, b.Request_App_Name, a.Reg_UserName, a.Reg_Email, (select Reg_F_Name+' '+Reg_L_Name+' as '+Reg_UserName from User_Registration where Reg_UserID = b.Request_App_ApprovedBy_Reg_UserID), b.Request_Status from Application_Request_Mapper b left join User_Registration a on a.Reg_UserID=b.Request_App_By_Reg_UserID) order by b.Request_Status desc";
	public static String addApplicationForAdmin = "insert into Application_Master (Application_Name, Application_Reg_Admin_UserID, Application_CreationTime) values (?,?,?)";
	public static String editApplicationForAdmin(int appId, String appName) {
		String s = "update Application_Master set Application_Name='"+appName+"' where Application_ID="+appId;
		return s;
	}
	public static String deleteApplicationForAdmin(int appId) {
		String s = "update Application_Master set Application_ID_Flag=0 where Application_ID="+appId;
		return s;
	}
	public static String updateApplicationForAdmin(int appId, String apNm) {
		String s = "update Application_Master set Application_Name='"+apNm+"' where Application_ID ="+appId;
		return s;
	}
	public static String deleteUserForAdmin(int appId) {
		String s = "update User_Registration set Reg_UserID_Flag=0 where Reg_UserID ="+appId;
		return s;
	}
	public static String deleteUserMapForAdmin(int appId) {
		String s = "update Application_User_Mapper set App_Map_ID_FLAG=0 where App_Map_ID ="+appId;
		return s;
	}
	public static String retriveApplicationForAdmin(int appId) {
		String s = "update Application_Master set Application_ID_Flag=1 where Application_ID="+appId;
		return s;
	}

	public static String checkIfAPPExistsForAdmin = "select * from Application_Master";
	public static String listAppForAdmin = "select a.Application_ID, a.Application_Name, b.Reg_F_Name+' '+b.Reg_L_Name+ ' as ' + b.Reg_UserName, a.Application_CreationTime, a.Application_ID_Flag from Application_Master a left join User_Registration b on a.Application_Reg_Admin_UserID=b.Reg_UserID";
	public static String listAllUsersForAdmin = "select * from User_Registration";
	public static String AdminMakesAdmin(int uid, String sts) {
		String s = "update User_Registration set Reg_User_Type='"+ sts +"' where Reg_UserID="+ uid;
		return s;
	}
	public static String TcHistoryChange = "select a.TS_U_ID, a.TS_U_TS_ID, a.TS_U_TS_Name, b.Application_Name, c.Reg_UserName, a.TS_U_Status, a.TS_U_CreationTime from TestScenario_Master_History a inner join Application_Master b on b.Application_ID=a.TS_U_TS_Application_ID inner join User_Registration c on c.Reg_UserID=a.TS_U_TS_Reg_UserID";
	public static String pages = "select * from Page_Master";
	public static String pageList ="select a.TS_ID, a.TS_Name, b.Application_Name, c.Reg_UserName from TestScenario_Master a inner join Application_Master b on b.Application_ID = a.TS_Application_ID inner join User_Registration c on c.Reg_UserID=a.TS_Reg_UserID";
	public static String userMapping = "select a.App_Map_ID, b.Application_Name, c.Reg_UserName, a.App_Map_ID_FLAG from Application_user_mapper a inner join Application_Master b on a.App_Application_ID=b.Application_ID inner join User_Registration c on c.Reg_UserID=a.App_user_Reg_ID";
}
