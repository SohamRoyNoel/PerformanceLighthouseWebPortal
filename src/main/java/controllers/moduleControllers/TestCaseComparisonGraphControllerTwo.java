package controllers.moduleControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class TestCaseComparisonGraphControllerTwo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestCaseComparisonGraphControllerTwo() {
		super();

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("thats a hiat");
		String applicationNo = request.getParameter("apps");
		String pageNO = request.getParameter("pages");
		//String testCsNO = request.getParameter("tcNos");
		String ACStart = request.getParameter("actStart");
		//            String ACEnd = request.getParameter("actEnd");
		String BSStart = request.getParameter("baseStart");
		//            String BSEnd = request.getParameter("baseEnd");
		//String flagAll = request.getParameter("flag");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		String ACTC = request.getParameter("tcs1");
		String BSTC = request.getParameter("tcs2");
		System.err.println("TC : " + ACTC + " BSTC : " + BSTC);
		String askNavigation = "";
		String pageNO1 ="";
		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();
		List<String> target2 = new ArrayList<String>();
		System.out.println("---------------------"+applicationNo + " " + pageNO + " " + ACStart+ " "+ BSStart + " " + ACTC + " " + BSTC);

		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		Statement st1 = null;

		ResultSet rs2 = null;
		Map<String, String> mps = new HashMap<String, String>();
		String modlok="";

		String cat = "";
		String category= "";
		String modCategory= "";
		Statement st2 = null;
		String seriesName = "";
		String multiSeries = "";
		String finalDataset = "";

		//String askMaxTimetakenCharts = "select distinct name from DependentTable";
		//            String askMaxTimetakenCharts = "select top 5 PageName as pg , Avg(TimeTaken) as avgCal from PageTimeLine group by PageName order by avgCal desc";
		//String maxPlot = "select distinct name from DependentTable";
		//String askFusion = "select * from PageTimeLine where PageName = ''";
		String fm = "";
		String appendMe = "";
		String appendMe2 = "";
		//String lokkhonRekha = "";

		String sm ="";
		String fc = "";
		String lr = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st1 = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st2 = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			ResultSet rs3 = null;
			ResultSet rs4 = null;
			ResultSet rs1 = null;
			//rs = st.executeQuery(askMaxTimetakenCharts);
			//while(rs.next()) {
			String lokkhonRekha = "";
			String tc1 = "";
			String tc2 = "";


			System.out.println("********* " + Queries.askFusion(BSStart, ACStart, pageNO, ACTC));
			System.out.println("################# " + Queries.askFusion2(BSStart, ACStart, pageNO, BSTC));
			System.out.println("################# 11111" + Queries.askFusion(BSStart, ACStart, pageNO, BSTC));


			rs4 = st2.executeQuery(Queries.askFusionTS(BSStart, ACStart, pageNO, ACTC));
			String x = "";
			String x1 = "";
			while(rs4.next()) {

				x1 = rs4.getString(1);
			}
			rs3 = st2.executeQuery(Queries.askFusionTS(BSStart, ACStart, pageNO, BSTC));
			while(rs3.next()) {

				x = rs3.getString(1);
			}

			if((x.toUpperCase().contains("WITHOUT")) || (x1.toUpperCase().contains("WITHOUT")) ) {
				if(x.toUpperCase().contains("WITHOUT")){


					rs2 = st2.executeQuery(Queries.askFusion(BSStart, ACStart, pageNO, BSTC));

					rs1 = st1.executeQuery(Queries.askFusion(BSStart, ACStart, pageNO, ACTC));

					while(rs2.next()) {

						target.add(rs2.getDate(2).toString());
					}
					while(rs1.next()) {

						target2.add(rs1.getDate(2).toString());
					}
					target.retainAll(target2);

					rs1.beforeFirst();
					rs = st.executeQuery(Queries.askFusion2(BSStart, ACStart, pageNO, BSTC));
					rs.next();
					for(String s : target) {

						lr = "{ \"value\":\""+rs.getFloat(2)+"\"}";
						lokkhonRekha += ","+lr;
						System.out.println("Lr : " + lr);
						tc1 = rs.getString(1);
					}

					System.out.println("Lokkhon Rekha 1 : " + lokkhonRekha);
					modlok = lokkhonRekha.substring(1);
					seriesName = "{\"seriesname\": \""+(tc1)+"\", \"data\": ["+modlok+"]},";
					multiSeries += seriesName;

					lokkhonRekha = "";
					lr = "";
					modlok = "";
					while(rs1.next()) {
						for(String s : target) {
							if(s.equals(rs1.getDate(2).toString())) {
								lr = "{ \"value\":\""+rs1.getFloat(3)+"\"}";
								lokkhonRekha += ","+lr;
								System.out.println("Value of target : " + s);
								System.out.println("Value of Result : " + rs1.getDate(2).toString());
								System.out.println("Lr : " + lr);
								tc2 = rs1.getString(1);
							}
						}
					}
					modlok = lokkhonRekha.substring(1);	
					String seriesName1 = "{\"seriesname\": \""+(tc2)+"\", \"data\": ["+modlok+"]},";
					multiSeries += seriesName1;


					//}
					String newMulti = multiSeries.substring(0, multiSeries.length()-1);
					finalDataset = "\"dataset\": ["+newMulti+"]"; // data

					System.out.println("--------------------- " + source + "--------------------" + target);


					System.out.println("Array length : " + source);
					// https://www.johnhancock.com/financial-advice/ideas-insights/making-amazing-retirement-plan.html

					for (String string : target) {
						String r = string;
						//String p= r.substring(r.indexOf("/")+1, r.length());
						cat = "{ \"label\":\""+r+"\"}";
						category += ","+cat;
					}

					System.err.println("Category : " + category);
					modCategory = category.substring(1);

					String categories = "\"categories\": [{ \"category\": ["+modCategory+"] }],"; // Labels

					String dataSource = "{" + 
							"\"chart\": {" + 
							"\"theme\": \"fusion\"," + 
							"\"caption\": \"\"," + 
							"\"subCaption\": \"\"," + 
							"\"exportFileName\": \"Loading Times Comparison & Trending (Seconds)"+ dateFormat.format(date)  +"\","+
							"\"xAxisName\": \"\"," + 
							"\"labelFontSize\": \"15\","+
							"\"labelFontBold\": \"1\","+
							"\"labelFontColor\": \"0075c2\","+
							"\"exportEnabled\": \"1\","+
							"\"exportFormats\": \"csv | xlsx\""+
							"},";

					String modifiedChart = dataSource+categories+finalDataset+"}";

					//                  System.out.println("XXXXXXXXXX : "+modifiedChart);
					jsonx = new Gson().toJson(modifiedChart);
					response.setContentType("text/json");
					response.getWriter().write(jsonx);
				}
				if(x1.toUpperCase().contains("WITHOUT")) {

					rs2 = st2.executeQuery(Queries.askFusion(BSStart, ACStart, pageNO, ACTC));
					
					rs1 = st1.executeQuery(Queries.askFusion(BSStart, ACStart, pageNO, BSTC));
					
					while(rs2.next()) {

						target.add(rs2.getDate(2).toString());
					}
					while(rs1.next()) {

						target2.add(rs1.getDate(2).toString());
					}
					target.retainAll(target2);
					
					rs1.beforeFirst();
					rs = st.executeQuery(Queries.askFusion2(BSStart, ACStart, pageNO, ACTC));
					rs.next();
					for(String s : target) {
						
						lr = "{ \"value\":\""+rs.getFloat(2)+"\"}";
						lokkhonRekha += ","+lr;
						System.out.println("Lr : " + lr);
						tc1 = rs.getString(1);
					}

					System.out.println("Lokkhon Rekha 1 : " + lokkhonRekha);
					modlok = lokkhonRekha.substring(1);
					seriesName = "{\"seriesname\": \""+(tc1)+"\", \"data\": ["+modlok+"]},";
					multiSeries += seriesName;

					lokkhonRekha = "";
					lr = "";
					modlok = "";
					while(rs1.next()) {
						for(String s : target) {
							if(s.equals(rs1.getDate(2).toString())) {
								lr = "{ \"value\":\""+rs1.getFloat(3)+"\"}";
								lokkhonRekha += ","+lr;
								System.out.println("Value of target : " + s);
								System.out.println("Value of Result : " + rs1.getDate(2).toString());
								System.out.println("Lr : " + lr);
								tc2 = rs1.getString(1);
							}
						}
					}
					modlok = lokkhonRekha.substring(1);	
					String seriesName1 = "{\"seriesname\": \""+(tc2)+"\", \"data\": ["+modlok+"]},";
					multiSeries += seriesName1;


					//}
					String newMulti = multiSeries.substring(0, multiSeries.length()-1);
					finalDataset = "\"dataset\": ["+newMulti+"]"; // data

					System.out.println("--------------------- " + source + "--------------------" + target);


					System.out.println("Array length : " + source);
					// https://www.johnhancock.com/financial-advice/ideas-insights/making-amazing-retirement-plan.html

					for (String string : target) {
						String r = string;
						//String p= r.substring(r.indexOf("/")+1, r.length());
						cat = "{ \"label\":\""+r+"\"}";
						category += ","+cat;
					}

					System.err.println("Category : " + category);
					modCategory = category.substring(1);

					String categories = "\"categories\": [{ \"category\": ["+modCategory+"] }],"; // Labels

					String dataSource = "{" + 
							"\"chart\": {" + 
							"\"theme\": \"fusion\"," + 
							"\"caption\": \"\"," + 
							"\"subCaption\": \"\"," + 
							"\"yAxisName\": \"Loading Time (Seconds)\"," + 
							"\"exportFileName\": \"Loading Times Comparison & Trending (Seconds)\","+
							"\"labelFontSize\": \"15\","+
							"\"labelFontBold\": \"1\","+
							"\"labelFontColor\": \"0075c2\","+
							"\"exportEnabled\": \"1\","+
							"\"exportFormats\": \"csv | xlsx\""+
							"},";

					String modifiedChart = dataSource+categories+finalDataset+"}";

					//                  System.out.println("XXXXXXXXXX : "+modifiedChart);
					jsonx = new Gson().toJson(modifiedChart);
					response.setContentType("text/json");
					response.getWriter().write(jsonx);

				}
			}else {
				rs1 = st1.executeQuery(Queries.askFusion(BSStart, ACStart, pageNO, ACTC));
				while(rs1.next()) {

					target.add(rs1.getDate(2).toString());
				}
				rs = st.executeQuery(Queries.askFusion1(BSStart, ACStart, pageNO, BSTC));
				while(rs.next()) {

					source.add(rs.getDate(2).toString());
				}
				source.retainAll(target);
				System.out.println("Source : " + source);
				rs1.beforeFirst();

				while(rs1.next()) {
					for(String s : source) {
						//                                       System.out.println("Value of source : " + s);
						//                                       System.out.println("Value of Result : " + rs1.getDate(2).toString());
						if(s.equals(rs1.getDate(2).toString())) {
							lr = "{ \"value\":\""+rs1.getFloat(3)+"\"}";
							lokkhonRekha += ","+lr;
							System.out.println("Value of source : " + s);
							System.out.println("Value of Result : " + rs1.getDate(2).toString());
							System.out.println("Lr : " + lr);
							tc1 = rs1.getString(1);

						}

					}
				}

				System.out.println("Lokkhon Rekha 1 : " + lokkhonRekha);
				modlok = lokkhonRekha.substring(1);
				seriesName = "{\"seriesname\": \""+(tc1)+"\", \"data\": ["+modlok+"]},";
				multiSeries += seriesName;

				lokkhonRekha = "";
				lr = "";
				modlok = "";


				rs.beforeFirst();


				while(rs.next()) {
					for(String s : source) {
						if(s.equals(rs.getDate(2).toString())) {
							lr = "{ \"value\":\""+rs.getFloat(3)+"\"}";
							lokkhonRekha += ","+lr;
							System.out.println("Value of target : " + s);
							System.out.println("Value of Result : " + rs.getDate(2).toString());
							System.out.println("Lr : " + lr);
							tc2 = rs.getString(1);
						}
					}
				}
				modlok = lokkhonRekha.substring(1);
				String seriesName1 = "{\"seriesname\": \""+(tc2)+"\", \"data\": ["+modlok+"]},";
				multiSeries += seriesName1;


				//}
				String newMulti = multiSeries.substring(0, multiSeries.length()-1);
				finalDataset = "\"dataset\": ["+newMulti+"]"; // data

				System.out.println("--------------------- " + source + "--------------------" + target);
				//                         pageNO = appendMe.substring(1, appendMe.length());
				//                         pageNO1 = appendMe2.substring(1, appendMe2.length());
				//                         String arr[] = pageNO.split(",");
				//                         String arr1[] = pageNO1.split(",");
				//                         List<String> arrs = new ArrayList<String>();
				//                  Arrays.sort(arr);
				//                  for(int i=0;i<arr.length;i++){
				//                 for(int j=0;j<arr1.length;j++){
				//                     
				//                 if(arr[i]==arr1[j]){
				//                         System.out.println("Common Value : " + arr[i]);
				//                         arrs.add(arr[i]);
				//                     }
				//                 }
				//             }


				System.out.println("Array length : " + source);
				// https://www.johnhancock.com/financial-advice/ideas-insights/making-amazing-retirement-plan.html

				for (String string : source) {
					String r = string;
					//String p= r.substring(r.indexOf("/")+1, r.length());
					cat = "{ \"label\":\""+r+"\"}";
					category += ","+cat;
				}

				System.err.println("Category : " + category);
				modCategory = category.substring(1);

				String categories = "\"categories\": [{ \"category\": ["+modCategory+"] }],"; // Labels

				String dataSource = "{" + 
						"\"chart\": {" + 
						"\"theme\": \"fusion\"," + 
						"\"caption\": \"\"," + 
						"\"subCaption\": \"\"," + 
						"\"xAxisName\": \"\"," + 
						"\"exportEnabled\": \"1\","+
						"\"exportFileName\": \"Loading Time Comparison & Trending (Seconds)\","+
						"\"labelFontSize\": \"15\","+
						"\"labelFontBold\": \"1\","+
						"\"labelFontColor\": \"0075c2\","+
						"\"exportFormats\": \"csv | xlsx\""+
						"},";

				String modifiedChart = dataSource+categories+finalDataset+"}";

				//                  System.out.println("XXXXXXXXXX : "+modifiedChart);
				jsonx = new Gson().toJson(modifiedChart);
				response.setContentType("text/json");
				response.getWriter().write(jsonx);
			}


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
