<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String login = (String) session.getAttribute("login");
        %>
        <form action="Servlet" method="get">
        Você está logado com o usuário: <%=login%>
        <button type="submit" name="op" value=logoff>logoff</button>
        <hr>
        <br> <button type="submit" name="op" value=visualizarClientes>Visualizar todos os clientes</button>
        <br> <button type="submit" name="op" value=visualizarVendedores>Visualizar todos os vendedores</button>
        <br> <button type="submit" name="op" value=visualizarEmbarcacoes>Visualizar todas as Embarcações</button>
        <br> <button type="submit" name="op" value=visualizarMarinas>Visualizar todas as Marinas</button>
        
        <br> <button type="submit" name="op" value=cadastroCliente>Cadastrar novo cliente</button>
        <br> <button type="submit" name="op" value=cadastroVendedor>Cadastrar novo vendedor</button>
        <br> <button type="submit" name="op" value=cadastroEmbarcacao>Cadastrar nova embarcação</button>
        <br> <button type="submit" name="op" value=cadastroVagas>Cadastrar novas vagas</button>
        <br> <button type="submit" name="op" value=cadastroMarina>Cadastrar nova marina</button>
        
        <br> <button type="submit" name="op" value=cadastroLocacao>Cadastrar nova locação</button>
        <br> <button type="submit" name="op" value=cadastroVenda>Cadastrar nova venda</button>
        
        </form>
        <jsp:include page="subPaginaMensagem.jsp"/>
    </body>
</html>
