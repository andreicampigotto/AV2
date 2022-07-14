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
        ArrayList<HashMap<String, Object>> vendedores = (ArrayList<HashMap<String, Object>>) request.getAttribute("visualizarVendedores");
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
        for (HashMap<String, Object> vendedor : vendedores) {
    %>
            <tr>
            <td><%=vendedor.get("id")%></td>
            <td><%=vendedor.get("nome")%></td>
            <td><%=vendedor.get("email")%></td>
            <td><%=vendedor.get("cpf")%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
