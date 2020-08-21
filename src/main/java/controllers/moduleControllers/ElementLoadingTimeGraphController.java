package controllers.moduleControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class ElementLoadingTimeGraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ElementLoadingTimeGraphController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userID=null;
		HttpSession session=request.getSession(false);  
		userID=(String)session.getAttribute("LoginID");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		

		if(userID == null) {

			String applicationNo = request.getParameter("appNM");
			//int appID = Integer.parseInt(applicationNo);
			String pageNO = request.getParameter("pgNo");
			//int pgID = Integer.parseInt(pageNO);
			String testCsNO = request.getParameter("tcNo");
			//int tsID = Integer.parseInt(testCsNO);
			String dtStart = request.getParameter("dts");
			String dtEnd = request.getParameter("dte");

			String flagAll = request.getParameter("flag");

			String jsonx = null;
			Connection cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			Map<String, String> mps = new HashMap<String, String>();
			String fm = "";
			String appendMe = "";

			String sm ="";
			String fc = "";
			String lr = "";
			String lokkhonRekha="";
			String askNavigation = "";

			// Navigation Graph Query
			if (flagAll.contentEquals("ALL")) {
				askNavigation = Queries.askAllResources(pageNO, testCsNO, applicationNo, dtStart, dtEnd);
				System.err.println("Sib da : "+ askNavigation);
			}else {
				askNavigation = Queries.askResources(pageNO, testCsNO, applicationNo, dtStart, dtEnd);
				System.err.println("Sib da : "+ askNavigation);
			}
			//System.out.println("querySSSSSS : " + askNavigation);
			int ix = 0;
			try {
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askNavigation);
				while(rs.next()) {
					//mps.put(rs.getString(1), rs.getString(2));
					fm = "{ \"value\":\""+rs.getFloat(2)+"\"}";
					sm = "{ \"label\":\""+rs.getString(1)+"\"}";
					//lr = "{ \"value\":\""+rs.getString(4)+"\"}";
					appendMe += ","+fm;
					fc += ","+sm;
					//lokkhonRekha += ","+lr;
					ix++;
				}


				rs1 = st.executeQuery(Queries.avgLine(pageNO, testCsNO, applicationNo, dtStart, dtEnd));
				System.out.println("PDPDPDPD : " + rs1);
				float carryValue = 0;
				while(rs1.next()) {
					carryValue = rs1.getFloat(1);
				}
				for (int i = 0; i < ix; i++) {
					lr = "{ \"value\":\""+carryValue+"\"}";
					lokkhonRekha += ","+lr;
				}
				String modlok = lokkhonRekha.substring(1);

				String chart = "\"chart\": {"+
						"\"theme\": \"fusion\","+
						"\"yaxisname\": \"Loading Times (Seconds)\","+
						"\"exportFileName\": \"Average Resource Loading Durations (Seconds) "+ dateFormat.format(date)  +"\","+
						"\"showvalues\": \"1\","+
						"\"placeValuesInside\": \"1\","+
						"\"rotateValues\": \"1\","+
						"\"valueFontColor\": \"#ffffff\","+
						"\"numberprefix\": \"\","+
						"\"numVisiblePlot\": \"15\","+
						"\"showLabels\": \"0\","+
						"\"labeldisplay\": \"WRAP\","+
						"\"labelFontSize\": \"15\","+
						"\"labelFontBold\": \"1\","+
						"\"labelFontColor\": \"0075c2\","+
						"\"linethickness\": \"3\","+
						"\"scrollheight\": \"10\","+
						"\"flatScrollBars\": \"1\","+
						"\"scrollShowButtons\": \"0\","+
						"\"scrollColor\": \"#cccccc\","+
						"\"exportEnabled\": \"1\","+
						"\"exportFormats\": \"csv | xlsx\","+
						"\"showHoverEffect\": \"1\""+

	              "}";

				String modifiedDataset = "\"dataset\": [{\"seriesName\": \"Actual Loading Time\",\"data\":["+appendMe.substring(1)+"]}";
				String modifiedCategories = "\"categories\": [{\"category\":["+fc.substring(1)+"]}]";
				//String modifiedChart = "{type: 'scrollbar2d',renderAt: 'containerss', width: '600',height: '500',dataFormat: 'json',dataSource: { \"chart\": {plottooltext: \"$dataValue Downloads\", theme: \"fusion\"},"+modifiedCategories+","+modifiedDataset+"}}";
				//			String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+"}";
				String modlok1 = "{ \"seriesName\": \"Average/Baseline Loading Time\", \"renderAs\": \"line\", \"showValues\": \"0\", \"data\": [ " + modlok + "]}";
				String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+","+ modlok1 +"]}";
				//			System.out.println("MODIFIED DATA : " + modifiedChart);
				//			String modifiedChart = "{"+modifiedDataset+"}";


				//System.out.println(modifiedChart);
				jsonx = new Gson().toJson(modifiedChart);
				//			response.setContentType("text/json");
				//			response.getWriter().write(modifiedChart);
				response.setContentType("text/json");
				response.getWriter().write(jsonx);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("**************************XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX*************************************************************");
			int u = Integer.parseInt(userID);
			String applicationNo = request.getParameter("appNM");
			//int appID = Integer.parseInt(applicationNo);
			String pageNO = request.getParameter("pgNo");
			//int pgID = Integer.parseInt(pageNO);
			String testCsNO = request.getParameter("tcNo");
			//int tsID = Integer.parseInt(testCsNO);
			String dtStart = request.getParameter("dts");
			String dtEnd = request.getParameter("dte");

			String flagAll = request.getParameter("flag");

			String jsonx = null;
			Connection cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			Map<String, String> mps = new HashMap<String, String>();
			String fm = "";
			String appendMe = "";

			String sm ="";
			String fc = "";
			String lr = "";
			String lokkhonRekha="";
			String askNavigation = "";

			// Navigation Graph Query
			if (flagAll.contentEquals("ALL")) {
				askNavigation = Queries.askAllResources(pageNO, testCsNO, applicationNo, dtStart, dtEnd, u);
			}else {
				askNavigation = Queries.askResources(pageNO, testCsNO, applicationNo, dtStart, dtEnd, u);
			}
			int ix = 0;
			try {
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askNavigation);
				while(rs.next()) {
					fm = "{ \"value\":\""+rs.getFloat(2)+"\"}";
					sm = "{ \"label\":\""+rs.getString(1)+"\"}";
					appendMe += ","+fm;
					fc += ","+sm;
					ix++;
				}


				rs1 = st.executeQuery(Queries.avgLine(pageNO, testCsNO, applicationNo, dtStart, dtEnd, u));
				float carryValue = 0;
				while(rs1.next()) {
					carryValue = rs1.getFloat(1);
				}
				for (int i = 0; i <= ix; i++) {
					lr = "{ \"value\":\""+carryValue+"\"}";
					lokkhonRekha += ","+lr;
				}
				String modlok = lokkhonRekha.substring(1);

				String chart = "\"chart\": {"+
						"\"theme\": \"fusion\","+
						"\"yaxisname\": \"Loading Times (Seconds)\","+
						"\"showvalues\": \"1\","+
						"\"placeValuesInside\": \"1\","+
						"\"rotateValues\": \"1\","+
						"\"valueFontColor\": \"#ffffff\","+
						"\"numberprefix\": \"\","+
						"\"numVisiblePlot\": \"15\","+
						"\"showLabels\": \"0\","+
						"\"labeldisplay\": \"WRAP\","+
						"\"linethickness\": \"3\","+
						"\"scrollheight\": \"10\","+
						"\"flatScrollBars\": \"1\","+
						"\"scrollShowButtons\": \"0\","+
						"\"labelFontSize\": \"15\","+
						"\"labelFontBold\": \"1\","+
						"\"labelFontColor\": \"0075c2\","+
						"\"scrollColor\": \"#cccccc\","+
						"\"exportEnabled\": \"1\","+
						"\"exportFormats\": \"csv | xlsx\","+
						"\"showHoverEffect\": \"1\""+

	              "}";

				String modifiedDataset = "\"dataset\": [{\"data\":["+appendMe.substring(1)+"]}";
				String modifiedCategories = "\"categories\": [{\"category\":["+fc.substring(1)+"]}]";
				String modlok1 = "{ \"seriesName\": \"Base Load Time\", \"renderAs\": \"line\", \"showValues\": \"0\", \"data\": [ " + modlok + "]}";
				String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+","+ modlok1 +"]}";

				jsonx = new Gson().toJson(modifiedChart);
				response.setContentType("text/json");
				response.getWriter().write(jsonx);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
