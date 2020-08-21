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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class DeleteTestScenarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteTestScenarioController() {
        super();
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
		Statement st = null;
		boolean auth = false;
		String Status = "Delete";
		System.out.println("Bools Flag : " + boolsFlag);
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamps = new java.sql.Timestamp(cal.getTimeInMillis());
		try {
			// check user is the owner
			cn = Connections.getConnection();
			String getTsid = Queries.deleteTestScenarioByUser(intTsID);
			Statement sts = cn.createStatement();
			sts.executeUpdate(getTsid);
			// Update History
			updateHistoryTable(cn, intTsID, oldTcName, intApp, intUID, Status, timestamps);
			
		} catch (Exception e) {	}
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
