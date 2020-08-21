package controllers.updateControllers;

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

public class UpdateTCModalController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateTCModalController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);  
		String userID=(String)session.getAttribute("LoginID");
		int intUid = Integer.parseInt(userID);
		String AppName = request.getParameter("appname");
		String TcNameEdited = request.getParameter("updatedtc");
		int application_id = 0;
		int activeflag = 1;
		String jsonx = null;
		Map<String, String> mps = new HashMap<String, String>();

		ResultSet rs = null;

		Connection cn = null;
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamps = new java.sql.Timestamp(cal.getTimeInMillis());

		try {
			cn = Connections.getConnection();
			ResultSet rs1 = null;
			Statement st = null;

			Statement st1 = null;
			Boolean ifExists = true;
			// Check If TestScenario already Exists
			//rs1 = st1.executeQuery();
			String sql = Queries.authTS1;
			st = cn.createStatement();
			
			//get app Id
			String appSql = Queries.askApplicationname;
			rs = st.executeQuery(appSql);
			while(rs.next()) {
				if(rs.getString(2).equalsIgnoreCase(AppName)) {
					application_id = rs.getInt(1);
				}
			}
			
			// entered existing test case
			rs1 = st.executeQuery(sql);
			while(rs1.next()) {
				if(rs1.getString(2).equals(TcNameEdited) && rs1.getInt(3) == application_id) {
					ifExists = false; 
				}else {
					ifExists = true;
				}
			}
			
			
			if(ifExists) {
				PreparedStatement preparedStatement1 = cn.prepareStatement(Queries.addTestCases);
				preparedStatement1.setString(1, TcNameEdited);
				preparedStatement1.setInt(2, application_id);
				preparedStatement1.setInt(3, intUid);
				preparedStatement1.setTimestamp(4, timestamps);

				preparedStatement1.executeUpdate();

				// Get The test case Id that is added recently
				int recentTc = 0;
				rs1 = st.executeQuery(sql);
				while(rs1.next()) {
					if(rs1.getString(2).equals(TcNameEdited) && rs1.getInt(3) == application_id) {
						recentTc = rs1.getInt(1);
					}
				}
				// Update History
				String status = "Create";
				UpdateTestCaseNameController.updateHistoryTable(cn, recentTc, TcNameEdited, application_id, intUid, status, timestamps);

				String ad = "Test Case Name Updated Successfully";
				mps.put("Status", ad);
				jsonx = new Gson().toJson(mps);

				response.setContentType("application/json");
				response.getWriter().write(jsonx);

			} else {
				String ad = "Again Conflicted TestCase Name For This Application; Please Try to use Different Name!";
				mps.put("Status", ad);
				jsonx = new Gson().toJson(mps);

				response.setContentType("application/json");
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
