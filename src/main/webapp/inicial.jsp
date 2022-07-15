<%-- 
    Document   : inicial
    Created on : 12 Jul 2022, 22:41:58
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="Servlet" method="get">
        <br> <button type="submit" name="op" value=visualizarClientes>Visualizar todos os clientes</button>
        <br> <button type="submit" name="op" value=visualizarVendedores>Visualizar todos os vendedores</button>
        <br> <button type="submit" name="op" value=visualizarEmbarcacoes>Visualizar todas as Embarcações</button>
        
        <br> <button type="submit" name="op" value=cadastroCliente>Cadastrar novo cliente</button>
        <br> <button type="submit" name="op" value=cadastroVendedor>Cadastrar novo vendedor</button>
        <br> <button type="submit" name="op" value=cadastroEmbarcacao>Cadastrar nova embarcação</button>
        <br> <button type="submit" name="op" value=cadastroVagas>Cadastrar vagas</button>
        
        <br> <button type="submit" name="op" value=locacao>Marina</button>
        <br> <button type="submit" name="op" value=venda>Venda</button>
        
        </form>
    </body>
</html>
