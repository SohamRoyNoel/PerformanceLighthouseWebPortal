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
    	
    	$.get('../UserMapDeleteController', {
           	 a: apID, 
            }, function(response) {
            	window.location.assign("https://performancehouse.azurewebsites.net/admin/AccessDetails.jsp");
            	//$('#example').DataTable().ajax.reload(null, false);
            });
    });
} );
</script>
<p class="home"><a href="#">Home</a> > <strong>User Application Access Details</strong></p>
<br>
<script>
var _nextId = 1;
var _activeId = 0;
var _activeTCname = 0;
var _activeTCOwner = 0;
var _activeUSEROwnerName = 0;
var _row = null;

</script>
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
<table id="example" class="display" style="width:100%; height:700px">
        <thead>
            <tr>
            	<th>Map Id</th>
                <th>Application Name</th>
                <th>User Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <%
	         try {
	        	
	         	Connection connection = Connections.getConnection();
	         	Statement statement = connection.createStatement();
	         	resultset = statement.executeQuery(Queries.userMapping);
	         	while (resultset.next()) {
	    %>
            <tr>
                <td><%=resultset.getInt(1) %></td>
                <td> <%=resultset.getString(2) %></td>
                <td><%=resultset.getString(3) %></td>
                <%
                	int x = resultset.getInt(4); 
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
            </tr>
        </tfoot>
    </table>
    </body>
    </html>
    <!--  -->