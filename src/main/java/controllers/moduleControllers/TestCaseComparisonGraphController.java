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

/**
 * Servlet implementation class TestCaseComparisonGraphController
 */
public class TestCaseComparisonGraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public TestCaseComparisonGraphController() {
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
		float a=0;
		float b=0;

		// Navigation Graph Query
		System.err.println("Nav Query1 : " + Queries.baseTCGraphQuery(applicationNo, pageNO, BSTC, BSStart));
		if(BSTC.equals("49")) {
			askNavigation = Queries.baseTCGraphQuery2(applicationNo, pageNO, BSTC, BSStart);
		}else {
			askNavigation = Queries.baseTCGraphQuery(applicationNo, pageNO, BSTC, BSStart);
		}
		System.err.println("Nav Query1fffff : " + askNavigation);
		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs= st.executeQuery(askNavigation);
			System.out.println("IF");
			// get values
			while(rs.next()) {
/*				fm = "{ \"value\":\""+rs.getFloat(4)+"\"},";
//				fm += "{ \"value\":\""+rs.getFloat(2)+"\"},";*/
				fm += "{ \"value\":\""+rs.getFloat(1)+"\"}";
				/*fm += "{ \"value\":\""+rs.getFloat(5)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(3)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(1)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(6)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(7)+"\"},";
				fm += "{ \"value\":\""+rs.getFloat(9)+"\"}";*/
//				fm = "{ \"value\":\""+rs.getFloat(9)+"\"}";
				a = rs.getFloat(1);
				//sm = "{ \"label\":\""+rs.getString(2)+"\"}";
				//appendMe += ","+fm;
				//fc += ","+sm;
			}
			appendMe = fm;
			// get labels
			/*sm =  "{ \"label\":\""+"Nav_TTFB"+"\"},";
//			sm +=  "{ \"label\":\""+"Nav_RedirectEvent"+"\"},";*/
			sm +=  "{ \"label\":\""+"Processing Time"+"\"}";
			/*sm +=  "{ \"label\":\""+"Nav_Processing"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_AppCache"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_UnloadEvent"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_DomInteractive"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_DomComplete"+"\"},";
			sm +=  "{ \"label\":\""+"Nav_PageLoad"+"\"}";*/
//			sm =  "{ \"label\":\""+"Nav_PageLoad"+"\"}";
			String modlok = "";
			System.err.println("Nav Query : " + Queries.actualTCGraphQuery(applicationNo, pageNO, ACTC, ACStart));
			rs = st.executeQuery(Queries.actualTCGraphQuery(applicationNo, pageNO, ACTC, ACStart));
			while(rs.next()) {
				/*modlok = "{ \"value\":\""+rs.getFloat(4)+"\"},";
//				modlok += "{ \"value\":\""+rs.getFloat(2)+"\"},";*/
				modlok = "{ \"value\":\""+rs.getFloat(1)+"\"}";
				/*modlok += "{ \"value\":\""+rs.getFloat(5)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(3)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(1)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(6)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(7)+"\"},";
				modlok += "{ \"value\":\""+rs.getFloat(9)+"\"}";*/
//				modlok = "{ \"value\":\""+rs.getFloat(9)+"\"}";
				b = rs.getFloat(1);
			}

//			float difference = a-b;
//			
			//sm +=  "{ \"label\":\""+"Nav_ContentLoad Time"+"\"}";
			modlok = "{ \"value\":\""+b+"\"}";
			
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
					"\"caption\": \"\","+
					"\"subCaption\": \"\","+
					"\"yAxisname\": \"Time in Seconds\","+
					"\"exportFileName\": \"Loading Time Trends (Seconds)"+ dateFormat.format(date)  +"\","+
					"\"numberPrefix\": \"\","+
					"\"labelFontSize\": \"15\","+
					"\"labelFontBold\": \"1\","+
					"\"labelFontColor\": \"0075c2\","+
					"\"exportEnabled\": \"1\","+
					"\"exportFormats\": \"csv | xlsx\""+

	              "}";

			String modifiedDataset = "\"dataset\": [{\"seriesname\": \"Base Test Case\",\"color\":\"F49264\", \"data\":["+appendMe+"]}";
			String modifiedCategories = "\"categories\": [{\"category\":["+sm+"]}]";
			String modlok1 = "{ \"seriesName\": \"Actual Test case\", \"color\":\"71BFE4\", \"data\": [ " + modlok + "]}";
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
