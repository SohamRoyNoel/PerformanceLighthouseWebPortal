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
    
 // Make Admin
    $('#example tbody').on('click', '.xxps', function () {
    	var apID = _activeId;
    	
    	$.get('../AdminAddAdminController', {
           	 a: apID, 
            }, function(response) {
            	alert("New Admin is Created");
            	//$('#example').DataTable().ajax.reload(null, false);
            });
    });
 
 // Remove Admin
    $('#example tbody').on('click', '.xp', function () {
    	var apID = _activeId;
    	
    	
    	$.get('../AdminRemovesAdminController', {
           	 a: apID, 
            }, function(response) {
            	alert("Admin is Removed");
            	//$('#example').DataTable().ajax.reload(null, false);
            });
    });
    
} );


</script>
<script>
var _nextId = 1;
var _activeId = 0;
var _row = null;

</script>
<script>

function opener(ctl){
	_row = $(ctl).parents("tr"); 
	var cols = _row.children("td");	 
	 var x = ctl.parentNode.parentNode.rowIndex;
	 var tab = document.getElementById('example');
	 _activeId = tab.rows[x].cells[0].innerHTML;		
}
</script>
<p class="home"><a href="#">Home</a> > <strong> Create New Admin</strong></p>
<br>

<table id="example" class="display p" style="width:100%; height:700px">
        <thead>
            <tr>
            	<th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>User Name</th>
                <th>User Email</th>
                <th>API Key</th>
                <th>User Role</th>
                <th>Operations</th>
            </tr>
        </thead>
        <tbody>
        <%
	         try {
	        	
	         	Connection connection = Connections.getConnection();
	         	Statement statement = connection.createStatement();
	         	resultset = statement.executeQuery(Queries.listAllUsersForAdmin);
	         	while (resultset.next()) {
	    %>
            <tr>
                <td><%=resultset.getInt(1) %></td>
                <td><%=resultset.getString(2) %></td>
                <td><%=resultset.getString(3) %></td>
                <td><%=resultset.getString(4) %></td>
                <td><%=resultset.getString(5) %></td>
                <td><%=resultset.getString(7) %></td>
                <td><%=resultset.getString(10) %></td>
                <%
                	String sts = resultset.getString(10);
                	if(sts.equals("Admin")){
                %>
                <td><button type='button' onClick='opener(this);' disabled class='btn btn-warning'>Make Admin <i class="fas fa-user-plus"></i></button> &nbsp&nbsp <button type='button' id='sure' onClick='opener(this);' class='btn btn-danger xp'>Remove Admin<i class="fas fa-user-times"></i></button></td>
            	<%
                	}else{
            	%>
            	<td><button type='button' onClick='opener(this);' id='maker' class='btn btn-warning xxps'>Make Admin <i class="fas fa-user-plus"></i></button> &nbsp&nbsp <button type='button' disabled class='btn btn-danger'>Remove Admin<i class="fas fa-user-times"></i></button></td>
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
                <th></th>
                <th></th>
            </tr>
        </tfoot>
    </table>
    </body>
    </html>