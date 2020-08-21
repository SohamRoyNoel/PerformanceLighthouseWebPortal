<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>LightHouse Login</title>
<!-- for-mobile-apps -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Account Login Widget Responsive, Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<!-- //for-mobile-apps -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Lato:400,700,900,300' rel='stylesheet' type='text/css'>
<link href="css/styleLOGIN.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/Login.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/easyResponsiveTabs.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		 $('.dash').click(function(event) {
     		window.location.assign("https://performancehouse.azurewebsites.net/public/dashboardGen.jsp");
     	 });
		
		$('#horizontalTab').easyResponsiveTabs({
			type : 'default', //Types: default, vertical, accordion           
			width : 'auto', //auto or any width like 600px
			fit : true
		// 100% fit in a container
		});
		
		$('#ems, #pss').click(function(){
			$("#errmsg").hide();
		});
		
		// Register User
		$('#btnSub').click(function(event) {
			 var fname = $("input#Fnm").val();
			 var lname = $("input#Lnm").val();
             var uname = $("input#Unm").val();
             var email = $("input#email").val();
             var password = $("input#password").val();
             var secQus = $("select#seqQus").val();
             var secQusAns = $("input#ans").val();
             
             if(fname == '' || lname == '' || uname == '' || email == '' || password == '' || secQus == '' || secQusAns == ''){
            	 alert("All fields should be filled.");
            	 
             }

			$.get('../RegistrationController', {
				fn : fname, ln : lname,un : uname, em : email,ps : password, secQ : secQus,secAns : secQusAns,
			}, function(response) {
				
				var x =response;
	  	    	alert(x);
			});
		});
		
		
		

	});
</script>

</head>
	<%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%
   ResultSet resultset = null;
   %>
<body>
	<div class="content">
		<h1>Welcome To JH Performance LightHouse</h1>
		<div class="main">
			<div class="profile-left wthree">
				<div class="sap_tabs">
					<div id="horizontalTab"
						style="display: block; width: 100%; margin: 0px;">
						<ul class="resp-tabs-list">
							<li class="resp-tab-item" aria-controls="tab_item-0" role="tab"><span>
									Sign in</span></li>
							<li class="resp-tab-item" aria-controls="tab_item-1" role="tab"><h2>
									<span>Sign Up</span>
								</h2></li>
							<div class="clear"></div>
						</ul>
						<div class="resp-tabs-container">
							<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-0">
								<div class="got">
									<h6>Got an account? Enter your details below to login</h6>
								</div>
								<div class="login-top">
									<form action="../LoginController" method="POST">
										<input type="text" class="email" id="ems" name="email" placeholder="Email"
											required="" /> <input type="password" name="password" id="pss" class="password"
											placeholder="Password" required="" /> 
											
											<!-- <label class="container">Check If You Are An Admin
											  <input type="checkbox" name="admins">
											  <span class="checkmark"></span>
											</label> ADD FUNCTIONALITY-->
									<div class="login-bottom">
										<ul>
										
										<li>
											<form>
												<input type="submit" id="loginer" value="LOGIN" />
											</form>
										</li>
											 <li><a class="dash" href="#">Redirect To DashBoard</a></li> 
											
											<ul>
												<div class="clear"></div>
									</div>
									</form>
								</div>
							</div>
							<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-1">
								<div class="login-top sign-top">
									<form action="#" method="post">
										<input type="text" name="name" id="Fnm" class="name active" placeholder="First Name" required="" />
										<input type="text" name="name" id="Lnm" class="name active" placeholder="Last Name" required="" />
										<input type="text" name="name" id="Unm" class="name active" placeholder="User Name" required="" /> 
										<input type="text" name="email" class="email" id="email" placeholder="Your Email" required="" />
										<input type="password" name="password" id="password" class="password" placeholder="Password" required="" />
										<%
								         try {
								        	
								         	Connection connection = Connections.getConnection();
								         	Statement statement = connection.createStatement();
								         	resultset = statement.executeQuery(Queries.askSecurityQuestion);
								         %>
										<select class="select-css" id="seqQus">
										<option>Select Your Security Question</option>
										<%
							            	while (resultset.next()) {
							            %>
											<option value="<%=resultset.getInt(1)%>"><%=resultset.getString(2)%></option>
										<%
								            }
								            %>
									</select>
									<%
								         } catch (Exception e) {
								         	out.println("wrong entry" + e);
								         }
							         %>
										<input type="text" name="name" id="ans" class="name active" placeholder="Answer of your security question" required="" /> 
									</form>
									<div class="login-bottom">
										<ul>
											<li>
												<form action="#" method="post">
													<input type="submit" id="btnSub" value="SIGN UP" />
												</form>
											</li>
											
											<ul>
												<div class="clear"></div>
												
									</div>
									
									<div id="ajaxGetUserServletResponse"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>

			</div>
			<div class="clear"></div>
		</div>
		<p class="footer">
			&copy; 2020 JH Performance Lighthouse. All Rights Reserved | Design by <a
				href=""> CoP Automation Team</a>
		</p>
	</div>
</body>
</html>