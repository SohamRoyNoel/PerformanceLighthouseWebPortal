package controllers.dropdownControllers;

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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class PageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PageController() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID=null;
		HttpSession session=request.getSession(false);  
		userID=(String)session.getAttribute("LoginID");

		if(userID == null) {
			String applicationName = request.getParameter("ApplicationName");
			int intApID = Integer.parseInt(applicationName);
			String jsonx = null;
			Connection cn = null;
			Statement st = null;
			ResultSet rs = null;
			Map<Integer, String> mps = new HashMap<Integer, String>();
			int z = 0;

			String askapplication = Queries.askPageName(intApID);

			try {
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askapplication);
				while(rs.next()) {
					mps.put(rs.getInt(1), rs.getString(2));
					z++;
				}
				mps.put(-1, "ALL");
				jsonx = new Gson().toJson(mps);

				response.setContentType("application/json");
				response.getWriter().write(jsonx);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			int uid = Integer.parseInt(userID);
			String applicationName = request.getParameter("ApplicationName");
			int intApID = Integer.parseInt(applicationName);
			String jsonx = null;
			Connection cn = null;
			Statement st = null;
			ResultSet rs = null;
			Map<Integer, String> mps = new HashMap<Integer, String>();
			int z = 30;

			String askapplication = Queries.askPageName(intApID, uid);

			try {
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askapplication);
				while(rs.next()) {
					mps.put(rs.getInt(1), rs.getString(2));
					System.out.println("ELEMNETS : " + rs.getInt(1)+" "+ rs.getString(2));
					z++;
				}
				mps.put(z+1, "ALL");
				System.err.println("Value of z : "+ z);
				System.err.println("Value of MAP : "+ mps);
				jsonx = new Gson().toJson(mps);

				response.setContentType("application/json");
				response.getWriter().write(jsonx);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
