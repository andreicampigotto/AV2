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
        <form action="Geral" method="get">
        <br> <button type="submit" name="visualizarClientes">Visualizar todas os clientes</button>
        <br> <button type="submit" name="visualizarVendedores">Visualizar todas os vendedores</button>
        <br> <button type="submit" name="visualizarEmbarcacoes">Visualizar todas os Embarcações</button>
        
        <br> <button type="submit" name="cadastroCliente">Cadastrar novo cliente</button>
        <br> <button type="submit" name="cadastroVendedor">Cadastrar novo vendedor</button>
        <br> <button type="submit" name="cadastroEmbarcacao">Cadastrar nova Embarcação</button>
        
        </form>
    </body>
</html>
