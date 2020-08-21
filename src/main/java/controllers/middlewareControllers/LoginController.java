package controllers.middlewareControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String emails = request.getParameter("email");
		String passwords = request.getParameter("password");
		//String chkBox = request.getParameter("admins");
		//System.out.println("CheckBox value : " + chkBox);
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;
		boolean detAdmin = false;
		String jsonx = null;
		String admin = "";

		Map<String, String> maps = new HashMap<String, String>();
		String askTestCase = Queries.loginUser(emails, passwords);
		int getLoginId = 0;
		String Id = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(askTestCase);
			while(rs.next()) {
				if (rs.getString(5).equalsIgnoreCase(emails) && rs.getString(6).equals(passwords)) {
					flag = true;
					getLoginId = rs.getInt(1);
					Id = String.valueOf(getLoginId);
					admin = rs.getString(10);
					if (admin.equals("Admin")) {
						detAdmin = true;
					}
				}
			}

			if (flag) {
				HttpSession session = request.getSession();
				session.setAttribute("LoginID", Id);
				System.out.println("Login : " + session.getAttribute("LoginID"));
				String x = getServletContext().getRealPath("Base.jsp");

				if (detAdmin == true) {
					response.sendRedirect(request.getContextPath() + "/admin/AllUsers.jsp");
				} else {
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("public/dashboardUser.jsp");
					requestDispatcher.forward(request, response);
				}

			} else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write("<html> <body> <div id='xyz' style='text-align: center;'>");
				out.write("<p id='errmsg'>");
				String greetings = "<script>window.location.replace('https://performancehouse.azurewebsites.net/public/login.jsp'); alert('Invalid Email or Password');</script>";
				System.err.println("Error login");
				out.print(greetings);				

				/*RequestDispatcher requestDispatchers = request
						.getRequestDispatcher("public/login.jsp");
				requestDispatchers.include(request, response);*/
				//response.setHeader("http://10.232.141.154:8094/PerformanceLightHouse" , "/public/login.jsp");
				out.close();
			}




		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
