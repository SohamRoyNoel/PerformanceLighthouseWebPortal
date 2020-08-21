package controllers.middlewareControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import connectionFactory.Connections;
import helperUtils.Randomizer;
import queryLibrary.Queries;

public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogoutController() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Logout Controller");
    	HttpSession session=request.getSession(false);
    	session.removeAttribute("LoginID");
    	
    	Map<Integer, String> mps = new HashMap<Integer, String>();
    	String jsonx = null;
    	jsonx = new Gson().toJson(mps);

		response.setContentType("application/json");
		response.getWriter().write(jsonx);
    	
    	
    	/*System.out.println("Path : " + request.getContextPath()+"/public/Login.jsp");
    	response.sendRedirect(request.getContextPath() + "/public/Login.jsp");*/
    	// response.setHeader("Location", request.getContextPath()+"/Login.jsp");
//    	RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getContextPath() + "/Login.jsp");
//		requestDispatcher.forward(request, response);

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);

		
	}

}
