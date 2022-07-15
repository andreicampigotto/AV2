<%-- 
    Document   : cadastroVagas
    Created on : Jul 15, 2022, 12:17:41 PM
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
     <body>
        <form action="Servlet?op=cadastroVagas" method="post">
            Insira a quantidade total de vagas: <input type="text" name="totalVagas"> <br>
            
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
