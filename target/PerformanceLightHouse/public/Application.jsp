

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Title Page-->
    <title>Dashboard</title>

    <!-- Fontfaces CSS-->
    <link href="css/font-face.css" rel="stylesheet" media="all">
    <link href="css/extranatok.css" rel="stylesheet" media="all">
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
    
    <!--Data Table-->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>

    <!--drag and drop-->
    <script src="vendor/dropzone-5.7.0/dist/dropzone.js"></script>
    <link href="vendor/dropzone-5.7.0/dist/dropzone.css" rel="stylesheet" media="all">

    <!--PopUp Modal : Sweet Modal-->
    <link rel="stylesheet" href="vendor/Sweet-Modal/dev/jquery.sweet-modal.css" />
    <script src="vendor/Sweet-Modal/min/jquery.sweet-modal.min.js"></script>
    
    
        
    <script>
    
    function showPage() {
  	  document.getElementById("loader").style.display = "none";
  	  document.getElementById("myDiv").style.display = "block";
  	}
    
    Dropzone.options.myAwesomeDropzone = {
    		  maxFiles: 1,
    		  acceptedFiles: ".xlsx, .xls",
    		  
    		  accept: function(file, done) {
    			 //console.log("File name 00:  " + file);
    			 //showPage();
    		    done();
    		    
    		  },
    		  init: function() {
    			     			  
    		    this.on("success", function(file, response){
    		    	 
    		    	console.log("Datatable response : "+ JSON.parse(response));
    		    	var n = JSON.parse(response);
    		    	var customer = n["Ts1"];
    		    	/*if(response != '[object Object]'){
	                		populateModalDataTable(response);
	                	} else {
	                		alert("Test Case Inserted Successfully");
	                	} */
	                	//var customer = n["Status"];
							                		                	
	                	console.log("++++++++++++++++++++ " + customer.Status);
	                	//removeLoader();
    		    	if (customer.Status != 'Test Data Inserted Successfully') {
    		    		new populateModalDataTable(response);
					} else {
						alert("Test Case Inserted Successfully");
					}
	                	
    		    });
    		  }
    		};
    
    // Data Table
        $(document).ready(function() {
        	        	
            $('#example').DataTable();
           $('#example1').DataTable();
           $('#example3').DataTable();
                      
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

            // Modal
            /*$(".edits").click(function(){
            	console.log("Edit Request");
                    $.sweetModal.prompt('Update the Test Case name', 'Can I?', 'Test Case Name', function(val) {
                    $.sweetModal('You typed: ' + val);
                });
            });*/

            $(".dels").click(function(){
                $.sweetModal.confirm('Titled Confirm', 'Are you sure you want to delete?', function() {
                    $.sweetModal('Test Case is Deleted!');
                }, function() {
                    $.sweetModal('Test Case is not Deleted');
                });
            });

            $(".req").click(function(){
                $.sweetModal('This is an alert.');
            });
           
            
            
        } );

       
    </script>
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
		
	$('#download').click(function(e) {
	    e.preventDefault();  //stop the browser from following
	    window.location.href = '../DownloadTemplate/TestCaseTemplate.xlsx';
	});
	 
	$("a[name='lnkViews']").on("click", function (e) {
		$.get('../LogoutController', {

		}, function(response) {
			pageRedirect();
       });
	});
	
	// Creates application
	$('#RequestBtn').click(function(event) {
		var apName = $("input#apName").val();
		if(apName == ''){
			alert("Application can not be added when it is empty");
		}else{
			$.get('../CreateApplicationController', {
		       	 ApplicationName : apName
		        }, function(response) {
		        	alert("Requested Application is Accepted, Waiting For Approval");
		        	window.location.assign("https://performancehouse.azurewebsites.net/public/Application.jsp");
		        });
		}
        
	});
	
	// Request Handler
	 $('.xx').click(function(event) {
		 var id = $(this).closest("tr").find(".appId").text();
		 var name = $(this).closest("tr").find(".apNm").text();
		 // alert("Id : " + id + " Name : " + name);
		 $.get('../CreateRequestController', {
	       	 AppId : id, AppN : name,
	        }, function(response) {
		        alert("Request Accepted, Waiting For Approval");
		        window.location.assign("https://performancehouse.azurewebsites.net/public/Application.jsp");
	        });
      });
	
	
	 // Add Test Case Controller
	 $('#tcsm').click(function(event) {
		 var aNO = $("select#app").val();
		 var TcName= $("input#tcnm").val(); 
		 
		 if (TcName != '') {
			 $.get('../AddTestcaseController', {
		       	 ApNO : aNO, TcNM : TcName,
		        }, function(response) {
		        	$('body').append('<div style="display:none;">'+response+'</div>');
		            var r = confirm($('#pqId').val());
		            
	            	if ($('#pqId').val() == "Test Case Already Exists! Do You want to Override?" && r == true) {
	            		$.get('../TestScenarioHistoryController', {
	           	       	 	
	           	        }, function(response) {
	           	        	alert("Test Scenario Owner Is Changed");
	           	        });
					}
		            
		            $("input[type='hidden']").remove();
		      });
		} else {
			alert("Test case name can not be added when it is empty");
		}
	 });
	 

	// Onchange Table Populate
	 $('#apps').change(function(event) {	
         var id = $("select#apps").val();
         _OnChangeAppDtatatablePopulate = id;
         
         
         $.get('../PopulateTableController', {
        	 ApplicationId : id
        	 
         }, function(response) {
             //populateDataTable(response);
             console.log("populating data table...");
		    // clear the table before populating it with more data
		    $("#example1").DataTable().clear();
		    //console.log("Data : " + response);
		    populateDataTable(response);
		   
     });
     });	
	// Delete Test Case Name Controller
	
});

function opener(ctl){
	console.log("I am opener");
	if(!$("#example1 tbody").length == 0){
		$.sweetModal.prompt('Update the Test Case name', null, null,  function(val) {
			/*$("#modalLoginForm").modal('show');	*/
			_row = $(ctl).parents("tr"); 

			var cols = _row.children("td");	 

			 var x = ctl.parentNode.parentNode.rowIndex;
			 var tab = document.getElementById('example1');
			 _activeId = tab.rows[x].cells[0].innerHTML;
			 _activeTCname = tab.rows[x].cells[1].innerHTML;
			 _activeTCOwner = tab.rows[x].cells[2].innerHTML;
			_editedVal = val;
			
			// edit function
			var updateTCName = _editedVal;
			 var row = _activeId;
			 var rowTC = _activeTCname;		
			 var rowOwner = _activeTCOwner;
			 var appid = $("select#apps").val();
			 var ov = "false";
			 $.get('../UpdateTestCaseNameController', {
	        	 TCname : updateTCName, tsID : row, tsName : rowTC, owner : rowOwner, ap:appid, flag : ov,
	         }, function(response) {
	        	 $('body').append('<div style="display:none;">'+response+'</div>');
	        	var r = confirm($('#pqIds').val());
	        	//console.log("R : " + r);
	        	var c = $('#pqIds').val();
	        	var x ="It Seems The Test Scenario Has Other Owner. Do You Want To Take Ownership?";
	        	var t = "true";
	        	if($('#pqIds').val() == x && r == true){
	        		$("input[type='hidden']").remove();
	        		ov = "true";
	        		
	        		$.get('../UpdateTestCaseNameController', {
	        			TCname : updateTCName, tsID : row, tsName : rowTC, owner : rowOwner, ap:appid, flag : ov,
	                }, function(response) {
	                	$('body').append('<div style="display:none;">'+response+'</div>');
	                	alert($('#pqIds').val());
	                	$("#modalLoginForm").modal('hide');
	                	////////////// Refresh datatbale for async load
	                	
	                	$.get('../PopulateTableController', {
	                   	 ApplicationId : _OnChangeAppDtatatablePopulate
	                   	 
	                    }, function(response) {
	                        //populateDataTable(response);
	                        console.log("populating data table...");
	           		    // clear the table before populating it with more data
	           		    $("#example1").DataTable().clear();
	           		    //console.log("Data : " + response);
	           		    populateDataTable(response);
	           		   
	                });
	                });
	        	}else{
	            	//console.log("llll : ");
	        	}        	
	        	$("input[type='hidden']").remove();
	        	$("#modalLoginForm").modal('hide');
	     	});
        }, function() {
        	$.sweetModal('You did not made the change');
        });
		
		
	}	
}
function openerDelete(ctl){
	console.log("I am opener");
	if(!$("#example1 tbody").length == 0){
		 $.sweetModal.confirm('Titled Confirm', 'Are you sure you want to delete?', function() {
			 _row = $(ctl).parents("tr"); 

				var cols = _row.children("td");	 

				 var x = ctl.parentNode.parentNode.rowIndex;
				 var tab = document.getElementById('example1');
				 _activeId = tab.rows[x].cells[0].innerHTML;
				 _activeTCname = tab.rows[x].cells[1].innerHTML;
				 _activeTCOwner = tab.rows[x].cells[2].innerHTML;
				//_editedVal = val;
				
				// delete function
				//var updateTCName = _editedVal;
				 var row = _activeId;
				 var rowTC = _activeTCname;		
				 var rowOwner = _activeTCOwner;
				 var appid = $("select#apps").val();
				 var ov = "false";
				 console.log("Test case Id : " + row);
				 $.get('../DeleteTestScenarioController', {
		        	  tsID : row, tsName : rowTC, owner : rowOwner, ap:appid, flag : ov,
		         }, function(response) {
		        	 $.sweetModal('Test Case is Deleted Successfully');
		     	});
         }, function() {
             $.sweetModal('Test Case is not Deleted');
         });	
		
	}	
}
// Update TC ON MODAL
function openerEditTC(ctl){
	console.log("I am opener");
	if(!$("#example3 tbody").length == 0){
		 _row = $(ctl).parents("tr"); 

	 var cols = _row.children("td");	 

	 var x = ctl.parentNode.parentNode.rowIndex;
	 var tab = document.getElementById('example3');
	 _activeTId = tab.rows[x].cells[0].innerHTML;
	 _activeTTCname = tab.rows[x].cells[1].innerHTML;
	 _activeTTCAPP = tab.rows[x].cells[2].innerHTML;
	 _activeTTCdataUpdate = tab.rows[x].cells[3].children[0].value;
	 console.log("New Mal : "+_activeTId + " " + _activeTTCname + " " + _activeTTCAPP +" " + _activeTTCdataUpdate);
	 if(_activeTTCdataUpdate != ''){
		 console.log(_activeTId + " " + _activeTTCname + " " + _activeTTCAPP +" " + _activeTTCdataUpdate);
		// Update On the fly	
		$(document).on('click', '#puller', function(){
			$.get('../UpdateTCModalController', {
	        	 updatedtc : _activeTTCdataUpdate, appname : _activeTTCAPP,	        	 
	         }, function(response) {
	        	 $.each(response, function(index, value) {
	        		 $('#example3 td:first input').attr('readonly','readonly')
	                	alert(value);
	        		 	tab.rows[x].remove();
	                	//tab.rows[x].cells[3].prop( "disabled", true );
	               });
			   
	     });
		});
	 } else {
		 alert("You cant not edit test case when it is empty");
	 }
         
		
	}	
}
//populate the data table with JSON data
function populateDataTable(data1) {
  console.log("populating data table1111...");
  // clear the table before populating it with more data
  $("#example1").DataTable().clear();
  console.log("Data : " + data1);
  var length=0;
  
  var x = JSON.parse(data1);
  //console.log("bbbb : " + Object.keys(x));
  console.log("HHHHH : " + Object.keys(x).length);
  length = Object.keys(x).length;
  for(var i = 1; i < length+1; i++) {
    var customer = x["Ts"+i];

    // You could also use an ajax property on the data table initialization
    $('#example1').dataTable().fnAddData( [
    	customer.id,
      customer.tsnm,
      customer.userId,
      customer.date,
      customer.Button
    ]);
  }
}

function populateModalDataTable(data1) {
	
	$("#scrollmodal").modal('show');
	  console.log("populating data table1111...");
	  // clear the table before populating it with more data
	  $("#example3").DataTable().clear();
	 // console.log("Data : " + data1);
	  var length=0;
	  
	  var x = JSON.parse(data1);
	  //console.log("bbbb : " + Object.keys(x));
	  //console.log("HHHHH : " + Object.keys(x).length);
	  length = Object.keys(x).length;
	  for(var i = 1; i < length+1; i++) {
	    var customer = x["Ts"+i];

	    // You could also use an ajax property on the data table initialization
	    //console.log("=============== " + customer.slNo);
	    $('#example3').dataTable().fnAddData( [
	    	customer.slNo,
	    	customer.tc,
	      customer.app,
	      customer.tb,
	      customer.Button
	    ]);
	  }
	}

function capable(){
	var x = $('#pqIdsUp').val();
	console.log("Now : " + x);
}
</script>

<script>
var _nextId = 1;
var _activeId = 0;
var _activeTCname = 0;
var _activeTCOwner = 0;
var _row = null;
var _editedVal=null;
var _OnChangeAppDtatatablePopulate = 0;

var _activeTId = 0;
var _activeTTCname = 0;
var _activeTTCAPP = 0;
var _activeTTCdataUpdate = null;

var _ifuploaded = false;

</script>
    <link href="css/theme.css" rel="stylesheet" media="all">
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
</head>


<body class="xyz">
<!-- Modal Plugin -->

<div class="modal-frame">
  <div class="modal">
		    <div class="modal-inset">
      			<div class="button close"><i class="fa fa-close"></i></div>
	
      <div class="modal-body">
				        <h3>Such Modal, Much Animate!</h3>
        				<p>Nothing groundbreaking, but I hope you enjoyed <br /> the physics for the open/close animation =).</p>
        <p class="ps">**I know the SASS is a bit messy, I did this on the fly and for fun <br /> and w.e. I DO WHAT I WANT!!</p>
      			</div>
    		</div>
  	</div>
</div>
<div class="modal-overlay"></div>
<!--  -->
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
                                                 aria-selected="true"><strong>Application</strong></a>
                                                <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile"
                                                 aria-selected="false"><strong>Test Cases</strong></a>
                                            </div>
                                        </nav>
                                        <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                                            <div class="tab-pane fade show active" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
                                                <div class="col-lg-12">
                                                    <div class="card">
                                                        <div class="card-header bg-primary">
                                                            <strong class="card-title text-light">Application Request Form</strong>
                                                        </div>
                                                        <div class="card-body card-block">
                                                            <form action="" method="post" class="form-horizontal">
                                                                <div class="row form-group">
                                                                    <div class="col col-md-3">
                                                                        <label for="hf-email" class=" form-control-label">Application Name</label>
                                                                    </div>
                                                                    <div class="col-12 col-md-9">
                                                                        <input type="email" id="apName" name="hf-email" placeholder="Enter Application Name..." class="form-control">
                                                                        <span class="help-block">Please enter your Application Name if that is not in the list</span>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                         <div class="card-footer">
                                                            <button type="submit" id="RequestBtn" class="btn btn-primary btn-sm">
                                                                <i class="fa fa-dot-circle-o"></i> Submit Application Request
                                                            </button>
                                                        </div> 
                                                        
                                                    </div>
                                                </div>

                                                <div class="col-lg-12">
                                                    <div class="card">
                                                        <div class="card-body text-white bg-primary">
                                                            <strong class="card-title text-light">All Listed Applications here</strong>
                                                        </div>
                                                        <div class="card-header bg-light">
                                                            <table id="example" class="display">
                                                                <thead>
                                                                    <tr>
                                                                        
                                                                        <th>Application Name</th>
                                                                        <th>Application Status</th>
                                                                        <th>Raise Request</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                     <%
																         try {
																        	
																         	Connection connection = Connections.getConnection();
																         	Statement statement = connection.createStatement();
																         	HttpSession sessions=request.getSession(false);  
																			String userID=(String)sessions.getAttribute("LoginID");
																			System.out.println("LOgin id : " + userID);
																			int UID = Integer.parseInt(userID);
																         	resultset = statement.executeQuery(Queries.getAppNameWithStatus(UID));
																         	while (resultset.next()) {
																         %>
																         <tr>
                                                                        
                                                                        <td class="apNm"><%=resultset.getString(1)%></td>
                                                                        <td><%=resultset.getString(2)%></td>
                                                                        <%
																	    	String sts = resultset.getString(2);
                                                                        System.out.println("STS : " + sts);
																	    	if(sts.trim().equals("Take Access")){
																	    %>
                                                                        <td><button type="button" class="btn btn-warning xx"><i class="far fa-question-circle"></i>&nbsp&nbspRequest Access</button></td>
                                                                    	<%
																	    	} else {
																	    %>
																	    <td><button type="button" disabled class="btn btn-warning req"><i class="far fa-question-circle"></i>&nbsp&nbspRequest Access</button></td>
																	    <%
																	    	}
																	    %>
																	    
                                                                    </tr>
                                                                    <%
															            }
															         } catch (Exception e) {
																         	e.printStackTrace();
																         }
															            %>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="custom-nav-profile" role="tabpanel" aria-labelledby="custom-nav-profile-tab">
                                                <!--Add Test case-->
                                                <div class="col-lg-12">
                                                    <div class="card">
                                                        <div class="card-header bg-dark">
                                                            <strong class="card-title text-light">Add Test Case Single </strong>
                                                        </div>
                                                        <div class="card-body card-block">
                                                            <form action="" method="post" class="form-horizontal">
                                                                <div class="row form-group">
                                                                    <div class="col col-md-3">
                                                                        <label for="select" class=" form-control-label">Select an Application Name:</label>
                                                                    </div>
                                                                    <div class="col-12 col-md-9">
                                                                        <select name="select" id="app" class="form-control">
                                                                            <option value="0">Please select</option>
                                                                            <%
																	         try {
																	        	
																	         	Connection connection = Connections.getConnection();
																	         	Statement statement = connection.createStatement();
																	         	HttpSession sessions=request.getSession(false);  
																				String userID=(String)sessions.getAttribute("LoginID");
																				
																				int UID = Integer.parseInt(userID);
																	         	resultset = statement.executeQuery(Queries.getAllApplications(UID));
																	         	while (resultset.next()) {
																	         %>
                                                                            <option value="<%=resultset.getInt(2)%>"><%=resultset.getString(1)%></option>
                                                                            <%
																	            }
																	         } catch (Exception e) {
																		         	out.println("wrong entry" + e);
															        		 }
														            		%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="row form-group">
                                                                    <div class="col col-md-3">
                                                                        <label for="text-input" class=" form-control-label">Add A Test Case Name For The Selected Application:</label>
                                                                    </div>
                                                                    <div class="col-12 col-md-9">
                                                                        <input type="text" id="tcnm" name="text-input" placeholder="Text" class="form-control">
                                                                        <small class="form-text text-muted">Add Test Case if That is not Listed</small>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <div class="card-footer">
                                                            <button type="submit" id="tcsm" class="btn btn-primary btn-sm">
                                                                <i class="fa fa-dot-circle-o"></i> Submit Test Case
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <!--Drag and Drop-->
                                                    <div class="card">
                                                        <div class="card-header bg-dark">
                                                            <strong class="card-title text-light">Drag And Drop Application Name And Test Case(.xlsx format only) Multiple</strong>
                                                        </div>
                                                        <div class="card-body text-white bg-light">
															
                                                            <div class="col-lg-12">
                                                                <form action="../UploadFileController"
                                                                	method="POST"
                                                                    class="dropzone"
                                                                    id="my-awesome-dropzone"
                                                                    enctype="multipart/form-data"
                                                                    ></form>
                                                            </div>
                                                            <br>
                                                        	&nbsp&nbsp&nbsp<button type="button" id="download" class="btn btn-primary" style="color: white; padding-left: 10px"><i class="fa fa-download" aria-hidden="true"></i></button> <font style="color: blue;">*Download the Test Case Template here</font>
                                                       		<!-- <button type="button" class="btn btn-secondary mb-1" data-toggle="modal" data-target="#scrollmodal">
																Scrolling
															</button> -->
                                                        </div>
                                                    </div>
                                                    <!--Explore Test cases-->
                                                    <div class="col-lg-12">
                                                        <div class="card">
                                                            <div class="card-header bg-dark">
                                                                <strong class="card-title text-light">All listed Test Cases are here</strong>
                                                            </div>
                                                            <div class="card-header bg-light">
                                                                <form class="form-header" action="" method="POST">
                                                                	 <div class="col-12 col-md-12">
                                                                        <select name="select" id="apps" class="form-control">
                                                                            <option value="0">Please select</option>
                                                                            <%
																	         try {
																	        	
																	         	Connection connection = Connections.getConnection();
																	         	Statement statement = connection.createStatement();
																	         	HttpSession sessions=request.getSession(false);  
																				String userID=(String)sessions.getAttribute("LoginID");
																				
																				int UID = Integer.parseInt(userID);
																	         	resultset = statement.executeQuery(Queries.getAllApplications(UID));
																	         	while (resultset.next()) {
																	         %>
                                                                            <option value="<%=resultset.getInt(2)%>"><%=resultset.getString(1)%></option>
                                                                            <%
																	            }
																	         } catch (Exception e) {
																		         	out.println("wrong entry" + e);
															        		 }
														            		%>
                                                                        </select>
                                                                    </div>
                                                                    <br>
                                                                    <!-- <input class="au-input au-input--xl col-sm-11" id="myInput11" type="text" name="search" placeholder="Search by Test Case &amp; Name..." /> -->
                                                                    <!-- <button class="au-btn--submit" type="submit">
                                                                        <i class="zmdi zmdi-search"></i>
                                                                    </button> -->
                                                                </form>
                                                                <br>
                                                                <table id="example1" class="display">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>Id</th>
                                                                            <th>Test Case Name</th>
                                                                            <th>Created By</th>
                                                                            <th>Creation Time</th>
                                                                            <th>Edit Or Delete</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    </tbody>
                                                                </table>
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
                    <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="copyright">
                                    <p>Copyright © 2020 JohnHancock. All rights reserved. by <a href="">QECoP</a>.</p>
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
        $('#multiple-select').select2();
    });
        
        
    </script>
    
     <input type="hidden" id="confermations" name="custId" value="${confermation }">
    
    <script>
function myFunction() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("myInput11");
  filter = input.value.toUpperCase();
  table = document.getElementById("example1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[2];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}
function myFunction1() {
	  var input, filter, table, tr, td, i, txtValue;
	  input = document.getElementById("myInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("myTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[1];
	    if (td) {
	      txtValue = td.textContent || td.innerText;
	      if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}
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
    <!-- <script src="vendor/animsition/animsition.min.js"></script> -->
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
	<script>
	/*var myVar;
	
	function myFunctionLoader() {
	 // myVar = setTimeout(showPage, 30000);
	}*/
	
	
	</script>
    

    <!-- Main JS-->
    <script src="js/main.js"></script>
	<div class="modal fade" id="scrollmodal" tabindex="-1" role="dialog" aria-labelledby="scrollmodalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="scrollmodalLabel">Existing Test Case List</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<p>
								<table id="example3" class="display" style="width:100%">
        <thead>
            <tr>
           		 <th>Serial No.</th>
                <th>Test Case name</th>
                <th>Application</th>
                <th>Update</th>
                <th>Confirm</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
							</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
							
						</div>
					</div>
				</div>
			</div>
			<!-- end modal scroll -->
			<!-- Loader Start -->
			   <!--  <div id="loader"></div> -->
			
			<!-- Loader End -->
</body>
<!-- Loader -->

<!-- Loader -->
</html>
<!-- end document-->
    