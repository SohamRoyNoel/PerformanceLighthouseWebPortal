package controllers.AdminAddAdminControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class AdminAddAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AdminAddAdminController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String uid = request.getParameter("a");
    	int intUID = Integer.parseInt(uid);
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String stss = "Admin";
        String askapplication = Queries.AdminMakesAdmin(intUID, stss);
        
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
