package controllers.AdminRequestRejectControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class AdminRejectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminRejectController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
 protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 System.out.println("Hello****");
    	
    	String reqID = request.getParameter("a");
    	int intreqID = Integer.parseInt(reqID);
    	String AppName = request.getParameter("b");
    	String AppUserNM = request.getParameter("c");
    	String AppUserEm = request.getParameter("d");
    	int intAppID = Integer.parseInt(reqID);
    	
		PrintWriter out = response.getWriter();
    	
    	int requestUID = 0;
    	int targetappID = 0;
    	
    	// Admin ID
    	HttpSession session=request.getSession(false);  
		String userID=(String)session.getAttribute("LoginID");
		int intApproverId = Integer.parseInt(userID);
    	
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String ad ="";
        // Get the user ID by UserName and Email
        String UID = Queries.getUserIdToAcceptRequest(AppUserEm, AppUserNM);
        
        // Insert 1 Row to Application User Mapper 
        
        try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			
			// get App Id
			String appId = Queries.getAppIds(AppName);
			rs1 = st.executeQuery(appId);
			while(rs1.next()) {
				targetappID = rs1.getInt(1);
			}
			// Requested By : user ID
			rs = st.executeQuery(UID);
			while(rs.next()) {
				requestUID = rs.getInt(1);
			}
			// Update Approved on Request Mapper
			String status = "Denied";
	        String askTestCase = Queries.updateRequestMapper(intApproverId, intreqID, AppName, requestUID, status);
	        Statement sts = cn.createStatement();
			sts.executeUpdate(askTestCase);
			
			
			

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
