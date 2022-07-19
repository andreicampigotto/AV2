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
        ArrayList<HashMap<String, Object>> vendas = (ArrayList<HashMap<String, Object>>) request.getAttribute("visualizarVendas");
    %>
        <table>
            <thead>
                <tr>
                    <th colspan="1">id</th>
                    <th colspan="1">embarcacao</th>
                    <th colspan="1">matricula</th>
                    <th colspan="1">vendedor</th>
                    <th colspan="1">cliente</th>
                    <th colspan="1">valor</th>
                </tr>
            </thead>
            <tbody>
    <%
        DAO dao = new DAO();
        for (HashMap<String, Object> venda : vendas) {
            Embarcacao e = dao.consultarEmbarcacao(Utilitarios.validaLong(venda.get("idEmbarcacao")));
            Vendedor v = dao.consultarVendedor(Utilitarios.validaLong(venda.get("idVendedor")));
            Cliente c = dao.consultarCliente(Utilitarios.validaLong(venda.get("idCliente")));
    %>
            <tr>
            <td><%=venda.get("id")%></td>
            <td><%=e.getNome()%></td>
            <td><%=v.getMatricula()%></td>
            <td><%=v.getNome()%></td>
            <td><%=c.getNome()%></td>
            <td><%=venda.get("valor")%></td>
            </tr>
    <%
        }
    %>       
            </tbody>
        </table>
    <a href="index.html">Retornar ao início</a>
    </body>
</html>
