package controllers.updateControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class PopulateTableController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public PopulateTableController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String AppId = request.getParameter("ApplicationId");
    	System.out.println("application id : " + AppId);
    	int intAppId = Integer.parseInt(AppId);
        String jsonx = null;
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        int uid = 0;
        HttpSession session=request.getSession(false);  
		String userIDs=(String)session.getAttribute("LoginID");
		int intUserIDs = Integer.parseInt(userIDs);
        
        int tid=0; String tsName = ""; String regUname = ""; String date="";
        Map<Integer, String> mps = new HashMap<Integer, String>();
        List<String> jsonList = new ArrayList<String>();
        
        String askTestCase = Queries.populateTableId(intAppId);
        
        try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(askTestCase);
			int counter = 1;
			while(rs.next()) {
				tid = rs.getInt(1);
				tsName = rs.getString(2);
				regUname = rs.getString(3);
				date = rs.getDate(4).toString();
				uid = rs.getInt(5);
				String htmlButton = "";
				if(uid==intUserIDs) {
					htmlButton = "<button type='button' onClick='opener(this);' id='opener' class='btn btn-warning edits'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></button> &nbsp&nbsp <button type='button' id='sure' onClick='openerDelete(this);' class='btn btn-danger dels'><i class='fa fa-trash' aria-hidden='true'></i></button>";
				}else {
					htmlButton = "<button type='button' disabled onClick='opener(this);' id='opener' class='btn btn-warning edits'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></button> &nbsp&nbsp <button type='button' id='sure' disabled onClick='openerDelete(this);' class='btn btn-danger dels'><i class='fa fa-trash' aria-hidden='true'></i></button>";
				}
				
				String entity = "Ts"+counter;
				String appender = "\""+entity+"\":{\"id\":\""+String.valueOf(tid)+"\",\"tsnm\":\""+tsName+"\",\"userId\":\""+regUname+"\",\"date\":\""+date+"\",\"Button\":\""+htmlButton+"\"}";
//				String appender = "{\"id\":\""+String.valueOf(tid)+"\",\"tsnm\":\""+tsName+"\",\"userId\":\""+regUname+"\",\"date\":\""+date+"\",\"Button\":\""+htmlButton+"\"}";
//				String appender = "{\"id\":\""+String.valueOf(tid)+"\",\"tsnm\":\""+tsName+"\",\"userId\":\""+regUname+"\",\"date\":\""+date+"\"}";
				
				jsonList.add(appender);
				counter++;
			}
			System.out.println("Listed Val : " + jsonList);
			String cnvjson = jsonList.toString();
			String newJsonBuilder = "{"+cnvjson.substring(1, cnvjson.length()-1)+ "}";
//			String newJsonBuilder = "{"+cnvjson.substring(1, cnvjson.length()-1)+ "}";
			System.out.println("NEW STRING : " + newJsonBuilder);
			jsonx = new Gson().toJson(newJsonBuilder);
			
			response.setContentType("application/json");
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
