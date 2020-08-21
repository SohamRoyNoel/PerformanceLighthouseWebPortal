<jsp:include page="Contents/AdminHeaderContent.jsp" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
 <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<%@page import="queryLibrary.Queries"%>
<%@page import="connectionFactory.Connections"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%
   ResultSet resultset = null;
   %>
 <script>
$(document).ready(function() {
    $('#adminapptable').DataTable();
    
    $('#apper').click(function(event) {
 	    var ap = $("input#exampleInputEmail1").val();
 	    if (ap == '') {
			alert("Application Name Can Not Remain Blank.")
		}else {
			 $.get('../AdminAddAppController', {
	 	            apNM : ap,
	 	    }, function(response) {
	 	        
	 	    	alert($('#pqId').val());
	 	    	window.location.assign("https://performancehouse.azurewebsites.net/admin/AddApplication.jsp");
	 	    	//res.send(browserRefresh('index.html'));
	 	    	//$("input[type='hidden']").remove();
	 	    	//alert("Application Added");
	 	    });
		}
 	   //location.reload();
     });
    
 // Remove Application
    $('#adminapptable tbody').on('click', '.xxx', function () {
    	var apID = _activeId;
    	
    	$.get('../AdminDeleteApplication', {
           	 a: apID, 
            }, function(response) {
            	alert("Application is Deactivated");
            	window.location.assign("https://performancehouse.azurewebsites.net/admin/AddApplication.jsp");
            	//$('#example').DataTable().ajax.reload(null, false);
            });
    });
 
 // Retrive Application
    $('#adminapptable tbody').on('click', '.yyy', function () {
    	var apID = _activeId;
    	alert("BOOM");
    	
    	$.get('../AdminRetriveApplicationController', {
           	 a: apID, 
            }, function(response) {
            	alert("Application is Reactivated");
            	//$('#example').DataTable().ajax.reload(null, false);
            	window.location.assign("https://performancehouse.azurewebsites.net/admin/AddApplication.jsp");
            });
    });
 
    // Edit Application
    $('#adminapptable tbody').on('click', '.zzz', function () {
    	
    	var apID = _activeId;
    	console.log("Bootstrap " + _activeAP);
    	//var apName = document.getElementById("adminapptable tbody psp");
    	//console.log("Bootstrap2 " + apName);
    	//var apNM = _activeAP;
    	var x= _editablename;
    	console.log("BOOMs" + apID + " " + x);
    	 
    	$.get('../AdminEditApplication', {
           	 a: apID, b : x,
            }, function(response) {
            	alert("Admin Name is Changed");
            	window.location.assign("https://performancehouse.azurewebsites.net/admin/AddApplication.jsp");
            	//$('#example').DataTable().ajax.reload(null, false);
            });
    });
} );

function loadtable(){
	
}

</script>
<div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold">Update Your TestCase Name and Application Name</h4>
      </div>
      <div class="modal-body mx-3">
        
        <div class="md-form mb-4">
          <input type="text" id="t" class="form-control validate">
          <label data-error="wrong" data-success="right" for="defaultForm-pass">Testcase Name</label>
        </div>

      </div>
      <div class="modal-footer d-flex justify-content-center">
        <button id="s" class="btn btn-success">Update</button>
        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
      </div>
    </div>
  </div>
</div>
<script>
var _nextId = 1;
var _activeId = 0;
var _activeAP = 0;
var _row = null;
var _editablename = null;

</script>
<script>

function opener(ctl){
	_row = $(ctl).parents("tr"); 
	var cols = _row.children("td");	 
	 var x = ctl.parentNode.parentNode.rowIndex;
	 var tab = document.getElementById('adminapptable');
	 _activeId = tab.rows[x].cells[0].innerHTML;
	 _activeAP = tab.rows[x].cells[1].innerHTML;
	 
	 _editablename = tab.rows[x].cells[1].children[0].value ;
	 console.log(_activeAP);
	 console.log(_editablename);
}
</script>
<p class="home"><a href="#">Home</a> > <strong> Add Applications</strong></p>
<br>
<form>
  <div class="form-group">
    <label for="exampleInputEmail1">Add Application</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter Application">
    <small id="emailHelp" class="form-text text-muted">This Application will be created but, User has to raise request to use it.</small>
  </div>
  <button type="submit" id="apper" class="btn btn-primary">Submit Your Application</button>
</form>
<br>
<table id="adminapptable" class="display p" style="width:100%; height:700px">
        <thead>
            <tr>
            	<th>Application ID</th>
                <th>Application Name</th>
                <th>Application Created By</th>
                <th>Application Creation Time</th>
                <th>Application Status</th>
                <th>Delete An Application</th>
            </tr>
        </thead>
        <tbody>
        <%
	         try {
	        	
	         	Connection connection = Connections.getConnection();
	         	Statement statement = connection.createStatement();
	         	resultset = statement.executeQuery(Queries.listAppForAdmin);
	         	while (resultset.next()) {
	    %>
            <tr>
                <td><%=resultset.getInt(1) %></td>
                <td><input type="text" id="psp" class="form-control" value="<%=resultset.getString(2) %>">  </td>
                <td><%=resultset.getString(3) %></td>
                <td><%=resultset.getString(4) %></td>
                <td><%=(resultset.getInt(5) > 0) ? "Active" : "Inactive" %></td>
                <%
                	String y = (resultset.getInt(5) > 0) ? "Active" : "Inactive";
                	if(y.equals("Active")){
                %>
                <td><button type='button' onClick='opener(this);' id='opener' class='btn btn-warning zzz'>Edit AppName <i class="fas fa-edit"></i></button> &nbsp&nbsp <button type='button' id='sure' onClick='opener(this);' class='btn btn-danger xxx'>Delete Application <i class="fas fa-trash-alt"></i></button></td>
				<%
                	}else{
            	%>
            	<td><button type='button' onClick='opener(this);' id='opener' disabled class='btn btn-warning'>Edit AppName <i class="fas fa-edit"></i></button> &nbsp&nbsp <button type='button' id='sures' onClick='opener(this);' class='btn btn-danger yyy'>Reactive Application <i class="fas fa-trash-alt"></i></button></td>
            	<%
                	}
            	%>            
            </tr> 
            <%
           		 }
	         } catch (Exception e) {
	         	out.println("wrong entry" + e);
	         }
            %>           
        </tbody>
        <tfoot>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </tfoot>
    </table>
    <br>
    
    </body>
    </html>