<%@page contentType="text/html" pageEncoding="UTF-8"
        import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String mensagem = (String) session.getAttribute("mensagem");
            if (mensagem != null) {
        %>
        <hr><%=mensagem%> 
        <%
                session.removeAttribute("mensagem");
            }
        %>
    </body>
</html>
