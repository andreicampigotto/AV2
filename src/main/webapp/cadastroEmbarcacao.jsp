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
        <form action="Servlet?op=cadastraEmbarcacao" method="post">
            Tamanho: <input type="text" name="tamanho">
            Tipo: <input type="text" name="tipo"> <br>
            Proprietário: 
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
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
