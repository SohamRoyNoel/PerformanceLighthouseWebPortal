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

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class NavigationGraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NavigationGraphController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("thats a hiat");
		String applicationNo = request.getParameter("appNM");
		String pageNO = request.getParameter("pgNo");
		String testCsNO = request.getParameter("tcNos");
		String dtStart = request.getParameter("dts");
		String dtEnd = request.getParameter("dte");
		String flagAll = request.getParameter("flag");
		String askNavigation = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		/*String invst = request.getParameter("iv");
    	String grow = request.getParameter("gt");*/
		String ss = "single";
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, String> mps = new HashMap<String, String>();

		String askFusion = "select top 9 * from Investments";
		String avggroth = "select top 9 avg(CustInvestmentValue) from Investments";
		String avgFixed = "select top 9 avg(Fixed) from Investments";
		float invst = 0;
		float grow = 0;
		String fm = "";
		String appendMe = "";
		String lokkhonRekha = "";

		String sm ="";
		String fc = "";
		String lr = "";

		// Navigation Graph Query
		if (flagAll.contentEquals("ALL")) {
			askNavigation = Queries.askNavAllGraphQuery(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
		}else {
			askNavigation = Queries.askNavGraphQuery(applicationNo, pageNO, testCsNO, dtStart, dtEnd);
		}
		try {
			System.out.println("Navigation Query : " + askNavigation);
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs= st.executeQuery(askNavigation);
			System.out.println("IF");
			// get values
			while(rs.next()) {
				fm = "{ \"value\":\""+rs.getFloat(4)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(2)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(8)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(5)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(3)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(1)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(6)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(7)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(9)+"\"}";

				//sm = "{ \"label\":\""+rs.getString(2)+"\"}";
				//appendMe += ","+fm;
				//fc += ","+sm;
			}
			appendMe = fm;
			// get labels
			sm =  "{ \"label\":\""+"Nav_TTFB"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_RedirectEvent"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_ContentLoad"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_Processing"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_AppCache"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_UnloadEvent"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_DomInteractive"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_DomComplete"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_PageLoad"+"\"}";
			String modlok = "";
			System.err.println("Navigation : " + Queries.AVGOFaskNavAllGraphQuery(applicationNo, pageNO, testCsNO, dtStart, dtEnd));
			rs = st.executeQuery(Queries.AVGOFaskNavAllGraphQuery(applicationNo, pageNO, testCsNO, dtStart, dtEnd));
			while(rs.next()) {
				modlok = "{ \"value\":\""+rs.getFloat(4)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(2)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(8)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(5)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(3)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(1)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(6)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(7)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(9)+"\"}";
			}

			
			/*modlok = "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"},";
			modlok += "{ \"value\":\""+grow+"\"}";*/

			//System.out.println("Lokkhon : "+ modlok);
			
			String chart = "\"chart\": {"+
					"\"theme\": \"fusion\","+
					"\"exportFileName\": \"Average Page Rendering Milestones (Seconds)"+ dateFormat.format(date)  +"\","+
					"\"caption\": \"\","+
					"\"subCaption\": \"\","+
					"\"yAxisname\": \"Time in Seconds\","+
					"\"numberPrefix\": \"\","+
					"\"labelFontSize\": \"15\","+
					"\"labelFontBold\": \"1\","+
					"\"labelFontColor\": \"0075c2\","+
					"\"exportEnabled\": \"1\","+
					"\"interactiveLegend\": \"0\","+
					"\"exportFormats\": \"csv | xlsx\""+

	              "}";

			String modifiedDataset = "\"dataset\": [{\"seriesname\": \"Actual Navigation Time\",\"color\":\"F49264\", \"data\":["+appendMe+"]}";
			String modifiedCategories = "\"categories\": [{\"category\":["+sm+"]}]";
			String modlok1 = "{ \"seriesName\": \"Avg./BaseLine Navigation Time\", \"color\":\"71BFE4\", \"data\": [ " + modlok + "]}";
			String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+","+ modlok1 +"]}";

			//String modifiedChart = "{"+modifiedDataset+"}";


			System.out.println("modifiedChartNAV : " + modifiedChart);
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
