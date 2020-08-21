package controllers.updateControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connectionFactory.Connections;
import queryLibrary.Queries;


public class TestScenarioHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public TestScenarioHistoryController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session=request.getSession(false);  
		String userID[]=(String[])session.getAttribute("PassValue");
		
		Connection cn = null;
		try {
			cn = Connections.getConnection();
			PreparedStatement preparedStatement1 = cn.prepareStatement(Queries.insertIntoTestScenarioHistory);
			preparedStatement1.setInt(1, Integer.parseInt(userID[0]));
			preparedStatement1.setString(2, userID[1]);
			preparedStatement1.setInt(3, Integer.parseInt(userID[2]));
			preparedStatement1.setInt(4, Integer.parseInt(userID[3]));
			preparedStatement1.setString(5, userID[4]);
			preparedStatement1.setTimestamp(6, Timestamp.valueOf(userID[5]));
			preparedStatement1.executeUpdate();
			
			Statement st = cn.createStatement();
			st.executeUpdate(Queries.updateTsMaster(Integer.parseInt(userID[0]), Integer.parseInt(userID[3])));
		} catch (SQLException e) {
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
