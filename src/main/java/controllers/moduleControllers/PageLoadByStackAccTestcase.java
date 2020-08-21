package controllers.moduleControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

/**
 * Servlet implementation class PageLoadByStackAccTestcase
 */
public class PageLoadByStackAccTestcase extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public PageLoadByStackAccTestcase() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("thats a hiat");
		String applicationNo = request.getParameter("apps");
		String pageNO = request.getParameter("pages");
		//String testCsNO = request.getParameter("tcNos");
		String ACStart = request.getParameter("actStart");
//		String ACEnd = request.getParameter("actEnd");
		String BSStart = request.getParameter("baseStart");
//		String BSEnd = request.getParameter("baseEnd");
		//String flagAll = request.getParameter("flag");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		String ACTC = request.getParameter("tcs1");
		String BSTC = request.getParameter("tcs2");
		System.err.println("TC : " + ACTC + " BSTC : " + BSTC);
		String askNavigation = "";

		/*String invst = request.getParameter("iv");
    	String grow = request.getParameter("gt");*/
		String ss = "single";
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Map<String, String> mps = new HashMap<String, String>();

		
		float invst = 0;
		float grow = 0;
		String fm = "";
		String appendMe = "";
		String lokkhonRekha = "";

		String sm ="";
		String fc = "";
		String lr = "";
		float a=0;
		float b=0;

		// Navigation Graph Query
		System.err.println("Stacked Query : " + Queries.TcPageLoadGraphQuery(applicationNo, BSTC, BSStart, ACStart));
		String askPage= Queries.TcPageDateLoadGraphQuery(applicationNo, BSTC, BSStart, ACStart);
		
		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			
			Map<String, Float> jsonCreate = new HashMap<String, Float>();
			Set<String> setDates = new HashSet<String>();
			Set<String> setPage = new HashSet<String>();
			
			String modlok = "";
			System.err.println("Nav Query2 : " + Queries.TcPageLoadGraphQuery(applicationNo, BSTC, BSStart, ACStart));
			rs = st.executeQuery(Queries.TcPageLoadGraphQuery(applicationNo, BSTC, BSStart, ACStart));
			while(rs.next()) {
				setDates.add(rs.getDate(3).toString());
				setPage.add(rs.getString(1));
				String key = rs.getString(1)+"||"+rs.getDate(3);
				float value = rs.getFloat(2);
				System.err.println("Key value : " + key + " " + value);
				jsonCreate.put(key, value);
			}
			System.out.println("jsoncreate : " + jsonCreate);
			String label="";
			// category
			for (String s : setDates) {
				System.out.println("--------------Date " + s);
			}
			for (String s : setPage) {
				System.out.println("--------------Page " + s);
			}
			for (String s : setDates) {
				label += "{\"label\":\""+s+"\"},";
			}
			String category = "["+label.substring(0, label.length()-1)+"],";
			String categories = "\"categories\":[{\"category\":"+category.substring(0, category.length()-1)+"}],";
			String categories1 = categories.substring(0, categories.length()-1); 
			// Category Created
			
			// dataset creating
			String seriesName = "";
			String completeSeries = "";
			for (String sr : setPage) {
				String pg = sr;
				String data = "";
				String x ="";
				for (String std : setDates) {
					String d = std;
					String combineKey = pg+"||"+d;
					System.err.println("Key : " + combineKey);
					if(jsonCreate.containsKey(combineKey)) {
						// Got Map Value
						x += "{\"value\":\""+jsonCreate.get(combineKey)+"\"},";
						System.err.println("X : " + x);
					}else {
						x += "{\"value\":\""+0.0+"\"},";
					}
					
				}
				seriesName += "{\"seriesName\":\""+pg+"\",\"data\":["+x.substring(0, x.length()-1)+"]},";
			}
			String dataset = "\"dataSet\":["+seriesName.substring(0, seriesName.length()-1)+"]";
			System.err.println("SeriesName : " + seriesName);
			System.err.println("dataset : " + dataset);
			
			
			
			

//			
			String chart = "\"chart\": {"+
					"\"theme\": \"fusion\","+
					"\"numVisiblePlot\": \"2\","+
					"\"exportEnabled\": \"1\","+
					"\"exportFormats\": \"csv | xlsx\","+
					"\"labelFontSize\": \"15\","+
					"\"labelFontBold\": \"1\","+
					"\"labelFontColor\": \"0075c2\","+
					"\"labelFontSize\": \"15\","+
					"\"labelFontBold\": \"1\","+
					"\"labelFontColor\": \"0075c2\","+
					"\"xaxisname\": \"Processing Times (Seconds)\","+
					"\"exportFileName\": \"E2E Performance Profiling (Seconds)"+ dateFormat.format(date)  +"\","+
					"\"plottooltext\": \"<b>$seriesName</b><hr>$label: <b>$dataValue</b>\""+

	              "}";

//			String modifiedDataset = "\"dataset\": [{\"seriesname\": \"Base Test Case\",\"color\":\"F49264\", \"data\":["+appendMe+"]}";
//			String modifiedCategories = "\"categories\": [{\"category\":["+sm+"]}]";
//			String modlok1 = "{ \"seriesName\": \"Actual Test case\", \"color\":\"71BFE4\", \"data\": [ " + modlok + "]}";
			String modifiedChart = "{"+chart+","+categories1+","+dataset+"}";

			//String modifiedChart = "{"+modifiedDataset+"}";


			System.out.println("modifiedChartLastOfUs : " + modifiedChart);
			jsonx = new Gson().toJson(modifiedChart);
			//				response.setContentType("text/json");
			//				response.getWriter().write(modifiedChart);
			response.setContentType("text/json");
			response.getWriter().write(jsonx);




		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
