package controllers.AdminAddAppControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class AdminEditApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AdminEditApplication() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Hitlar");
    	String appId = request.getParameter("a");
    	String appName = request.getParameter("b");
    	int intAppID = Integer.parseInt(appId);
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String stss = "User";
        String askapplication = Queries.updateApplicationForAdmin(intAppID, appName);
        System.out.println(askapplication);
        
        try {
        	cn = Connections.getConnection();
        	Statement sts = cn.createStatement();
			sts.executeUpdate(askapplication);
						

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
