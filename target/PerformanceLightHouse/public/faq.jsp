<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Title Page-->
    <title>Dashboard</title>

    <!-- Fontfaces CSS-->
    <link href="css/font-face.css" rel="stylesheet" media="all">
    <link href="vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <link href="vendor/font-awesome-5/css/fontawesome-all.min.css" rel="stylesheet" media="all">
    <link href="vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">

    <!-- Bootstrap CSS-->
    <link href="vendor/bootstrap-4.1/bootstrap.min.css" rel="stylesheet" media="all">

    <!-- Vendor CSS-->
    <!-- <link href="vendor/animsition/animsition.min.css" rel="stylesheet" media="all"> -->
    <link href="vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet" media="all">
    <link href="vendor/wow/animate.css" rel="stylesheet" media="all">
    <link href="vendor/css-hamburgers/hamburgers.min.css" rel="stylesheet" media="all">
    <link href="vendor/slick/slick.css" rel="stylesheet" media="all">
    <link href="vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">


   <script src="http://code.jquery.com/jquery-3.5.1.js"></script> 
    
	 <link href="css/theme.css" rel="stylesheet" media="all">
	 <script src="vendor/ckeditor5-build-classic/ckeditor.js"></script>
	 
	 
</head>
<%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>

<%
   ResultSet resultset = null;
   %>
   <script>
   function pageRedirect() {
	    window.location.replace("https://performancehouse.azurewebsites.net/public/login.jsp");
	} 
   $(document).ready(function() {
	   $('.app').click(function(event) {
    		window.location.assign("https://performancehouse.azurewebsites.net/public/Application.jsp");
    	 });	
    	 
    	// FAQ
        $('.faq').click(function(event) {
       	 window.location.assign("https://performancehouse.azurewebsites.net/public/faq.jsp");
    	 });
        
        // Dashboard user
        $('.dash').click(function(event) {
       	 window.location.assign("https://performancehouse.azurewebsites.net/public/dashboardUser1.jsp");
      	 });
        
        $("a[name='lnkViews']").on("click", function (e) {
     		$.get('../LogoutController', {

    		}, function(response) {
    			pageRedirect();
            });
     	});
   });

   </script>
   <style>
.collapsible {
  background-color: #07F778;
  color: Black;
  cursor: pointer;
  padding: 18px;
  width: 100%;
  border: none;
  text-align: left;
  outline: none;
  font-size: 15px;
}



.setcontent {
  padding: 0 18px;
  display: none;
  overflow: hidden;
  background-color: #f1f1f1;
}
</style>
<script>
function copyCat() {
	  var copyText = document.getElementById("lol");
	  copyText.select();
	  copyText.setSelectionRange(0, 99999)
	  document.execCommand("copy");
	  alert("Api key is copied");
	  return false;
	}
	
</script>
<body class="">
    <div class="page-wrapper">
        <!-- MENU SIDEBAR-->
        <aside class="menu-sidebar d-none d-lg-block">
            <div class="logo">
                <a href="#">
                    <img src="images/thumbnail.png" style="height: 45px; width: 350px;" alt="Cool Admin" />
                </a>
            </div>
            <div class="menu-sidebar__content js-scrollbar1">
                <nav class="navbar-sidebar">
                    <ul class="list-unstyled navbar__list">
                        <li class="active has-sub">
                            <a class="js-arrow dash" href="#" style="color: white;">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-tachometer-alt"></i><strong>Dashboard</strong></a>
                        </li>
                        <li class="active has-sub">
                            <a class="js-arrow app" href="Application.jsp" style="color: white;">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-desktop" aria-hidden="true"></i><strong>Application</strong></a>
                        </li>
                        <li class="active has-sub">
                            <a class="js-arrow faq" href="" style="color: white;">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-question"></i><strong>FAQ</strong></a>
                        </li>
                        
                    </ul>
                </nav>
            </div>
        </aside>
        <!-- END MENU SIDEBAR-->

        <!-- PAGE CONTAINER-->
        <div class="page-container">
            <!-- HEADER DESKTOP-->
            <header class="header-desktop">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="header-wrap">
                        <%
				         try {
				         	Connection connection = Connections.getConnection();
				         	Statement statement = connection.createStatement();
				         	String userID=(String)session.getAttribute("LoginID");
				         	int uid = Integer.parseInt(userID);
				         	resultset = statement.executeQuery(Queries.loginUser1(uid));
				         	while (resultset.next()) {
				         %>
                            <div class="form-header">
                                <input class="form-control" style="width: 400px;" value="<%=resultset.getString(7) %>" type="text" name="search" id="lol" readonly/>
                                <button class="au-btn--submit" type="" onclick='copyCat();' title="Click To Copy API Key to ClipBoard">
                                    <i style="color: white" class="fa fa-clone" aria-hidden="true"></i>
                                </button>
                            </div>
                            <div class="header-button">
                                
                                <div class="account-wrap">
                                    <div class="account-item clearfix js-item-menu">
                                        <div class="image">
                                            <img src="images/avt.jpg" alt="JHancock" />
                                        </div>
                                        <div class="content">
                                            <a class="js-acc-btn" href="#"><%=resultset.getString(2) + " " +resultset.getString(3) %></a>
                                        </div>
                                        <div class="account-dropdown js-dropdown">
                                            <div class="info clearfix">
                                                <div class="image">
                                                    <a href="#">
                                                        <img src="images/avt.jpg" alt="John Hancock" />
                                                    </a>
                                                </div>
                                                <div class="content">
                                               
                                                    <h5 class="name">
                                                        <a href="#"><%=resultset.getString(2) +" " +resultset.getString(3) + " ("+ resultset.getString(4) +")" %></a>
                                                    </h5>
                                                    <span class="email"><%=resultset.getString(5) %></span>
                                                    <%
									         	}
										         } catch (Exception e) {
										         	out.println("wrong entry" + e);
										         }
									         %>
                                                </div>
                                            </div>
                                            <!-- <div class="account-dropdown__body">
                                                <div class="account-dropdown__item">
                                                    <a href="#">
                                                        <i class="zmdi zmdi-account"></i>Account</a>
                                                </div>
                                                
                                            </div> -->
                                            <div class="account-dropdown__footer">
                                                <a name='lnkViews'>
                                                    <i class="zmdi zmdi-power"></i>Logout</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <!-- HEADER DESKTOP-->

            <!-- MAIN CONTENT-->
            <div class="main-content">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="col-lg-12">
                            <div class="card">
                                
                                <div class="card-body">
                                    <div class="custom-tab">

                                        <nav>
                                            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                                <a class="nav-item nav-link active" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home"
                                                 aria-selected="true"><strong>FAQ</strong></a>
                                                <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile"
                                                 aria-selected="false"><strong>Ask Me A Question</strong></a>
                                            </div>
                                        </nav>
                                        <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                                            <div class="tab-pane fade show active" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
                                                <div class="col-lg-12">
                                                    <div class="card">
                                                        <div class="card-header bg-primary">
                                                            <strong class="card-title text-light">Already Asked and Answered Questions</strong>
                                                        </div>
                                                        <!--  -->
                                                        <div class="card-body card-block">
                                                            <form action="" method="post" class="form-horizontal">
                                                                <div class="row form-group">
                                                                    
                                                                    <div class="col-12 col-md-12">
                                                                        <!-- Collapsable -->
                                                                        <button type="button" class="collapsible">Open Section 1</button>
																				<div class="setcontent">
																				  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
																		</div>
																		<br><br>
																		<button type="button" class="collapsible">Open Section 2</button>
																				<div class="setcontent">
																				  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
																				</div>
																				<br><br>
																		<button type="button" class="collapsible">Open Section 3</button>
																				<div class="setcontent">
																				  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
																				</div>
																				<br><br>
                                                                        <!-- Collapsable -->
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <!--  -->
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="custom-nav-profile" role="tabpanel" aria-labelledby="custom-nav-profile-tab">
                                                <!--Add Test case-->
                                                <div class="col-lg-12">
                                                    <div class="card">
                                                        <div class="card-header bg-dark">
                                                            <strong class="card-title text-light">Ask Me a Question Now</strong>
                                                        </div>
                                                        <div class="card-body card-block">
                                                            <form action="" method="post" class="form-horizontal">
                                                                
                                                                <div class="row form-group">
                                                                    <div class="col col-md-3">
                                                                        <label for="text-input" class=" form-control-label"><strong>Tell Me How Can I Help You</strong></label>
                                                                    </div>
                                                                    <div class="col-12 col-md-12">
                                                                        <input type="text" id="tcnm" name="text-input" placeholder="Text" class="form-control">
                                                                        
                                                                    </div>
                                                                </div>
                                                            </form>
                                                            <form action="" method="post" class="form-horizontal">
                                                               
                                                                <div class="row form-group">
                                                                    <div class="col col-md-3">
                                                                        <label for="text-input" class=" form-control-label"><strong>Please Provide a Brief Description</strong></label>
                                                                    </div>
                                                                    <div class="col-12 col-md-12">
                                                                        <div id="editor"></div>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <div class="card-footer">
                                                            <button type="submit" id="tcsm" class="btn btn-primary btn-sm">
                                                                <i class="fa fa-dot-circle-o"></i> Submit Question
                                                            </button>
                                                        </div>
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END MAIN CONTENT-->
            <!-- END PAGE CONTAINER-->
        </div>

    </div>
<script>
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
    } else {
      content.style.display = "block";
    }
  });
}

</script>
<script>
    ClassicEditor
        .create( document.querySelector( '#editor' ) )
        .catch( error => {
            console.error( error );
        } );
</script>

    <!-- Jquery JS-->
    <!-- <script src="vendor/jquery-3.2.1.min.js"></script>
    <script src="vendor/jquery-ui.min.js"></script> -->
    <!-- <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script> -->
    <!-- Bootstrap JS-->
    <script src="vendor/bootstrap-4.1/popper.min.js"></script>
    <script src="vendor/bootstrap-4.1/bootstrap.min.js"></script>
    <!-- Vendor JS       -->
    <script src="vendor/slick/slick.min.js">
    </script>
    <script src="vendor/wow/wow.min.js"></script>
    <script src="vendor/animsition/animsition.min.js"></script>
    <!-- <script src="vendor/bootstrap-progressbar/bootstrap-progressbar.min.js"> -->
    </script>
    <script src="vendor/counter-up/jquery.waypoints.min.js"></script>
    <script src="vendor/counter-up/jquery.counterup.min.js">
    </script>
    <!-- <script src="vendor/circle-progress/circle-progress.min.js"></script> -->
    <script src="vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
    <!-- <script src="vendor/chartjs/Chart.bundle.min.js"></script> -->
    <script src="vendor/select2/select2.min.js">
    </script>

    

    <!-- Main JS-->
    <script src="js/main.js"></script>

</body>

</html>
<!-- end document-->
    