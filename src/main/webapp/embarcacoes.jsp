<%-- 
    Document   : embarcacoes
    Created on : Jul 13, 2022, 1:20:33 PM
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
        ArrayList<Embarcacao> embarcacoes = (ArrayList<Embarcacao>) request.getAttribute("embarcacoes");
    %>
        <table>
            <thead>
                <tr>
                    <th colspan="1">id</th>
                    <th colspan="1">tamanho</th>
                    <th colspan="1">tipo</th>
                    <th colspan="1">proprietario</th>
                </tr>
            </thead>
            <tbody>
    <%
        for (Embarcacao embarcacao : embarcacoes) {
    %>
            <tr>
            <td><%=embarcacao.getId()%></td>
            <td><%=embarcacao.getTipo()%></td>
            <td><%=embarcacao.getTamanho()%></td>
            <td><%=embarcacao.getProprietario().getNome()%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
