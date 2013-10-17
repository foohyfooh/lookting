<%-- 
    Document   : login
    Created on : Sep 13, 2013, 5:50:41 PM
    Author     : Jonathan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look Ting</title>
        <link rel="stylesheet" href="styles/stylesheet.css"/>
    </head>
    <body onload="init();">
        <form action="Service/login" method="GET" id="login">
            Username: <input name="username" type="text" autofocus/>
            Password: <input name="password" type="password"/>
            <input value="Login" type="submit"/>
        </form>
        <form action="Service/register" method="POST" id="register">
            Username: <input name="username" type="text" autocomplete="off"/>
            Password: <input name="password" type="password"/>
            <input value="Register" type="submit"/>
        </form>
        <input class="changeBtn" type="button" value="I don't have credentials"/>
        <script>
            function init(){
                login = document.getElementById("login");
                register = document.getElementById("register");
                login.style.display = "inline";
                register.style.display = "none";
                changeBtn = document.getElementsByClassName("changeBtn")[0];
                changeBtn.style.display = "inline";
                changeBtn.onclick = function change(){
                    if(register.style.display === "none"){//hidden
                        login.style.display = "none";
                        register.style.display = "inline";
                        changeBtn.value = "I have credentials";
                    }else{//not hidden
                        login.style.display = "inline";
                        register.style.display  = "none";
                        changeBtn.value = "I don't have credentials";
                    }
                };
            }
        </script>
    </body>
</html>
