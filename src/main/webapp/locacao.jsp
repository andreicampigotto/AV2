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
        <form action="Servlet?op=cadastraLocacao" method="post">
            Quantidade: <input type="text" name="quantidade">
            Embarcação: 
        <select name="idEmbarcacao">
    <%
        ArrayList<HashMap<String, Object>> embarcacoes = (ArrayList<HashMap<String, Object>>) request.getAttribute("embarcacoes");
        for (HashMap<String, Object> embarcacao : embarcacoes) {
    %>
            <option value="<%=embarcacao.get("id")%>"><%=embarcacao.get("nome")%></option>
    <%
        }
    %>
        </select> <br>
        Cliente:
        <select name="idUsuario">
    <%
        ArrayList<HashMap<String, Object>> clientes = (ArrayList<HashMap<String, Object>>) request.getAttribute("clientes");
        for (HashMap<String, Object> cliente : clientes) {
    %>
            <option value="<%=cliente.get("id")%>"><%=cliente.get("nome")%></option>
    <%
        }
    %>
        </select> <br>
        Marina:
        <select name="idMarina">
    <%
        ArrayList<HashMap<String, Object>> marinas = (ArrayList<HashMap<String, Object>>) request.getAttribute("marinas");
        for (HashMap<String, Object> marina : marinas) {
    %>
            <option value="<%=marina.get("id")%>"><%=marina.get("id")%></option>
    <%
        }
    %>
        </select> <br>
        Valor: <input type="text" name="valor"> <br>
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
