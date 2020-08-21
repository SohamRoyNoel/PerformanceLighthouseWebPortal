package controllers.AdminShow;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class UserDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("HITTEr");
    	String appId = request.getParameter("a");
    	int intAppID = Integer.parseInt(appId);
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String stss = "User";
        String askapplication = Queries.deleteUserForAdmin(intAppID);
        System.out.println(askapplication);
        PrintWriter out = response.getWriter();
        try {
        	cn = Connections.getConnection();
        	Statement sts = cn.createStatement();
			sts.executeUpdate(askapplication);
						
			out.write("<script>window.location.replace('https://performancehouse.azurewebsites.net/admin/UserRemover.jsp'); alert('User Removed');</script>");
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
