<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="Contents/AdminHeaderContent.jsp" />
<%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%
   ResultSet resultset = null;
	ResultSet resultset1 = null;
	ResultSet resultset2 = null;
	ResultSet resultset3 = null;
   %>
			<p class="home"><a href="#">Home</a> > <strong> Dashboard</strong></p>
			<div class="list_of_members">
				
				<div class="new-users">
					<div class="icon">
						<i class="user1"></i>
					</div>
					<div class="icon-text">
					<%
			         try {
			        	
			         	Connection connection = Connections.getConnection();
			         	Statement statement = connection.createStatement();
			         	resultset = statement.executeQuery(Queries.getUserCountForAdmin);
			         	while (resultset.next()) {
			         %>
						<h3><%=resultset.getString(1)%></h3>
						<%
			            }
				         
			            %>
						<p>Users</p>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="sales">
					<div class="icon">
						<img src="https://img.icons8.com/nolan/64/cloudflare.png"/>
					</div>
					<div class="icon-text">
					<%
					resultset1 = statement.executeQuery(Queries.getApplicationCountForAdmin);
					while (resultset1.next()) {
					%>
						<h3><%=resultset1.getString(1)%></h3>
						<%
			            }
			            %>
						<p>Applications</p>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="orders">
					<div class="icon">
						<img src="https://img.icons8.com/dusk/64/000000/domain.png"/>
					</div>
					<div class="icon-text">
					<%
					resultset2 = statement.executeQuery(Queries.getPageCountForAdmin);
					while (resultset2.next()) {
					%>
						<h3><%=resultset2.getString(1)%></h3>
						<%
			            }
			            %>
						<p>Web pages</p>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="visitors">
					<div class="icon">
						<img src="https://img.icons8.com/cotton/64/000000/test-tube-1.png"/>
					</div>
					<div class="icon-text">
					<%
					resultset3 = statement.executeQuery(Queries.getTSCountForAdmin);
					while (resultset3.next()) {
					%>
						<h3><%=resultset3.getString(1)%></h3>
						<%
			            }
			            %>
						<p>Test Cases</p>
						
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>
			 	
			<%
			         } catch (Exception e) {
				         	out.println("wrong entry" + e);
				         }
						%>
			<div class="cd-tabs">
</div> 

		</div>
		<div class="clearfix">
		jhfjhf
		</div>
	</div>
	<div class="footer">
			<div class="copyright text-center">
					<p>&copy; 2015 All rights reserved | Template by  <a href="http://w3layouts.com">  W3layouts</a></p>
			</div>		
		</div>
</body>
</html>