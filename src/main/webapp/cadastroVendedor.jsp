<%-- 
    Document   : cadastroVendedor
    Created on : Jul 13, 2022, 12:44:31 PM
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
        <form action="Servlet?op=cadastraVendedor" method="post">
            Matricula <input type="text" name="matricula"> <br>
            Nome: <input type="text" name="nome"> <br>
            Email: <input type="text" name="email"> <br>
            CPF: <input type="text" name="cpf"> <br>
            Senha: <input type="password" name="senha"> <br>
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
