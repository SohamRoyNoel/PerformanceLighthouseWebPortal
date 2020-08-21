<!DOCTYPE html>
<html lang="en">
<%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%
   ResultSet resultset = null;
   %>
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
    <link href="vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <link href="css/theme.css" rel="stylesheet" media="all">
    <!-- DateRange PIcker-->
    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
	
	<!-- Fusion -->
	<script type="text/javascript" src="vendor/FusionCharts/js/fusioncharts.js"></script>
 	<script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>
 	<!-- Google Charts -->
 	<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css" rel="stylesheet" />
	
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<style>
	.tooltip {
	  position: relative;
	  display: inline-block;
	  border-bottom: 1px dotted black;
	}
	
	.tooltip .tooltiptext {
	  visibility: hidden;
	  width: 120px;
	  background-color: black;
	  color: #fff;
	  text-align: center;
	  border-radius: 6px;
	  padding: 5px 0;
	
	  /* Position the tooltip */
	  position: absolute;
	  z-index: 1;
	}
	
	.tooltip:hover .tooltiptext {
	  visibility: visible;
	}
	</style>

   <script>
   var pageList = 0;
   var pagevalues = null;
   var tcvalues = null;
   var tcList = 0;
   /*google.charts.load('current', {packages: ['corechart', 'bar']});
   google.charts.setOnLoadCallback(drawBarColors);

   function drawBarColors(datam) {
         var data = google.visualization.arrayToDataTable(datam);

         var options = {
           title: '',
           chartArea: {width: '50%'},
           colors: ['#b0120a', '#ffab91'],
           hAxis: {
             title: 'Time Milis',
             minValue: 0
           },
           vAxis: {
             title: 'Milestones'
           }
         };
         var chart = new google.visualization.BarChart(document.getElementById('chart_div108'));
         chart.draw(data, options);
       }*/
   </script>
<script>
function pageRedirect() {
	    window.location.replace("https://performancehouse.azurewebsites.net/public/login.jsp");
	} 
         $(document).ready(function() {         	
        	// Application page	 
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
         	 
        	 
        	 // fINAL GRAPH
         $('#rngpicker').click(function(event) {	
                 var baseSelection = $("input#startA").val();
                 var actSelection = $("input#startB").val();
                 var s = baseSelection.split(" - ");
                 var s1 = actSelection.split(" - ");
                 var xy = pageList.substring(2, pageList.length); // page list
                 var applicationname = $("select#applicationName").val();
                 
                 //console.log("Start : " + s[0] + " End : " + s[1]);
                 //console.log("Start1 : " + s1[0] + " End1 : " + s1[1]);
                 
                 $.get('../BaseActualGraphController', {
                	 baseStart:s[0], baseEnd:s[1], actStart:s1[0], actEnd:s1[1], pages: xy, apps: applicationname
                 }, function(response) { 
                	//console.log(JSON.parse(response));
           	    	var x = response;
           	    	console.log(x);
           	    	var topStores='';
           	    	FusionCharts.ready(function(x) {
           	    		//var FusionCharts = require('fusioncharts');  
           	    		var revenueChart = new FusionCharts({
                           type: 'scrollline2D',
                           renderAt: 'charter-containers3',
                           width: '1530',
                           height: '470',
                           dataFormat: 'json',
                           dataSource: response                  
                           
           	    }).render();
               });
                 });
                 
                 });
         
        	 // get page name
         $('#applicationName').change(function(event) {	
                 var applicationname = $("select#applicationName").val();
                 //console.log(applicationname);

                 $.get('../PageController', {
                	 ApplicationName : applicationname
                 }, function(response) {               


                	 console.log("Response : " + response);
         
                 var select = $('#page');
                 select.find('option').remove();
                 
                   $.each(response, function(index, value) {
                	pageList = pageList+ ',' + index;
					pagevalues = pagevalues +','+ value;
					
                   $('<option>').val(index).text(value).appendTo(select);
               });
                 });
                 });
           
      // Get the Test Scenerio of choosen state
         $('#page').click(function(event) {
        	 console.log("ami es6e");
         	    var pageNO = $("select#page").val();
         	   var applicationname = $("select#applicationName").val();
         	   var pagevalue = document.getElementById('page');
         	  var ddlArray= 0;
         	 console.log("PGval Age: " + pageNO);
         	  var pagevalue1 = $("#page option:selected").text();
                console.log("PGval : " + pageNO);
                //var pagevalue1 = $("#page option:selected").text();
				
                var xy=0;
                var pageIds = null;
                if(pagevalue1 == 'ALL'){
                	/*for (i = 0; i < ddl.options.length; i++) {
                		   ddlArray  += ddl .options[i].value+",";
                		}
                	var idOfAllElements = ddlArray.substring(1, ddlArray.length-1);*/
	               	 xy = pageList.substring(2, pageList.length);
	               	 console.log("page list : " + pageList);
	               	 console.log("xy : " + xy);
	               	 
	               	$.get('../TestCaseController', {
         	            pgNo : xy, apID:applicationname, pgType:'ALL',
         	    }, function(response) {
         	
         	    var select = $('#testCases');
         	    select.find('option').remove();
         	      $.each(response, function(index, value) {
         	    	 tcList = tcList+ ',' + index;
  					tcvalues = tcvalues +','+ value;
         	    	  
         	      $('<option>').val(index).text(value).appendTo(select);
         	  });
         	    });
                }else{
                	pagevalue1 = 'None';
                	$.get('../TestCaseController', {
                		
         	            pgNo : pageNO, apID:applicationname, pgType:pagevalue1,
         	    }, function(response) {
         	
         	    var select = $('#testCases');
         	    select.find('option').remove();
         	      $.each(response, function(index, value) {
         	    	 tcList = tcList+ ',' + index;
  					tcvalues = tcvalues +','+ value;
         	    	  
         	      $('<option>').val(index).text(value).appendTo(select);
         	  });
         	    });
                }
                
                
                
         	    
                });
         
         $('#btns1').click(function(event) {	
        	 
             var applicationname = $("select#applicationName").val();
             var pageNO = $("select#page").val();
             var pagevalue = document.getElementById('page');
      	    var pagevalue1 =$("#page option:selected").text();
      	  var tccvalue = document.getElementById('testCases');
  	    var tccvalue1 = $("#testCases option:selected").text(); // all tc
             /*console.log("PGvalX : " + pagevalue1);
             console.log("PageListX : " + pageList);
             console.log("PageValuesX : " + pagevalues);
             console.log(pageNO);*/
             var xy=0;
             var tcs = 0;
             var pageIds = null;
             var NavGraph = 'NO';
        	 var PgGraph = 'NO';
        	 var elemtGraph = 'NO';
        	 var dtrange = $("input#start").val();
        	 var splitDate = dtrange.split(" - ");
        	 
        	 //console.log("date : " + dtStart);
             if(pagevalue1 == 'ALL'){
            	 xy = pageList.substring(2, pageList.length);
            	 //console.log("Modified : " + xy);
            	 pageIds = xy.split(',');
            	 NavGraph = 'ALL';
            	 PgGraph = 'ALL';
            	 elemtGraph = 'ALL';
            	 
            	 console.log("Modified TC : " + tcList.substring(2, tcList.length));
            	 if(tccvalue1 == 'ALL'){
            		 tcs = tcList.substring(2, tcList.length);
            		 
            	 }else{
            		 tcs = $("select#testCases").val().toString();
            	 }
                	 //console.log("yammer : "+ tcs);
                     var dtStart = splitDate[0];
                     var dtEnd = splitDate[1];
                     //var pageNOs = pageIds[i];
                     // Navigation
                             	            	 //2nd graph 
        	             $.get('../NavigationGraphController', {
        					appNM : applicationname, pgNo : xy,tcNos : tcs, dts : dtStart,dte : dtEnd, flag : NavGraph,
        		  	    }, function(response) {
        		  	    //console.log(JSON.parse(response));
        	      	    	var x = response;
        	      	    	console.log(x);
        	      	    	var topStores='';
        	      	    	FusionCharts.ready(function(x) {
        	      	    		//alert("How low");
        	      	    		//var FusionCharts = require('fusioncharts');  
        	      	    		var xx = x;
        	      	    		console.log(xx);
        	      	    		var revenueChart = new FusionCharts({
        	                      type: 'msbar2d',
        	                      renderAt: 'charter-containera',
        	                      width: '600',
        	                      height: '500',
        	                      dataFormat: 'json',
        	                      dataSource: response                   
        	                      
        	      	    }).render();
        	          });
        		  	    });
        	             
                     
        	          // Page Load Time : max mean -- 3rd
        	             $.get('../PageLoadGraphController', {
        						appNM : applicationname, pgNo : xy,tcNo : tcs, dts : dtStart,dte : dtEnd, flag : PgGraph,
        			  	    }, function(response) {
        			  	    //console.log(JSON.parse(response));
        		      	    	var x = response;
        		      	    	//console.log(x);
        		      	    	var topStores='';
        		      	    	FusionCharts.ready(function(x) {
        		      	    		//var FusionCharts = require('fusioncharts');  
        		      	    		var xx = x;
        		      	    		//console.log(xx);
        		      	    		var revenueChart = new FusionCharts({
        		                      type: 'scrollcombi2d',
        		                      renderAt: 'charter-containerMax',
        		                      width: '600',
        		                      height: '445',
        		                      dataFormat: 'json',
        		                      dataSource: response                   
        		                      
        		      	    }).render();
        		          }); 	
        			  	    });
        	          
        	          // Element Wise Loading Time -- 1st 
        	            $.get('../ElementLoadingTimeGraphController', {
        						appNM : applicationname, pgNo : xy,tcNo : tcs, dts : dtStart,dte : dtEnd, flag : elemtGraph,
        			  	    }, function(response) {
        			  	    //console.log(JSON.parse(response));
        		      	    	var x = response;
        		      	    	//console.log(x);
        		      	    	var topStores='';
        		      	    	FusionCharts.ready(function(x) {
        		      	    		//var FusionCharts = require('fusioncharts');  
        		      	    		var xx = x;
        		      	    		//console.log(xx);
        		      	    		var revenueChart = new FusionCharts({
        		                      type: 'scrollcombi2d',
        		                      renderAt: 'charter-container',
        		                      width: '900',
        		                      height: '330',
        		                      dataFormat: 'json',
        		                      dataSource: response                   
        		                      
        		      	    }).render();
        		          });
        			  	    });
        	          
        	         // Page Wise Loading Time -- 4th and dependent 5th
        	            $.get('../AllPageLoadGraphController', {
        						appNM : applicationname, pgNo : xy,tcNo : tcs, dts : dtStart,dte : dtEnd, flag : elemtGraph,
        			  	    }, function(response) {
        			  	    //console.log(JSON.parse(response));
        		      	    	var x = response;
        		      	    	//console.log(x);
        		      	    	var topStores='';
        		      	    	FusionCharts.ready(function(x) {
        		      	    		//var FusionCharts = require('fusioncharts');  
        		      	    		var xx = x;
        		      	    		//console.log(xx);
        		      	    		var revenueChart = new FusionCharts({
        		                      type: 'scrollbar2d',
        		                      renderAt: 'charter-containers1',
        		                      width: '600',
        		                      height: '455',
        		                      dataFormat: 'json',
        		                      dataSource: response
        		                      ,events: {
        		                          dataPlotClick: function(ev, props) {
        		                              var infoElem = document.getElementById("infolbl");
        		                              
        		                              //console.log(props.categoryLabel);
        		                              plotIndex = props.dataIndex;
        		                              plotValue = props.categoryLabel;
        		                              var dataStrings = plotIndex + "," + plotValue; 
        		                              console.log("Val : "+ dataStrings);
        		                              
        		                              $.ajax({
        		                            	    type: "POST",
        		                            	    url: "../LinkedchartServlet2",
        		                            	    data: { dt : plotIndex, dt1 : plotValue },
        		                            	    success: function(data) {
        		                            	    	//console.log(JSON.parse(response));
        		                              	    	var x = data;
        		                              	    	console.log(x);
        		                              	    	var topStores='';
        		                              	    	FusionCharts.ready(function(x) {
        		                              	    		//var FusionCharts = require('fusioncharts');  
        		                              	    		var xx = x;
        		                              	    		console.log(xx);
        		                              	    		var revenueChart1 = new FusionCharts({
        		                                              type: 'scrollline2d',
        		                                              renderAt: 'charter-containers2',
        		                                              width: '600',
        		                                              height: '455',
        		                                              dataFormat: 'json',
        		                                              dataSource: data                   
        		                                              
        		                              	    }).render();
        		                                  });
        		                            	    }
        		                            	});
        		                           }
        		                        }
        		                      
        		      	    }).render();
        		          });
        			  	    });
                 
             }else{
            	 var testCsNO = $("select#testCases").val().toString();
            	 var testLength = testCsNO.length;
            	 //console.log("Ducking length : "+ testLength)
                 var dtStart = splitDate[0];
                 var dtEnd = splitDate[1];
                 
				if(tccvalue1 == 'ALL'){
                	 
                	 testCsNO = tcList.substring(2, tcList.length);
            		 
            	 }else{
            		 testCsNO = $("select#testCases").val().toString();
            	 }
                 
                 // Navigation
    	             $.get('../NavigationGraphController', {
    					appNM : applicationname, pgNo : pageNO,tcNos : testCsNO, dts : dtStart,dte : dtEnd, flag : NavGraph,
    		  	    }, function(response) {
    		  	    //console.log(JSON.parse(response));
    	      	    	var x = response;
    	      	    	console.log(x);
    	      	    	var topStores='';
    	      	    	FusionCharts.ready(function(x) {
    	      	    		//alert("How low");
    	      	    		//var FusionCharts = require('fusioncharts');  
    	      	    		var xx = x;
    	      	    		console.log(xx);
    	      	    		var revenueChart = new FusionCharts({
    	                      type: 'msbar2d',
    	                      renderAt: 'charter-containera',
    	                      width: '600',
    	                      height: '500',
    	                      dataFormat: 'json',
    	                      dataSource: response                   
    	                      
    	      	    }).render();
    	          });
    		  	    });
    	             
                 
    	          // Page Load Time
    	             $.get('../PageLoadGraphController', {
    						appNM : applicationname, pgNo : pageNO,tcNo : testCsNO, dts : dtStart,dte : dtEnd, flag : NavGraph,
    			  	    }, function(response) {
    			  	    //console.log(JSON.parse(response));
    		      	    	var x = response;
    		      	    	//console.log(x);
    		      	    	var topStores='';
    		      	    	FusionCharts.ready(function(x) {
    		      	    		//var FusionCharts = require('fusioncharts');  
    		      	    		var xx = x;
    		      	    		//console.log(xx);
    		      	    		var revenueChart = new FusionCharts({
    		                      type: 'scrollcombi2d',
    		                      renderAt: 'charter-containerMax',
    		                      width: '600',
    		                      height: '445',
    		                      dataFormat: 'json',
    		                      dataSource: response                   
    		                      
    		      	    }).render();
    		          });  	
    			  	    });
    	          
    	          // Element Wise Loading Time
    	            $.get('../ElementLoadingTimeGraphController', {
    						appNM : applicationname, pgNo : pageNO,tcNo : testCsNO, dts : dtStart,dte : dtEnd, flag : NavGraph,
    			  	    }, function(response) {
    			  	    //console.log(JSON.parse(response));
    		      	    	var x = response;
    		      	    	//console.log(x);
    		      	    	var topStores='';
    		      	    	FusionCharts.ready(function(x) {
    		      	    		//var FusionCharts = require('fusioncharts');  
    		      	    		var xx = x;
    		      	    		//console.log(xx);
    		      	    		var revenueChart = new FusionCharts({
    		                      type: 'scrollcombi2d',
    		                      renderAt: 'charter-container',
    		                      width: '900',
    		                      height: '330',
    		                      dataFormat: 'json',
    		                      dataSource: response                   
    		                      
    		      	    }).render();
    		          });
    			  	    });
    	          
    	         // Page Wise Loading Time
    	            $.get('../AllPageLoadGraphController', {
    	            	appNM : applicationname, pgNo : pageNO,tcNo : testCsNO, dts : dtStart,dte : dtEnd, flag : NavGraph,
    			  	    }, function(response) {
    			  	    //console.log(JSON.parse(response));
    		      	    	var x = response;
    		      	    	//console.log(x);
    		      	    	var topStores='';
    		      	    	FusionCharts.ready(function(x) {
    		      	    		//var FusionCharts = require('fusioncharts');  
    		      	    		var xx = x;
    		      	    		//console.log(xx);
    		      	    		var revenueChart = new FusionCharts({
    		                      type: 'scrollbar2d',
    		                      renderAt: 'charter-containers1',
    		                      width: '600',
    		                      height: '455',
    		                      dataFormat: 'json',
    		                      dataSource: response 
    		                      ,events: {
    		                          dataPlotClick: function(ev, props) {
    		                              var infoElem = document.getElementById("infolbl");
    		                              
    		                              //console.log(props.categoryLabel);
    		                              plotIndex = props.dataIndex;
    		                              plotValue = props.categoryLabel;
    		                              var dataStrings = plotIndex + "," + plotValue; 
    		                              console.log("Val : "+ dataStrings);
    		                              
    		                              $.ajax({
    		                            	    type: "POST",
    		                            	    url: "../LinkedchartServlet2",
    		                            	    data: { dt : plotIndex, dt1 : plotValue },
    		                            	    success: function(data) {
    		                            	    	//console.log(JSON.parse(response));
    		                              	    	var x = data;
    		                              	    	console.log(x);
    		                              	    	var topStores='';
    		                              	    	FusionCharts.ready(function(x) {
    		                              	    		//var FusionCharts = require('fusioncharts');  
    		                              	    		var xx = x;
    		                              	    		console.log(xx);
    		                              	    		var revenueChart1 = new FusionCharts({
    		                                              type: 'scrollline2d',
    		                                              renderAt: 'charter-containers2',
    		                                              width: '600',
    		                                              height: '455',
    		                                              dataFormat: 'json',
    		                                              dataSource: data                   
    		                                              
    		                              	    }).render();
    		                                  });
    		                            	    }
    		                            	});
    		                           }
    		                        }
    		                      
    		      	    }).render();
    		          });
    			  	    });
             }
             
             
             
             
             
             
             });
         });
		 
      </script>
</head>
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

<body>
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
									         	String userID=(String)session.getAttribute("LoginID");
									         	int uid = Integer.parseInt(userID);
									         	resultset = statement.executeQuery(Queries.countAppUser(uid));
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
									         	String userID=(String)session.getAttribute("LoginID");
									         	int uid = Integer.parseInt(userID);
									         	resultset = statement.executeQuery(Queries.getTSCountForAdmin + " where TS_Reg_UserID="+uid);
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
									         	String userID=(String)session.getAttribute("LoginID");
									         	int uid = Integer.parseInt(userID);
									         	resultset = statement.executeQuery(Queries.countpageUser(uid));
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
									         	String userID=(String)session.getAttribute("LoginID");
									         	int uid = Integer.parseInt(userID);
									         	resultset = statement.executeQuery(Queries.countElementUser(uid));
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
                        <div class="row">
                            
                            <div class="col-sm-9 col-md-6 col-lg-8">
                                <div class="au-card-title" style="background-image:url('images/bg-title-02.jpg');">
                                    <div class="bg-overlay bg-overlay--blue"></div>
                                    <h3>
                                        <i class="zmdi zmdi-comment-text"></i>Average Resource Loading Durations 
                                    </h3>
                                </div>
                                <div class="au-card chart-percent-card" style="height: 386px;">
                                    <div class="au-card-inner">                                        
                                       <div style="width: 300px; height: 330px"  id="charter-container" alt="Data Will be loaded here"><p style="text-align: center; vertical-align: middle; line-height: 275px; column-width: 1520px;  ">Data will be loaded here</p></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-6 col-lg-4">
                               
                                <div class="au-card au-card--bg-blue au-card-top-countries m-b-40" style="height: 500px;">
                                    <div class="au-card-inner">
                                    <%
								         try {
								        	
								         	Connection connection = Connections.getConnection();
								         	Statement statement = connection.createStatement();
								         	resultset = statement.executeQuery(Queries.askApplicationname);
								         %>
                                        <div class="row form-group">
                                            <div class="col col-md-12">
                                                <label for="select" class=" form-control-label" style="color: black;">Select Application Name</label><br>
                                            </div>
                                            <div class="col-12 col-md-12">
                                                <select id="applicationName" name="select" id="select" class="form-control">
                                                    <option value="0">Please select</option>
                                                    <%
							            	while (resultset.next()) {
							            %>
											<option value="<%=resultset.getString(1)%>"><%=resultset.getString(2)%></option>
										<%
								            }
								            %>
                                                </select>
                                            </div>
                                        </div>
                                        <%
								         } catch (Exception e) {
								         	out.println("wrong entry" + e);
								         }
							         %>
                                        <div class="row form-group">
                                            <div class="col col-md-12">
                                                <label for="select" class=" form-control-label" style="color: black;">Select Page Name</label><br>
                                            </div><br>
                                            <div class="col-12 col-md-12">
                                                <select id="page" name="select" id="select" class="form-control">
                                                    <option disabled selected value="0">Please select page name</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row form-group">
                                            <div class="col-12 col-md-12">
                                                <label for="select" class=" form-control-label" style="color: black;">Select Test Cases</label><br>
                                            </div>
                                            <div class="col-lg-12">
                                                <select name="multiple-select" id="testCases" multiple="" class="form-control multiple-select" placeholder="Select Test Cases">
                                                    <option disabled value="0">Please select Your Test Cases</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row form-group">
                                            <div class="col col-md-12">
                                                <label for="select" class=" form-control-label" style="color: black;">Select Date Range</label><br>
                                            </div>
                                            <div class="col-12 col-md-12">
                                                <input type="text" id="start" class="form-control" name="datefilter" value="" />                                   
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row form-group">
                                            <div class="col-12 col-md-12"></div>
                                            <button type="button" id="btns1" class="btn btn-success btn-lg btn-block">Get Result</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-md-6 col-lg-6" >
                                <div class="au-card-title" style="background-image:url('images/bg-title-02.jpg');">
                                    <div class="bg-overlay bg-overlay--blue"></div>
                                    <h3>
                                        <i class="zmdi zmdi-comment-text"></i>Average Page Rendering Milestones
                                    </h3>
                                </div>
                                <div class="au-card chart-percent-card" style="height: 500px;">
                                    <div class="au-card-inner">   
                                      <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>                                     
                                       
							<div style="width: 300px; height: 330px"  id="charter-containera" alt="Data Will be loaded here"><p style="text-align: center; vertical-align: middle; line-height: 275px; column-width: 1520px;  ">Data will be loaded here</p></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <div class="au-card-title" style="background-image:url('images/bg-title-02.jpg');">
                                    <div class="bg-overlay bg-overlay--blue"></div>
                                    <h3>
                                        <i class="zmdi zmdi-comment-text"></i>Max, Mean, Min Loading Times
                                    </h3>
                                </div>
                                <div class="au-card chart-percent-card" style="height: 500px;">
                                    <div class="au-card-inner">                                        
                                       
									<div style="width: 300px; height: 330px"  id="charter-containerMax" alt="Data Will be loaded here"><p style="text-align: center; vertical-align: middle; line-height: 275px; column-width: 1520px;  ">Data will be loaded here</p></div>
							<center><div id="columns_div" style="width: 675px; height: 475px;"><p style="text-align: center; vertical-align: middle; line-height: 410px; column-width: 720px;  ">Data will be loaded here</p></div></center>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <div class="au-card-title" style="background-image:url('images/bg-title-02.jpg');">
                                    <div class="bg-overlay bg-overlay--blue"></div>
                                    <h3>
                                        <i class="zmdi zmdi-comment-text"></i>Webpage Loading Times
                                    </h3>
                                </div>
                                <div class="au-card chart-percent-card" style="height: 500px;">
                                    <div class="au-card-inner">                                        
                                       <div style="width: 300px; height: 330px"  id="charter-containers1" alt="Data Will be loaded here"><p style="text-align: center; vertical-align: middle; line-height: 275px; column-width: 1520px;  ">Data will be loaded here</p></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <div class="au-card-title" style="background-image:url('images/bg-title-02.jpg');">
                                    <div class="bg-overlay bg-overlay--blue"></div>
                                    <h3>
                                        <i class="zmdi zmdi-comment-text"></i>Individual Page Details</h3>
                                </div>
                                <div class="au-card chart-percent-card" style="height: 500px;">
                                    <div class="au-card-inner">                                        
                                       <div style="width: 300px; height: 330px"  id="charter-containers2" alt="Data Will be loaded here"><p style="text-align: center; vertical-align: middle; line-height: 275px; column-width: 1520px;  ">Data will be loaded here</p></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="au-card-title" style="background-image:url('images/bg-title-02.jpg');">
                                    <div class="bg-overlay bg-overlay--blue"> </div>
                                    <h3>
                                    <table style="cellspacing: 40px;  border-spacing: 40px; ">
                                    <tr>
                                    <td width="38%"><i class="zmdi zmdi-comment-text"></i>Page Comparison Base vs Actual</td>
                                    <td style="padding: 5px; font-size: 18px"><label for="datefilter2">Base Date Range: </label></td>
                                    <td style="padding: 5px"><input type="text" placeholder="Select Base Date Range" id="startA" class="form-control" name="datefilter1" value="" /></td>
                                    <td style="padding: 5px; font-size: 18px"><label for="datefilter2">Actual Date Range: </label></td>
                                    <td style="padding: 5px"><input type="text" id="startB" placeholder="Select Actual Date Range" class="form-control" name="datefilter2" value="" /></td>
                                    <td style="padding: 5px"><Button type="submit" id="rngpicker" class="btn btn-successHours"><strong><p style="color: white">Generate</p></strong></Button></td>
                                    </tr>
                                    </table>
                                        </h3>
                                        
                                </div>
                                <div class="au-card chart-percent-card" style="height: 500px;">
                                    <div class="au-card-inner">                                        
                                       <div style="width: 300px; height: 330px"  id="charter-containers3" alt="Data Will be loaded here"><p style="text-align: center; vertical-align: middle; line-height: 275px; column-width: 1520px;  ">Data will be loaded here</p></div>
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
    <script>
        $(document).ready(function() {
        $('.multiple-select').select2();
    });
    </script>
    <script type="text/javascript">
        $(function() {
        
          $('input[name="datefilter"]').daterangepicker({
              autoUpdateInput: false,
              locale: {
                  cancelLabel: 'Clear'
              }
          });
        
          $('input[name="datefilter"]').on('apply.daterangepicker', function(ev, picker) {
              $(this).val(picker.startDate.format('MM/DD/YYYY') + ' - ' + picker.endDate.format('MM/DD/YYYY'));
          });
        
          $('input[name="datefilter"]').on('cancel.daterangepicker', function(ev, picker) {
              $(this).val('');
          });
          
          // 1
          $('input[name="datefilter1"]').daterangepicker({
              autoUpdateInput: false,
              locale: {
                  cancelLabel: 'Clear'
              }
          });
        
          $('input[name="datefilter1"]').on('apply.daterangepicker', function(ev, picker) {
              $(this).val(picker.startDate.format('MM/DD/YYYY') + ' - ' + picker.endDate.format('MM/DD/YYYY'));
          });
        
          $('input[name="datefilter1"]').on('cancel.daterangepicker', function(ev, picker) {
              $(this).val('');
          });
          
          // 2
          $('input[name="datefilter2"]').daterangepicker({
              autoUpdateInput: false,
              locale: {
                  cancelLabel: 'Clear'
              }
          });
        
          $('input[name="datefilter2"]').on('apply.daterangepicker', function(ev, picker) {
              $(this).val(picker.startDate.format('MM/DD/YYYY') + ' - ' + picker.endDate.format('MM/DD/YYYY'));
          });
        
          $('input[name="datefilter2"]').on('cancel.daterangepicker', function(ev, picker) {
              $(this).val('');
          });
        
        });
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
    <!-- <script src="vendor/slick/slick.min.js"> -->
    </script>
    <!-- <script src="vendor/wow/wow.min.js"></script> -->
    <script src="vendor/animsition/animsition.min.js"></script>
    <!-- <script src="vendor/bootstrap-progressbar/bootstrap-progressbar.min.js"> -->
    </script>
    <!-- <script src="vendor/counter-up/jquery.waypoints.min.js"></script> -->
    <!-- <script src="vendor/counter-up/jquery.counterup.min.js"> -->
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
