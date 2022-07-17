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
            ArrayList<HashMap<String, Object>> marinas = (ArrayList<HashMap<String, Object>>) request.getAttribute("visualizarMarinas");
        %>
            <table>
                <thead>
                    <tr>
                        <th colspan="1">id</th>
                        <th colspan="1">vagas</th>
                    </tr>
                </thead>
                <tbody>
        <%
            for (HashMap<String, Object> marina : marinas) {
        %>
                <tr>
                <td><%=marina.get("id")%></td>
                <td><%=marina.get("totalVagas")%></td>
                </tr>
        <%
            }
        %>       
                </tbody>
            </table>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
