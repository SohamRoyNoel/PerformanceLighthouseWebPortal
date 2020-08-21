package controllers.AdminAddAppControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
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

public class AdminAddAppController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminAddAppController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String applicationNM = request.getParameter("apNM");
		HttpSession session=request.getSession(false);  
		String userID=(String)session.getAttribute("LoginID");
		int intUID = Integer.parseInt(userID);
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamps = new java.sql.Timestamp(cal.getTimeInMillis());
		Boolean addFlag = true;
		int appid = 0;
		
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, String> mps = new HashMap<String, String>();
		PrintWriter out = response.getWriter();

		// Navigation Graph Query
		String addAPP = Queries.addApplicationForAdmin;
		try {
			// Add Application
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(Queries.checkIfAPPExistsForAdmin);
			while(rs.next()) {
				if (rs.getString(2).equalsIgnoreCase(applicationNM)) {
					addFlag = false;
				}
			}
			if (addFlag) {
				PreparedStatement preparedStatement1 = cn.prepareStatement(addAPP);
				preparedStatement1.setString(1, applicationNM);
				preparedStatement1.setInt(2, intUID);
				preparedStatement1.setTimestamp(3, timestamps);
				preparedStatement1.executeUpdate();
				
				// Provide Access for admin
				rs = st.executeQuery(Queries.checkIfAPPExistsForAdmin);
				while(rs.next()) {
					if (rs.getString(2).equalsIgnoreCase(applicationNM)) {
						appid = rs.getInt(1);
					}
				}
				if (appid > 0) {
					preparedStatement1 = cn.prepareStatement(Queries.GiveAccess);
					preparedStatement1.setInt(1, appid);
					preparedStatement1.setInt(2, intUID);
					preparedStatement1.executeUpdate();
				}
				String ad = "Application Added Successfully";
				out.write("<input type='hidden' id='pqId' value='"+ad+"'/>");
				
			}else {
				String ad = "Application Already Exists";
				out.write("<input type='hidden' id='pqId' value='"+ad+"'/>");
			}
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
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
