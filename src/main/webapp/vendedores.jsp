<%-- 
    Document   : vendedores
    Created on : Jul 13, 2022, 1:20:23 PM
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
    <%
        ArrayList<Vendedor> vendedores = (ArrayList<Vendedor>) request.getAttribute("vendedores");
    %>
        <table>
            <thead>
                <tr>
                    <th colspan="1">matricula</th>
                    <th colspan="1">nome</th>
                    <th colspan="1">email</th>
                    <th colspan="1">cpf</th>
                </tr>
            </thead>
            <tbody>
    <%
        for (Vendedor vendedor : vendedores) {
    %>
            <tr>
            <td><%=vendedor.getId()%></td>
            <td><%=vendedor.getNome()%></td>
            <td><%=vendedor.getEmail()%></td>
            <td><%=vendedor.getCpf()%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
