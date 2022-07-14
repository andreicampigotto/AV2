<%-- 
    Document   : locacao
    Created on : Jul 14, 2022, 2:06:21 PM
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
        <form action="Servlet?op=locacao" method="post">
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
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
