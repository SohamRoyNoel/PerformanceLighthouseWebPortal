package controllers.moduleControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class BaseActualGraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BaseActualGraphController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static String askFusion(String Pgname) {
		String askFusion = "select * from PageTimeLine where PageName = '"+ Pgname +"'";
		return askFusion;
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String baseStartdate = request.getParameter("baseStart");
		String baseEndDate = request.getParameter("baseEnd");
		String ActStartdate = request.getParameter("actStart");
		String ActEndDate = request.getParameter("actEnd");
		String custName =  request.getParameter("pages");
		String app =  request.getParameter("apps");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		System.out.println("---------------------"+baseStartdate + " " + baseEndDate + " " + ActStartdate+ " "+ ActEndDate + " " + custName + " " + app);
		
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		Statement st1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Map<String, String> mps = new HashMap<String, String>();
		String modlok="";

		String cat = "";
		String category= "";
		String modCategory= "";

		String seriesName = "";
		String multiSeries = "";
		String finalDataset = "";

		//String askMaxTimetakenCharts = "select distinct name from DependentTable";
		//		String askMaxTimetakenCharts = "select top 5 PageName as pg , Avg(TimeTaken) as avgCal from PageTimeLine group by PageName order by avgCal desc";
		//String maxPlot = "select distinct name from DependentTable";
		//String askFusion = "select * from PageTimeLine where PageName = ''";
		String fm = "";
		String appendMe = "";
		//String lokkhonRekha = "";

		String sm ="";
		String fc = "";
		String lr = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			st1 = cn.createStatement();
			//rs = st.executeQuery(askMaxTimetakenCharts);
			//while(rs.next()) {
			String lokkhonRekha = "";

			System.out.println("----------------- " + Queries.askFusion(baseStartdate, baseEndDate, custName));
			System.out.println("----------------- " + Queries.askFusion1(ActStartdate, ActEndDate, custName));
			rs1 = st1.executeQuery(Queries.askFusion(baseStartdate, baseEndDate, custName));
			while(rs1.next()) {
				lr = "{ \"value\":\""+rs1.getFloat(2)+"\"}";
				lokkhonRekha += ","+lr;
				appendMe += ","+rs1.getString(1);
			}
			modlok = lokkhonRekha.substring(1);
			seriesName = "{\"seriesname\": \""+(baseStartdate+" to "+ baseEndDate)+"\", \"data\": ["+modlok+"]},";
			multiSeries += seriesName;
			
			lokkhonRekha = "";
			lr = "";
			modlok = "";
			
			rs = st.executeQuery(Queries.askFusion1(ActStartdate, ActEndDate, custName));
			while(rs.next()) {
				lr = "{ \"value\":\""+rs.getFloat(2)+"\"}";
				lokkhonRekha += ","+lr;
				//System.out.println("Lokkhon : " + lokkhonRekha);
			}
			modlok = lokkhonRekha.substring(1);
			String seriesName1 = "{\"seriesname\": \""+(ActStartdate+" to "+ ActEndDate)+"\", \"data\": ["+modlok+"]},";
			multiSeries += seriesName1;
			
			
			//}
			String newMulti = multiSeries.substring(0, multiSeries.length()-1);
			finalDataset = "\"dataset\": ["+newMulti+"]"; // data

//			System.out.println("--------------------- " + appendMe);
			custName = appendMe.substring(1, appendMe.length());
			String arr[] = custName.split(",");
			Arrays.sort(arr);
			
			// https://www.johnhancock.com/financial-advice/ideas-insights/making-amazing-retirement-plan.html
			
			for (String string : arr) {
				String r = string.substring(8, string.length());
				String p= r.substring(r.indexOf("/")+1, r.length());
				//String p1 = p.indexOf("/T") > 0 ? p.substring(p.indexOf("/T")+1, p.length()) : p;
				cat = "{ \"label\":\""+p+"\"}";
				category += ","+cat;
			}

			modCategory = category.substring(1);

			String categories = "\"categories\": [{ \"category\": ["+modCategory+"] }],"; // Labels
			
			String dataSource = "{" + 
					"\"chart\": {" + 
					"\"theme\": \"fusion\"," + 
					"\"exportFileName\": \"Timeboxed Comparison of Webpage Loading Times(Seconds)"+ dateFormat.format(date)  +"\","+
					"\"caption\": \"\"," + 
					"\"subCaption\": \"\"," + 
					"\"xAxisName\": \"\"," + 
					"\"exportEnabled\": \"1\","+
					"\"labelDisplay\": \"stagger\","+
					"\"labelFontSize\": \"15\","+
					"\"labelFontBold\": \"1\","+
					"\"labelFontColor\": \"0075c2\","+
					"\"exportFormats\": \"csv | xlsx\""+
					"},";

			String modifiedChart = dataSource+categories+finalDataset+"}";

//			System.out.println("XXXXXXXXXX : "+modifiedChart);
			jsonx = new Gson().toJson(modifiedChart);
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
