<%-- 
    Document   : cadastroEmbarcacao
    Created on : Jul 13, 2022, 1:07:25 PM
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
        <form action="Servlet" method="post">
            Tamanho: <input type="text" name="tamanho">
            Tipo: <input type="text" name="tipo"> <br>
            Proprietário: 
        <select name="carros">
    <%
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");
        for (Cliente cliente : clientes) {
    %>
            <option value="<%=cliente.getId()%>"><%=cliente.getNome()%></option>
    <%
        }
    %>
        </select> <br>
            
            
            <input type="hidden" name="parent" value="embarcacao">
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
