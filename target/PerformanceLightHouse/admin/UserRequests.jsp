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
    $('#example').DataTable();
    
 // Accept Request
	$('#example tbody').on('click', '.xxp', function () {
		var apID = _activeId;
		var apName = _activeAPname;
		var apUserName = _activeUSEROwner;
		var apEmail = _activeUSEROwnerName;
		
		$.get('../AdminAcceptController', {
	       	 a: apID, b:apName, c:apUserName, d:apEmail,
	        }, function(response) {
	        	alert("Access is Granted");
	        	window.location.assign("https://performancehouse.azurewebsites.net/admin/UserRequests.jsp");
	        	//$('#example').DataTable().ajax.reload(null, false);
	        });
	});
 
	// Reject Request
	$('#example tbody').on('click', '.pps', function () {
		var apID = _activeId;
		var apName = _activeAPname;
		var apUserName = _activeUSEROwner;
		var apEmail = _activeUSEROwnerName;
		
		$.get('../AdminRejectController', {
			a: apID, b:apName, c:apUserName, d:apEmail,
	        }, function(response) {
	        	alert("You have Rejected the request"); 
	        	window.location.assign("https://performancehouse.azurewebsites.net/admin/UserRequests.jsp");
	        	//$('.p').DataTable().ajax.reload(null, false);
	        	// var url = 'UserRequests.jsp';
	        	 //$('#example').load(url + ' #example');
	        });
	});	
} );
</script>
<script>
var _nextId = 1;
var _activeId = 0;
var _activeTCname = 0;
var _activeTCOwner = 0;
var _activeUSEROwnerName = 0;
var _row = null;

</script>
<p class="home"><a href="#">Home</a> > <strong> Application Requests</strong></p>
<br>
<script>

function opener(ctl){
	
	_row = $(ctl).parents("tr"); 

	var cols = _row.children("td");	 

	 var x = ctl.parentNode.parentNode.rowIndex;
	 var tab = document.getElementById('example');
	 _activeId = tab.rows[x].cells[0].innerHTML;
	 _activeAPname = tab.rows[x].cells[1].innerHTML;
	 _activeUSEROwner = tab.rows[x].cells[2].innerHTML;
	 _activeUSEROwnerName = tab.rows[x].cells[3].innerHTML;
		
}
</script>

<table id="example" class="display p" style="width:100%; height:700px">
        <thead>
            <tr>
                 <th>ID</th>
                <th>Application Name</th>
                <th>Asked By</th>
                <th>Asked By Email</th>
                <th>Approved By</th>
                <th>Status</th>
                <th>Grant Access Or Revoke Access Access</th>
            </tr>
        </thead>
        <tbody>
        <%
	         try {
	        	
	         	Connection connection = Connections.getConnection();
	         	Statement statement = connection.createStatement();
	         	resultset = statement.executeQuery(Queries.getrequestForAdmin);
	         	while (resultset.next()) {
	    %>
            <tr>
                <td><%=resultset.getInt(1) %></td>
                <td><%=resultset.getString(2) %></td>
                <td><%=resultset.getString(3) %></td>
                <td><%=resultset.getString(4) %></td>
                <td><%=resultset.getString(5) %></td>
                <td><%=resultset.getString(6) %></td>
                <%
                	String x = resultset.getString(6); 
                	if(x.equals("Pending")){
                %>
                <td><button type='button' onClick='opener(this);' id='accept' class='btn btn-warning xxp'>Grant Access <i class="far fa-check-circle"></i></button> &nbsp&nbsp <button type='button' onClick='opener(this);' id='reject' class='pps btn btn-danger'>Revoke Access <i class="far fa-times-circle"></i></button></td>
            	<%
                	}else{
            	%>
            	<td><button type='button' disabled onClick='opener(this);' class='btn btn-warning'>Grant Access <i class="far fa-check-circle"></i></button> &nbsp&nbsp <button disabled type='button' onClick='opener(this);' id='reject' class='btn btn-danger'>Revoke Access <i class="far fa-times-circle"></i></button></td>
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
            <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
        </tfoot>
    </table>