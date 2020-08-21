<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Title Page-->
    <title>Dashboard</title>

    <!-- Fontfaces CSS-->
    <link href="../css/font-face.css" rel="stylesheet" media="all">
    <link href="../css/extranatok.css" rel="stylesheet" media="all">
    <link href="../vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <link href="../vendor/font-awesome-5/css/fontawesome-all.min.css" rel="stylesheet" media="all">
    <link href="../vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">

    <!-- Bootstrap CSS-->
    <link href="../vendor/bootstrap-4.1/bootstrap.min.css" rel="stylesheet" media="all">

    <!-- Vendor CSS-->
    <!-- <link href="vendor/animsition/animsition.min.css" rel="stylesheet" media="all"> -->
    <link href="../vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet" media="all">
    <link href="../vendor/wow/animate.css" rel="stylesheet" media="all">
    <link href="../vendor/css-hamburgers/hamburgers.min.css" rel="stylesheet" media="all">
    <link href="../vendor/slick/slick.css" rel="stylesheet" media="all">
    <link href="../vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="../vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">


   <script src="http://code.jquery.com/jquery-3.5.1.js"></script> 
    <link href="../css/theme.css" rel="stylesheet" media="all">
    <!--Data Table-->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>

    <!--PopUp Modal : Sweet Modal-->
    <link rel="stylesheet" href="../vendor/Sweet-Modal/dev/jquery.sweet-modal.css" />
    <script src="../vendor/Sweet-Modal/min/jquery.sweet-modal.min.js"></script>
    
    <%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>

<%
   ResultSet resultset = null;
   %>
   
<script>
function pageRedirect() {
    window.location.replace("http://10.232.141.154:8094/PerformanceLightHouse/public/login.jsp");
} 
</script>
</head>

<body>
    <div class="page-wrapper">
        <!-- MENU SIDEBAR-->
        <aside class="menu-sidebar d-none d-lg-block">
            <div class="logo">
                <a href="#">
                    <img src="../images/thumbnail.png" style="height: 45px; width: 350px;" alt="Cool Admin" />
                </a>
            </div>
            <div class="menu-sidebar__content js-scrollbar1">
                <nav class="navbar-sidebar">
                    <ul class="list-unstyled navbar__list">
                        <li class="active has-sub">
                            <a class="js-arrow dash" href="#" style="color: white;">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-tachometer-alt"></i><strong>Dashboard</strong></a>
                        </li>
                        <!-- <li class="active has-sub" >
                            <a class="js-arrow" href="" style="color: white;">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <i class="fas fa-question"></i>&nbsp;<strong>FAQ</strong></a>
                        </li> -->
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
                                            <img src="../images/avt.jpg" alt="JHancock" />
                                        </div>
                                        <div class="content">
                                            <a class="js-acc-btn" href="#"><%=resultset.getString(2) + " " +resultset.getString(3) %></a>
                                        </div>
                                        <div class="account-dropdown js-dropdown">
                                            <div class="info clearfix">
                                                <div class="image">
                                                    <a href="#">
                                                        <img src="../images/avt.jpg" alt="John Hancock" />
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
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                
                                    <p style="color: white; font-size: 40px">Performance Light House Admin Panel</p>
                                
                            </div>
                        </div>
                        <div class="row m-t-25">
                            <div class="col-sm-3 col-md-3">
                                <div class="overview-item overview-item--c1">
                                    <div class="overview__inner">
                                        <div class="overview-box clearfix">
                                            <div class="icon">
                                               <img width="65" height="65" src="https://img.icons8.com/color/64/000000/laptop--v1.png"/>
                                            </div>
                                            <div class="text">
                                            <%
									         try {
									         	Connection connection = Connections.getConnection();
									         	Statement statement = connection.createStatement();
									         	resultset = statement.executeQuery(Queries.getApplicationCountForAdmin);
									         	while (resultset.next()) {
									         %>
                                                <h2><%=resultset.getInt(1) %></h2>
                                             <%
									         	}
										         } catch (Exception e) {
										         	out.println("wrong entry" + e);
										         }
									         %>
                                                <span>Applications</span>
                                            </div>
                                        </div>
                                        <div class="overview-chart">
                                            <canvas id=""></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3">
                                <div class="overview-item overview-item--c2">
                                    <div class="overview__inner">
                                        <div class="overview-box clearfix">
                                            <div class="icon">
                                                <img width="65" height="65" src="https://img.icons8.com/cotton/64/000000/test-tube.png"/>
                                            </div>
                                            <div class="text">
                                            <%
									         try {
									        	//getTSCountForAdmin
									         	Connection connection = Connections.getConnection();
									         	Statement statement = connection.createStatement();
									         	resultset = statement.executeQuery(Queries.getTSCountForAdmin);
									         	while (resultset.next()) {
									         %>
                                                <h2><%=resultset.getInt(1) %></h2>
                                                <%
									         	}
										         } catch (Exception e) {
										         	out.println("wrong entry" + e);
										         }
									         %>
                                                <span>Test Cases</span>
                                            </div>
                                        </div>
                                        <div class="overview-chart">
                                            <canvas id=""></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3">
                                <div class="overview-item overview-item--c3">
                                    <div class="overview__inner">
                                        <div class="overview-box clearfix">
                                            <div class="icon">
                                                <img width="65" height="65" src="https://img.icons8.com/color/64/000000/two-pages.png"/>
                                            </div>
                                            <div class="text">
                                            <%
									         try {
									        	//getTSCountForAdmin
									         	Connection connection = Connections.getConnection();
									         	Statement statement = connection.createStatement();
									         	resultset = statement.executeQuery(Queries.getPageCountForAdmin);
									         	while (resultset.next()) {
									         %>
                                                <h2><%=resultset.getInt(1) %></h2>
                                                <%
									         	}
										         } catch (Exception e) {
										         	out.println("wrong entry" + e);
										         }
									         %>
                                                <span>Active Pages</span>
                                            </div>
                                        </div>
                                        <div class="overview-chart">
                                            <canvas id=""></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3">
                                <div class="overview-item overview-item--c4">
                                    <div class="overview__inner">
                                        <div class="overview-box clearfix">
                                            <div class="icon">
                                                 <img width="65" height="65" src="https://img.icons8.com/nolan/64/gears.png"/>
                                            </div>
                                            <div class="text">
                                                <%
									         try {
									        	//getTSCountForAdmin
									         	Connection connection = Connections.getConnection();
									         	Statement statement = connection.createStatement();
									         	resultset = statement.executeQuery(Queries.getResCountForAdmin);
									         	while (resultset.next()) {
									         %>
                                                <h2><%=resultset.getInt(1) %></h2>
                                                <%
									         	}
										         } catch (Exception e) {
										         	out.println("wrong entry" + e);
										         }
									         %>
                                                <span>Resources</span>
                                            </div>
                                        </div>
                                        <div class="overview-chart">
                                            <canvas id=""></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                                              
                           
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="copyright">
                                    <p>Copyright © 2020 JohnHancock. All rights reserved. by <a href="">QECoP</a>.</p>
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
    

    <script src="../vendor/bootstrap-4.1/popper.min.js"></script>
    <script src="../vendor/bootstrap-4.1/bootstrap.min.js"></script>
    
    <script src="../vendor/animsition/animsition.min.js"></script>
    
    <script src="../vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
    <script src="../vendor/select2/select2.min.js">
    </script>

    

    <!-- Main JS-->
    <script src="../js/main.js"></script>

</body>


</html>

    