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

public class PageLoadGraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PageLoadGraphController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID=null;
		HttpSession session=request.getSession(false);  
		userID=(String)session.getAttribute("LoginID");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		if(userID == null) {

			System.out.println("I am page loader");
			String applicationNo = request.getParameter("appNM");
			String pageNO = request.getParameter("pgNo");
			String testCsNO = request.getParameter("tcNo");
			String dtStart = request.getParameter("dts");
			String dtEnd = request.getParameter("dte");

			String flagAll = request.getParameter("flag");
			String askAVGloadtime="";
			String askMAXloadtime="";
			String askMINloadtime="";

			String jsonx = null;
			Connection cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			String fm = "";
			String appendMe = "";

			String sm ="";
			String fc="";
			int ix = 0;
			String modlok = "";
			Map<String, String> mps = new HashMap<String, String>();

			// Navigation Graph Query

			if (flagAll.contentEquals("ALL")) {
				askAVGloadtime = Queries.askAverageAllPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
				askMAXloadtime = Queries.askMaximumAllPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
				askMINloadtime = Queries.askMinimumAllPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
			}else {
				askAVGloadtime = Queries.askAveragePageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
				askMAXloadtime = Queries.askMaximumPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
				askMINloadtime = Queries.askMinimumPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
			}
			System.out.println("Avg Query : " + askAVGloadtime);
			System.out.println("Max Query : " + askMAXloadtime);
			System.out.println("Min Query : " + askMINloadtime);
			Map<String, String> mapentry = new HashMap<String, String>();


			// System.out.println("query : " + askNavigation);
			String a = "";
			String b = "";
			String c = "";
			try {
				
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askAVGloadtime);
				while(rs.next()) { a = rs.getString(1);}
				rs1 = st.executeQuery(askMAXloadtime);
				while(rs1.next()) { b = rs1.getString(1);}
				rs2 = st.executeQuery(askMINloadtime);
				while(rs2.next()) { c = rs2.getString(1);}
				

				String chart = "\"chart\": {"+
						"\"theme\": \"fusion\","+
						"\"yaxisname\": \"Loading Times (Seconds)\","+
						"\"exportFileName\": \"Max, Mean, Min Webpage Loading Time (Seconds)"+ dateFormat.format(date)  +"\","+
						"\"showvalues\": \"1\","+
						"\"placeValuesInside\": \"1\","+
						"\"rotateValues\": \"1\","+
						"\"valueFontColor\": \"#ffffff\","+
						"\"numberprefix\": \"\","+
						"\"numVisiblePlot\": \"15\","+
						"\"showLabels\": \"1\","+
						"\"labeldisplay\": \"WRAP\","+
						"\"linethickness\": \"3\","+
						"\"flatScrollBars\": \"1\","+
						"\"labelFontSize\": \"15\","+
						"\"labelFontBold\": \"1\","+
						"\"labelFontColor\": \"0075c2\","+
						"\"exportEnabled\": \"1\","+
						"\"placeValuesInside\": \"0\","+
						"\"exportFormats\": \"csv | xlsx\","+
						"\"showHoverEffect\": \"1\""+
	              "},";
				
				
				String embed = "\"categories\": [{"+
						"\"category\": [{"+
						"\"label\": \"Max Pageload Time\""+
						"},"+
						"{ \"label\": \"Average Pageload Time\" },"+
						"{ \"label\": \"Min Pageload Time\" }]}],"+
						"\"dataset\": [{"+
						"\"data\": [{"+
						"\"value\": \""+b+"\"},"+
						"{ \"value\": \""+a+"\" },"+ 
						"{ \"value\": \""+c+"\" }]}]";
			        
		
				String makejson = "{"+chart+ embed+"}";
				System.err.println("makejson graph : " + makejson);
				jsonx = new Gson().toJson(makejson);
				System.err.println("PageLoad graph : " + jsonx);
				response.setContentType("application/json");
				response.getWriter().write(jsonx);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			int u = Integer.parseInt(userID);
			System.out.println("I am page loader");
			String applicationNo = request.getParameter("appNM");
			String pageNO = request.getParameter("pgNo");
			String testCsNO = request.getParameter("tcNo");
			String dtStart = request.getParameter("dts");
			String dtEnd = request.getParameter("dte");

			String flagAll = request.getParameter("flag");
			String askAVGloadtime="";
			String askMAXloadtime="";
			String askMINloadtime="";

			String jsonx = null;
			Connection cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			Map<String, String> mps = new HashMap<String, String>();
			String a = "";
			String b = "";
			String c = "";
			

			// Navigation Graph Query

			if (flagAll.contentEquals("ALL")) {
				askAVGloadtime = Queries.askAverageAllPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd, u);
				askMAXloadtime = Queries.askMaximumAllPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd, u);
				askMINloadtime = Queries.askMinimumAllPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd, u);
			}else {
				askAVGloadtime = Queries.askAveragePageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd, u);
				askMAXloadtime = Queries.askMaximumPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd, u);
				askMINloadtime = Queries.askMinimumPageLoad(applicationNo, pageNO, testCsNO, dtStart, dtEnd, u);
			}
			System.out.println("Avg Query : " + askAVGloadtime);
			System.out.println("Max Query : " + askMAXloadtime);
			System.out.println("Min Query : " + askMINloadtime);


			// System.out.println("query : " + askNavigation);
			try {
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askAVGloadtime);
				while(rs.next()) { a = rs.getString(1);}
				rs1 = st.executeQuery(askMAXloadtime);
				while(rs1.next()) { b = rs1.getString(1);}
				rs2 = st.executeQuery(askMINloadtime);
				while(rs2.next()) { c = rs2.getString(1);}

				String chart = "\"chart\": {"+
						"\"theme\": \"fusion\","+
						"\"yaxisname\": \"Loading Times (Seconds)\","+
						"\"showvalues\": \"1\","+
						"\"placeValuesInside\": \"1\","+
						"\"rotateValues\": \"1\","+
						"\"valueFontColor\": \"#ffffff\","+
						"\"numberprefix\": \"\","+
						"\"numVisiblePlot\": \"15\","+
						"\"showLabels\": \"1\","+
						"\"labeldisplay\": \"WRAP\","+
						"\"linethickness\": \"3\","+
						"\"scrollheight\": \"10\","+
						"\"labelFontSize\": \"15\","+
						"\"labelFontBold\": \"1\","+
						"\"labelFontColor\": \"0075c2\","+
						"\"flatScrollBars\": \"1\","+
						"\"scrollShowButtons\": \"0\","+
						"\"scrollColor\": \"#cccccc\","+
						"\"exportEnabled\": \"1\","+
						"\"exportFormats\": \"csv | xlsx\","+
						"\"showHoverEffect\": \"1\""+

	              "},";
				
				
				String embed = "\"categories\": [{"+
						"\"category\": [{"+
						"\"label\": \"Max Pageload Time\""+
						"},"+
						"{ \"label\": \"Average Pageload Time\" },"+
						"{ \"label\": \"Min Pageload Time\" }]}],"+
						"\"dataset\": [{"+
						"\"data\": [{"+
						"\"value\": \""+b+"\"},"+
						"{ \"value\": \""+a+"\" },"+ 
						"{ \"value\": \""+c+"\" }]}]";
			        
		
				String makejson = "{"+chart+ embed+"}";
				System.err.println("makejson graph : " + makejson);
				jsonx = new Gson().toJson(makejson);
				System.err.println("PageLoad graph : " + jsonx);
				response.setContentType("application/json");
				response.getWriter().write(jsonx);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println("application Name : " + applicationname + " Page No : "+ pageNO + " testCaseNo : " + testCsNO + " DateS : " + dtStart + " DateE : " + dtEnd);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
