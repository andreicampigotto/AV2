<%-- 
    Document   : cadastro
    Created on : 30 Jun 2022, 00:37:22
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="Servlet?op=cadastraCliente" method="post">
            Nome: <input type="text" name="nome"> <br>
            Email: <input type="text" name="email"> <br>
            CPF: <input type="text" name="cpf"> <br>
            Senha: <input type="text" name="senha"> <br>
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
