<%@page contentType="text/html" pageEncoding="UTF-8"
        import="java.util.*"
        import="ifc.edu.br.av2.dao.*"
        import="ifc.edu.br.av2.util.*"
        import="ifc.edu.br.av2.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <%
        ArrayList<HashMap<String, Object>> alugueis = (ArrayList<HashMap<String, Object>>) request.getAttribute("visualizarAlugueis");
    %>
        <table>
            <thead>
                <tr>
                    <th colspan="1">id</th>
                    <th colspan="1">embarcacao</th>
                    <th colspan="1">cliente</th>
                    <th colspan="1">marina</th>
                    <th colspan="1">valor</th>
                </tr>
            </thead>
            <tbody>
    <%
        DAO dao = new DAO();
        for (HashMap<String, Object> aluguel : alugueis) {
            Embarcacao e = dao.consultarEmbarcacao(Utilitarios.validaLong(aluguel.get("idEmbarcacao")));
            Cliente c = dao.consultarCliente(Utilitarios.validaLong(aluguel.get("idCliente")));
            Marina m = dao.consultarMarina(Utilitarios.validaLong(aluguel.get("idMarina")));
    %>
            <tr>
            <td><%=aluguel.get("id")%></td>
            <td><%=e.getNome()%></td>
            <td><%=c.getNome()%></td>
            <td><%=m.getId()%></td>
            <td><%=aluguel.get("valor")%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
