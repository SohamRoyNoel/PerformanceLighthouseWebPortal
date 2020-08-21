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
    
 // Remove User
    $('#example tbody').on('click', '.xxx', function () {
    	var apID = _activeId;
    	
    	$.get('../UserDeleteController', {
           	 a: apID, 
            }, function(response) {
            	window.location.assign("https://performancehouse.azurewebsites.net/admin/UserRemover.jsp");
            	//$('#example').DataTable().ajax.reload(null, false);
            });
    });
} );
</script>
<script>
var _nextId = 1;
var _activeId = 0;
var _activeAP = 0;
var _row = null;

</script>
<script>

function opener(ctl){
	_row = $(ctl).parents("tr"); 
	var cols = _row.children("td");	 
	 var x = ctl.parentNode.parentNode.rowIndex;
	 var tab = document.getElementById('example');
	 _activeId = tab.rows[x].cells[0].innerHTML;
	 _activeAP = tab.rows[x].cells[1].innerHTML;
	 console.log(_activeAP);
}
</script>
<p class="home"><a href="#">Home</a> > <strong> Remove User</strong></p>
<br>

<table id="example" class="display" style="width:100%; height:700px">
        <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>User Name</th>
                <th>User Email</th>
                <th>API Key</th>
                <th>User Type</th>
                <th>Status</th>
                <th>Actions</th>
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
                	int y = resultset.getInt(11);
                	if(y==1){
                %>
                <td>Active</td>
                <%
                	} else{
                %>
                <td>Removed</td>
                <%
                	}
                %>
                <%
                	int x = resultset.getInt(11); 
                	if(x==1){
                %>
                <td> <button type='button' onClick='opener(this);' id='opener' class='btn btn-warning xxx'>Revoke User Access <i class="fas fa-user-slash"></i></button></td>
            	<%
                	}else{
            	%>
            	<td> <button type='button' disabled onClick='opener(this);' id='opener' class='btn btn-warning'>Revoke User Access <i class="fas fa-user-slash"></i></button></td>
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