package controllers.updateControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class CreateApplicationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateApplicationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Boolean flag = true;
		
		// Get Application name from UI
		String applicationNM = request.getParameter("ApplicationName");

		// check if the requested application already exists in Application table
		String appNames = Queries.getAllApplications;
		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(appNames);
			while(rs.next()) {
				if(rs.getString(2).equalsIgnoreCase(applicationNM)) {
					flag = false;
				}
			}
			
			// Insert The Application request  to "Application_request_mapper"
			if (flag) {
				String insertReq = Queries.addNewApplicationRequest;
				HttpSession session=request.getSession(false);  
				String userID=(String)session.getAttribute("LoginID");
				String appname = applicationNM;
				int adminId = 0;
				String reqStatus = "Pending";
				PreparedStatement preparedStatement = cn.prepareStatement(insertReq);
				preparedStatement.setString(1, appname);
				preparedStatement.setInt(2, Integer.parseInt(userID));
				preparedStatement.setNull(3, adminId);;
				preparedStatement.setString(4, reqStatus);
				preparedStatement.executeUpdate();
				
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
