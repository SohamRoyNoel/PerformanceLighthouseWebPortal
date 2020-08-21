package controllers.middlewareControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;
import helperUtils.Randomizer;
import queryLibrary.Queries;

public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistrationController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String fname = request.getParameter("fn");
    	String lname = request.getParameter("ln");
    	String uname = request.getParameter("un");
    	String email = request.getParameter("em");
    	String password = request.getParameter("ps");
    	String securityqus = request.getParameter("secQ");
    	String securityAns = request.getParameter("secAns");
    	String apiToken = Randomizer.RandomCustomAPI();
    	String UserRole = "User";
    	Map<String, String> maps = new HashMap<String, String>();
    	// System.out.println("nm : " + name + " em : " + email + " pname " + pname + " password " + password + " sqcQ  " + securityQus + " sqcA " + securityAns);
    	
    	Statement st = null;
		ResultSet rs = null;
		boolean flag = true;
		String jsonx = null;
		
    	String query = Queries.registerUser();
    	String regSuccessQuery = Queries.askRegisterUser;
    	try {
    		Connection connection = Connections.getConnection();
    		st = connection.createStatement();
			rs = st.executeQuery(regSuccessQuery);
			while(rs.next()) {
				if(rs.getString(3).equals(email)){
					flag = false;
				} 
			}
    		
			if (flag) {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, fname);
				preparedStatement.setString(2, lname);
				preparedStatement.setString(3, uname);
				preparedStatement.setString(4, email);
				preparedStatement.setString(5, password);
				preparedStatement.setString(6, apiToken);
				preparedStatement.setInt(7, Integer.parseInt(securityqus));
				preparedStatement.setString(8, securityAns);
				preparedStatement.setString(9, UserRole);
				preparedStatement.executeUpdate();
				
				maps.put("greetings", "Login Successful");
			} else {
				String greetings = "Same Email Id already exists. You should Login";
				maps.put("greetings", "Same Email Id already exists. You should Login");
			}
        	
			jsonx = new Gson().toJson(maps);
			System.out.println("json : " + jsonx);

			response.setContentType("application/json");
			response.getWriter().write(jsonx);
			
    	}catch (SQLException e) {
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
