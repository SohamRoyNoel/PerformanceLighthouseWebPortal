package controllers.updateControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

public class UpdateTestCaseNameController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateTestCaseNameController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tcName = request.getParameter("TCname");
		int TS_AppID = 0;
		String DBTC = "";
		String tsID = request.getParameter("tsID");
		String oldTcName = request.getParameter("tsName");
		String applicationID = request.getParameter("ap");
		int intApp = Integer.parseInt(applicationID);
		String flagParameter = request.getParameter("flag");
		boolean boolsFlag = Boolean.parseBoolean(flagParameter);
		
		String tcOwner = request.getParameter("owner");
		HttpSession session=request.getSession(false);  
		String userID=(String)session.getAttribute("LoginID");
		int intUID = Integer.parseInt(userID);

		int intTsID = Integer.parseInt(tsID);
		Connection cn = null;
		PrintWriter out = response.getWriter();
		ResultSet rs = null;
		ResultSet rs1 = null;
		Statement st = null;
		boolean auth = false;
		String Status = "Update";
		System.out.println("Bools Flag : " + boolsFlag);
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamps = new java.sql.Timestamp(cal.getTimeInMillis());

		try {
			cn = Connections.getConnection();
			if (boolsFlag) {
				Statement st1 = null;
				Boolean ifExists = true;
				// Check If TestScenario already Exists
				//rs1 = st1.executeQuery();
				String sql = Queries.authTS1;
				st = cn.createStatement();
				rs1 = st.executeQuery(sql);
				while(rs1.next()) {
					if(rs1.getString(2).equals(tcName) && rs1.getInt(3) == intApp) {
						ifExists = false;
					}
				}
				if(ifExists) {
					String getTsid = Queries.updateTestScenarioByUser(intTsID, oldTcName, tcName, intUID);
					Statement sts = cn.createStatement();
					sts.executeUpdate(getTsid);

					// Update History
					updateHistoryTable(cn, intTsID, tcName, intApp, intUID, Status, timestamps);

					String ad = "Test Case Name Updated Successfully";
					out.write("<input type='hidden' id='pqIds' value='"+ad+"'/>");
					
				} else {
					String ad = "Conflicted TestCase Name For This Application; Please Try to use Different Name!";
					out.write("<input type='hidden' id='pqIds' value='"+ad+"'/>");
				}
				
			} else {
				// check user is the owner
				String sql = Queries.authenticateUserName(intUID);
				st = cn.createStatement();
				rs = st.executeQuery(sql);
				while(rs.next()) {
					if (rs.getString(4).equals(tcOwner)) {
						auth = true;
					}
				}
				if (auth) {
					Boolean ifExists = true;
					// Check If TestScenario already Exists
					rs = st.executeQuery(Queries.authTS);
					while(rs.next()) {
						if(rs.getString(2).equals(tcName) && rs.getInt(3) == intApp) {
							ifExists = false;
						}
					}
					if(ifExists) {
						// Update TestScenario
						String getTsid = Queries.updateTestScenarioByUser(intTsID, oldTcName, tcName, intUID);
						Statement sts = cn.createStatement();
						sts.executeUpdate(getTsid);
						
						// Update History
						updateHistoryTable(cn, intTsID, tcName, intApp, intUID, Status, timestamps);
						
						String ad = "Test Case Name Updated Successfully";
						out.write("<input type='hidden' id='pqIds' value='"+ad+"'/>");
					}else {
						String ad = "Conflicted TestCase Name For This Application; Please Try to use Different Name!";
						out.write("<input type='hidden' id='pqIds' value='"+ad+"'/>");
					}
					
				} else {
					String ad = "It Seems The Test Scenario Has Other Owner. Do You Want To Take Ownership?";
					out.write("<input type='hidden' id='pqIds' value='"+ad+"'/>");
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateHistoryTable(Connection cn, int TsID, String TsName, int AppID, int RegId, String status, Timestamp tt) {
		try {
			cn = Connections.getConnection();
			PreparedStatement preparedStatement1 = cn.prepareStatement(Queries.insertIntoTestScenarioHistory);
			preparedStatement1.setInt(1, TsID);
			preparedStatement1.setString(2, TsName);
			preparedStatement1.setInt(3, AppID);
			preparedStatement1.setInt(4, RegId);
			preparedStatement1.setString(5, status);
			preparedStatement1.setTimestamp(6, tt);
			preparedStatement1.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
