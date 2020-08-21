<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%
   ResultSet resultset = null;
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Performance Admin Panel</title>
<link href="resources/css/bootstrap.css" rel="stylesheet" type='text/css' />
<!-- Custom Theme files -->
<link href="resources/css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<script src="resources/js/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js" integrity="sha256-KzZiKy0DWYsnwMF+X1DvQngQ2/FxF7MF3Ff72XcpuPs=" crossorigin="anonymous"></script>
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Metro Panel Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--webfont-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

	<script src="resources/js/jquery.nicescroll.js"></script>
	
	 <link href="css/jqvmap.css" media="screen" rel="stylesheet" type="text/css" />
	   <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>  -->
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>
$(document).ready(function() {  
	
	$('#lgout').click(function(event) {
		$.get('../LogoutController', {

		}, function(response) {
			pageRedirect();
        });
	});
	
});

function pageRedirect() {
    window.location.replace("http://10.232.141.154:8094/PerformanceLightHouse/public/login.jsp");
}  
</script>

</head>
<body>
<div class="total-content">
		<div class="col-md-3 side-bar">
			<div class="logo text-center">
				<a href="#"><img width="190" height="110"
				src="https://kitconcept.com/blog/static/lighthouse-logo-af07c4b89f3fa49c62ef529afb647ac3-3067a.png"
				class="img-circle" width="80"></a>
				<h3 style="color: white; font-family: Lucida Calligraphy;"><b>Performance LightHouse</b></h3>
			</div>
			<div class="navigation">
				<h3>Featured</h3>
				<ul>
					<li><a href="AllUsers.jsp"><i class="fas fa-users fa-2x"></i></a></li> &nbsp&nbsp
					<li><a href="AllUsers.jsp">Users</a></li>
				</ul>
				<ul>
					<li><a href="NewAdmin.jsp"><i class="fas fa-crown fa-2x"></i></a></li> &nbsp
					<li><a href="NewAdmin.jsp">Make General Admin</a></li>
				</ul>
				<ul>
					<li><a href="AddApplication.jsp"><i class="fas fa-plus fa-2x"></i></a></li> &nbsp&nbsp&nbsp&nbsp&nbsp
					<li><a href="AddApplication.jsp">Add Application</a></li>
				</ul>
				<ul>
					<!-- Application User Mapper -->
					<li><a href="AccessDetails.jsp"><i class="fas fa-universal-access fa-2x"></i></a></li> &nbsp&nbsp&nbsp&nbsp
					<li><a href="AccessDetails.jsp">Application Access Details</a></li>
				</ul>
				<ul>
					<li><a href="UserRequests.jsp"><i class="fas fa-bell fa-2x"></i></a></li> &nbsp&nbsp&nbsp&nbsp
					<li><a href="UserRequests.jsp">Application Access Requests</a></li>
				</ul>
			</div>
			<div class="navigation">
				<h3>Navigation</h3>
				<ul>
					<li><a href="TestCases.jsp"><i class="fas fa-vials fa-2x"></i></a></li> &nbsp&nbsp
					<li><a href="TestCases.jsp">TestCases</a></li>
				</ul>
				<ul>
					<li><a href="TSHistory.jsp"><i class="fas fa-history fa-2x"></i></a></li>&nbsp&nbsp&nbsp&nbsp&nbsp
					<li><a href="TSHistory.jsp">TestCase History</a></li>
				</ul>
				<ul>
					<li><a href="Pages.jsp"><i class="fas fa-pager fa-2x"></i></a></li>&nbsp&nbsp&nbsp&nbsp&nbsp
					<li><a href="Pages.jsp">Available Pages</a></li>
				</ul>
			</div>
			<div class="navigation">
				<h3>All Others</h3>
				<ul>
					<li><a href="UserRemover.jsp"><i class="fas fa-user-minus fa-2x"></i></a></li>&nbsp&nbsp&nbsp
					<li><a href="UserRemover.jsp">Remove Users</a></li>
				</ul>
				<ul>
					<li><a href="FAQ.jsp"><i class="fas fa-question fa-2x"></i></a></li> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
					<li><a href="FAQ.jsp">FAQ</a></li>
				</ul>
			</div>
		</div>
		<div class="col-md-9 content">
			<div class="home-strip">
				
				<div class="member" style="float: right;">
				<%
					try{
						Connection connection = Connections.getConnection();
			         	Statement statement = connection.createStatement();
			         	HttpSession sessions=request.getSession(false);  
			    		String userID=(String)sessions.getAttribute("LoginID");
			    		int intUID = Integer.parseInt(userID);
			    		String uname = "";
			         	resultset = statement.executeQuery(Queries.authenticateUserName(intUID));
			         	while (resultset.next()) {
			         		if(resultset.getString(10).equals("Admin")){
			         			uname = resultset.getString(2)+" "+ resultset.getString(3);
			         		}
			         	}
					
				%>
					<p><a href="#"><i class="men"></i></a><a href="#"><%=uname %></a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit" id="lgout" class="btn btn-danger">Logout&nbsp&nbsp<i class="fas fa-sign-out-alt fa-1x"></i></button></p>
					<%
			         	}catch(Exception e){
							e.printStackTrace();
						}
					%>
					
			<!-----end-wrapper-dropdown-2---->
			<br>
			<div class="clearfix"></div>
			</div>
			
			<div class="clearfix"></div>	
				</div>
				<div class="clearfix"></div>
				<br>