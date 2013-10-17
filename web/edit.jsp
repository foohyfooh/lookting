<%-- 
    Document   : edit
    Created on : Sep 4, 2013, 8:21:46 PM
    Author     : Jonathan
--%>

<%@page import="com.foohyfooh.lt.Auth"%>
<%@page import="com.foohyfooh.lt.Event"%>
<%@page import="com.foohyfooh.lt.Utils"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
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
            <input name="id" hidden/>
            Event Name: <input name="name" type="text" maxlength="100" required/><br />
            Category: <select name="category">
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
            Start Date: <input id="start_date" name="start_date" type="text" readonly/>
            <img src="scripts/cal/cal.gif" 
                 onclick="NewCssCal('start_date', 'yyyyMMdd', 'dropdown', true, 24, true, 'future');"
                 style="cursor:pointer"/><br />
            End Date: <input id ="end_date" name="end_date" type="text" readonly/>
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
        <%
            EntityManager entityManager = Utils.createEntityManager();
            Query query = entityManager.createNamedQuery("Event.findById");
            Event event = (Event) query.setParameter("id", Integer.parseInt(request.getParameter("id"))).getSingleResult();
            if(event == null){
                response.sendRedirect("index.jsp");
            }
        %>
            <%--
                Work around since for values that get their info from the request
                Ensures that they can be set to required
                Consider if the required is necessary or change only properties with new values
                Or if the info should be gotten from the database and put here so the request will expose less info
            --%>
        <script>
            document.getElementsByName("id")[0].value = "<%= event.getId() %>";
            document.getElementsByName("name")[0].value = "<%= StringEscapeUtils.escapeEcmaScript(event.getName()) %>";
            document.getElementsByName("category")[0].value = "<%= StringEscapeUtils.escapeEcmaScript(event.getCategory()) %>";
            document.getElementById("start_date").value = "<%= event.getStartDate() %>";
            document.getElementById("end_date").value = "<%= event.getEndDate() %>";
            document.getElementsByName("price")[0].value = "<%= StringEscapeUtils.escapeEcmaScript(event.getPrice()) %>";
            document.getElementsByName("address")[0].value = "<%= StringEscapeUtils.escapeEcmaScript(event.getAddress()) %>";
            document.getElementsByName("description")[0].value = "<%= StringEscapeUtils.escapeEcmaScript(event.getDescription())  %>";
            document.getElementsByName("contact")[0].value = "<%= StringEscapeUtils.escapeEcmaScript(event.getContact()) %>";
            window.history.replaceState(null, "Edit Event", "edit.jsp");
        </script> 
        <%}%>
    </body>
</html>
