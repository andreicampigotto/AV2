<%-- 
    Document   : venda
    Created on : Jul 14, 2022, 2:31:36 PM
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
        <form action="Servlet?op=venda" method="post">
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
        
        Vendedor:
        <select name="idVendedor">
    <%
        ArrayList<HashMap<String, Object>> vendedores = (ArrayList<HashMap<String, Object>>) request.getAttribute("vendedores");
        for (HashMap<String, Object> vendedor : vendedores) {
    %>
            <option value="<%=vendedor.get("matricula")%>"><%=vendedor.get("nome")%></option>
    <%
        }
    %>
        </select> <br>
        
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
