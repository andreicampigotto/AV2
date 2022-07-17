<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        
        <form method="post" action="Servlet?op=validaLogin">
            Login: <input type="text" name="login"> <br>
            Senha: <input type="password" name="senha"> <br>
            <input type="submit" value="Login" />
        </form>
        <form action="Servlet" method="get">
            <br> <button type="submit" name="op" value=cadastroVendedor>Cadastrar novo vendedor</button>
        </form>
        <jsp:include page="subPaginaMensagem.jsp"/>
    </body>
</html>
