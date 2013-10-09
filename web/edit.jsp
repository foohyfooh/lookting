<%-- 
    Document   : edit
    Created on : Sep 4, 2013, 8:21:46 PM
    Author     : Jonathan
--%>

<%@page import="com.foohyfooh.lt.Auth"%>
<%@page import="com.foohyfooh.lt.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Event</title>
        <script src="scripts/datetimepicker_css.js"></script>
    </head>
    <body>
        <%
            if(!Auth.verifySession(session))
                response.sendRedirect("login.jsp");
        %>
        Please enter information into each field. Description, Contact Info and Artwork are optional <br />
        <form method="POST" action="Service/edit" enctype="multipart/form-data">
            <input name="id" type="hidden" 
                   value="<%= Utils.getParameter(request, "id") %>" />
            Event Name: <input name="name" type="text" maxlength="100" required/><br />
            Category: <input name="category" type="text" required/><br />
            Start Date: <input id="start_date" name="start_date" type="text" 
                               value="<%= Utils.getParameter(request, "start_date") %>" readonly/>
            <img src="scripts/cal/cal.gif" 
                 onclick="NewCssCal('start_date', 'yyyyMMdd', 'dropdown', true, 24, true, 'future');"
                 style="cursor:pointer"/><br />
            End Date: <input id ="end_date" name="end_date" type="text" 
                             value="<%= Utils.getParameter(request, "end_date") %>" readonly/>
            <img src="scripts/cal/cal.gif" 
                 onclick="NewCssCal('end_date', 'yyyyMMdd', 'dropdown', true, 24, true, 'future');"
                 style="cursor:pointer"/><br />
            Price Info: <input name="price" type="text" maxlength="100" required/><br />
            Address: <input name="address" type ="text" required/><br />
            Description: <br /> <textarea name="description" cols="50" rows="4"></textarea><br />
            Contact Info: <input name="contact" type="text" maxlength="300"/><br />
            Artwork: <input name="artwork" type="file" accept="image/*" /><br />
            
            <input value="Save" type="submit" />
        </form>
        <%if(request.getQueryString() != null){%>
            <%--
                Work around since for values that get their info from the request
                Ensures that they can be set to required
                Consider if the required is necessary or change only properties with new values
            --%>    
            <%--
                Putting newlines for the description is working on my local machine
                but not on the server
            --%>
            <script>
                document.getElementsByName("name")[0].value = "<%= request.getParameter("name") %>";
                document.getElementsByName("category")[0].value = "<%= request.getParameter("category") %>";
                document.getElementsByName("price")[0].value = "<%= request.getParameter("price")%>";
                document.getElementsByName("address")[0].value = "<%= request.getParameter("address") %>";
                document.getElementsByName("description")[0].value = "<%= Utils.getParameter(request, "description").replace("\r\n", "\\\r\n")  %>";
                <%--document.getElementsByName("description")[0].value = "<%= Utils.getParameter(request, "description").replace("\r\n", "\\n\\")  %>";--%>
                document.getElementsByName("contact")[0].value = "<%= Utils.getParameter(request, "contact") %>";
                window.history.replaceState(null, "Edit Event", "edit.jsp");
            </script> 
        <%}%>
        
    </body>
</html>
