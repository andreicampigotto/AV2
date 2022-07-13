<%-- 
    Document   : lista
    Created on : 30 Jun 2022, 00:37:09
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
    <%
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");
    %>
        <table>
            <thead>
                <tr>
                    <th colspan="1">id</th>
                    <th colspan="1">nome</th>
                    <th colspan="1">email</th>
                    <th colspan="1">cpf</th>
                </tr>
            </thead>
            <tbody>
    <%
        for (Cliente cliente : clientes) {
    %>
            <tr>
            <td><%=cliente.getId()%></td>
            <td><%=cliente.getNome()%></td>
            <td><%=cliente.getEmail()%></td>
            <td><%=cliente.getCpf()%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
