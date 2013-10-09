<%-- 
    Document   : index
    Created on : Sep 2, 2013, 4:35:31 PM
    Author     : Jonathan
--%>

<%@page import="com.foohyfooh.lt.Auth"%>
<%@page import="com.foohyfooh.lt.Database"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look Ting</title>
        <link rel="stylesheet" href="styles/stylesheet.css"/>
    </head>
    <body onload="init();">
        <%
            if(!Auth.verifySession(session))
                response.sendRedirect("login.jsp");
            else{
                out.print(String.format("<h1>Welcome %s</h1>", session.getAttribute("username")));
                Database database = new Database();
                database.printDev(out, request);
            }
        %>
        <form>
            <input type="button" value ="Add" onclick="window.location = 'add.jsp';">
            <input type="button" value ="Logout" onclick="window.location = 'Service/logout';" />
        </form>
        <script>
            function init(){
                var changers = document.getElementsByClassName("changeBtn");
                var deletes = document.getElementsByClassName("hidden");
                var change = function (i){
                    return function(){
                        if( (i % 2) === 0){
                            changers[i].style.display = "none";
                            changers[i+1].style.display = "inline";
                            //Make the associated cancel option visible
                            deletes[Math.floor( (i + 1) / 2)].style.display = "inline";
                        }else{
                            changers[i-1].style.display = "inline";
                            changers[i].style.display = "none";
                            //Make the associated cancel option hidden
                            deletes[Math.floor(i / 2)].style.display = "none";
                        }
                    };
                };

                for(var i = 0; i < changers.length; i++){
                    if( (i % 2) === 0){
                        changers[i].style.display = "inline";
                    }else{
                        changers[i].style.display = "none";
                        //Make  the associated cancel option hidden
                        deletes[Math.floor(i / 2)].style.display = "none";
                    }
                    changers[i].onclick = change(i);
                }
            }
        </script>
    </body>
</html>
