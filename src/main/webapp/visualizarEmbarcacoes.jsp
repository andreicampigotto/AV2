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
        ArrayList<HashMap<String, Object>> embarcacoes = (ArrayList<HashMap<String, Object>>) request.getAttribute("visualizarEmbarcacoes");
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
        for (HashMap<String, Object> embarcacao : embarcacoes) {
    %>
            <tr>
            <td><%=embarcacao.get("id")%></td>
            <td><%=embarcacao.get("tipo")%></td>
            <td><%=embarcacao.get("tamanho")%></td>
            <td><%=embarcacao.get("idUsuario")%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
