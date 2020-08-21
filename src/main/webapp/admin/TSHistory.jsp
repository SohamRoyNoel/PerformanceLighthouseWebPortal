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
} );
</script>
<p class="home"><a href="#">Home</a> > <strong> Test Case Details</strong></p>
<br>

<table id="example" class="display" style="width:100%; height:700px">
        <thead>
            <tr>
            	<th>History ID</th>
                <th>TestCase Id</th>
                <th>TestCase Name</th>
                <th>Application Name</th>
                <th>User Name</th>
                <th>TestCase Status</th>
                <th>Change Time</th>
            </tr>
        </thead>
        <tbody>
        <%
	         try {
	        	
	         	Connection connection = Connections.getConnection();
	         	Statement statement = connection.createStatement();
	         	resultset = statement.executeQuery(Queries.TcHistoryChange);
	         	while (resultset.next()) {
	    %>
            <tr>
                <td><%=resultset.getInt(1) %></td>
                <td><%=resultset.getInt(2) %></td>
                <td><%=resultset.getString(3) %></td>
                <td><%=resultset.getString(4) %></td>
                <td><%=resultset.getString(5) %></td>
                <td><%=resultset.getString(6) %></td>
                <td><%=resultset.getDate(7) %></td>
                           
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
            </tr>
        </tfoot>
    </table>