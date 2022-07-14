<%@page contentType="text/html" pageEncoding="UTF-8"
        import="java.util.*"
        import="ifc.edu.br.av2.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <%
        ArrayList<HashMap<String, Object>> clientes = (ArrayList<HashMap<String, Object>>) request.getAttribute("visualizarClientes");
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
        for (HashMap<String, Object> cliente : clientes) {
    %>
            <tr>
            <td><%=cliente.get("id")%></td>
            <td><%=cliente.get("nome")%></td>
            <td><%=cliente.get("email")%></td>
            <td><%=cliente.get("cpf")%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
