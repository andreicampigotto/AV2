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
    </body>
</html>
