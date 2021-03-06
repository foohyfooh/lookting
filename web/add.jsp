<%-- 
    Document   : add
    Created on : Sep 3, 2013, 2:53:12 PM
    Author     : Jonathan
--%>

<%@page import="com.foohyfooh.lt.Auth"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Event</title>
        <script src="scripts/datetimepicker_css.js"></script>
        <script>
            window.history.replaceState(null, "Add Event", "add.jsp");  
        </script>
    </head>
    <body>
        <%
            if(!Auth.verifySession(session))
                response.sendRedirect("login.jsp");
        %>
        Please enter information into each field. Description, Contact Info and Artwork are optional <br />
        <form method="POST" action="Service/add" enctype="multipart/form-data">
            Event Name: <input name="name" type="text" maxlength="100" required/><br />
            Category: 
            <select name="category">
                <option value="Parties & Nightlife">Parties & Nightlife</option>
                <option value="Arts & Culture">Arts & Culture</option>
                <option value="Business">Business</option>
                <option value="Carnival & Fetes">Carnival & Fetes</option>
                <option value="Family & Kids">Family & Kids</option>
                <option value="Government & Political">Government & Political</option>
                <option value="Educational">Educational</option>
                <option value="Concert">Concert</option>
                <option value="Concert">Outdoors</option>
                <option value="Religious & Spiritual">Religious & Spiritual</option>
                <option value="Social & Lifestyle">Social & Lifestyle</option>
                <option value="Games & Sports">Games & Sports</option>
                <option value="Theatre">Theatre</option>
                <option value="Miscellaneous">Miscellaneous</option>
            </select><br />
            Start Date: <input id="start_date" name="start_date" type="text" required readonly/>
            <img src="scripts/cal/cal.gif" 
                 onclick="NewCssCal('start_date', 'yyyyMMdd', 'dropdown', true, 24, true, 'future');" 
                 style="cursor:pointer"/><br />
            End Date: <input id ="end_date" name="end_date" type="text" required readonly/>
            <img src="scripts/cal/cal.gif" 
                 onclick="NewCssCal('end_date', 'yyyyMMdd', 'dropdown', true, 24, true, 'future');" 
                 style="cursor:pointer"/><br />
            Price Info: <input name="price" type="text" maxlength="100" required/><br />
            Address: <input name="address" type ="text" required/><br />
            Description: <br /> <textarea name="description" cols="50" rows="4"></textarea><br />
            Contact Info: <input name="contact" type="text" maxlength="300"/><br />
            Artwork: <input name="artwork" type="file" accept="image/*"/><br />
            <input value="Add" type="submit"/>
        </form>
    </body>
</html>
