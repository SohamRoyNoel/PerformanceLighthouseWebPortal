package controllers.updateControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connectionFactory.Connections;
import queryLibrary.Queries;

@WebServlet()
public class AddTestcaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AddTestcaseController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Boolean flag = true;
		String ad = "";
		
		// Get Application name from UI
		String applicationId = request.getParameter("ApNO");
		int intApId = Integer.parseInt(applicationId);
		String TcName = request.getParameter("TcNM");
		PrintWriter out = response.getWriter();
		HttpSession sessions = request.getSession();
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamps = new java.sql.Timestamp(cal.getTimeInMillis());
		HttpSession session=request.getSession(false);  
		String userID=(String)session.getAttribute("LoginID");
		int intUID = Integer.parseInt(userID);
		String ts = timestamps.toString();

		// check if the requested Test Scenerio already exists in Application table
		String appNames = Queries.getTestCaseNamesByApplicationName+intApId;
		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(appNames);
			while(rs.next()) {
				if(rs.getString(2).equalsIgnoreCase(TcName)) {
					flag = false;
				}
			}
			
			// Insert The Application request  to "Application_request_mapper"
			if (flag) {
				String insertReq = Queries.addTestCases;
				int appId = intApId;
				String TcNames = TcName; 
				PreparedStatement preparedStatement = cn.prepareStatement(insertReq);
				preparedStatement.setString(1, TcNames);
				preparedStatement.setInt(2, appId);
				preparedStatement.setInt(3, intUID);
				preparedStatement.setTimestamp(4, timestamps);
				preparedStatement.executeUpdate();
				ad ="New Test Case Inserted Successfully";
				
				
				
				// Insert TestScenario with CREATE status to "TestScenario_Master_History"
				int TsID=0;
				String status = "Create";
				rs = st.executeQuery(Queries.getTestScenarioId(ts, intUID, appId, TcNames));
				while(rs.next()) {
					TsID = rs.getInt(1);
				}
				PreparedStatement preparedStatement1 = cn.prepareStatement(Queries.insertIntoTestScenarioHistory);
				preparedStatement1.setInt(1, TsID);
				preparedStatement1.setString(2, TcNames);
				preparedStatement1.setInt(3, appId);
				preparedStatement1.setInt(4, intUID);
				preparedStatement1.setString(5, status);
				preparedStatement1.setTimestamp(6, timestamps);
				preparedStatement1.executeUpdate();
				
			} else {
				int TsID=0;
				ResultSet rs1 = null;
				ad ="Test Case Already Exists! Do You want to Override?";
				rs1 = st.executeQuery(Queries.getTestScenarioId(intApId, TcName));
				while(rs1.next()) {
					TsID = rs1.getInt(1);
				}
				String arrays[] = {String.valueOf(TsID), TcName,applicationId,userID, "Update", ts };
				sessions.setAttribute("PassValue", arrays);
			}

			out.write("<input type='hidden' id='pqId' value='"+ad+"'/>");
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
