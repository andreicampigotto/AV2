<%@page contentType="text/html" pageEncoding="UTF-8"
        import="ifc.edu.br.av2.dao.*"
        import="ifc.edu.br.av2.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String login = (String) session.getAttribute("login");
            boolean isVendedor = new DAO().isVendedor(Utilitarios.validaLong(session.getAttribute("idLogin")));
            String nomePerfil = isVendedor ? "Vendedor" : "Cliente";
        %>
        <form action="Servlet" method="get">
        Você está logado com o usuário: <%=login%> 
        <button type="submit" name="op" value=logoff>logoff</button>
        <br> Perfil: <%=nomePerfil%>
        <hr>
        <br> <button type="submit" name="op" value=visualizarClientes>Visualizar todos os clientes</button>
        <br> <button type="submit" name="op" value=visualizarVendedores>Visualizar todos os vendedores</button>
        <br> <button type="submit" name="op" value=visualizarEmbarcacoes>Visualizar todas as Embarcações</button>
        <br> <button type="submit" name="op" value=visualizarMarinas>Visualizar todas as Marinas</button>
        <br> <button type="submit" name="op" value=visualizarVendas>Visualizar todas as Vendas</button>
        <br> <button type="submit" name="op" value=visualizarAlugueis>Visualizar todas os Aluguéis</button>
        
        <%
            if (isVendedor) {
        %>
            <br> <button type="submit" name="op" value=cadastroCliente>Cadastrar novo cliente</button>
            <br> <button type="submit" name="op" value=cadastroVendedor>Cadastrar novo vendedor</button>
            <br> <button type="submit" name="op" value=cadastroVagas>Cadastrar novas vagas</button>
            <br> <button type="submit" name="op" value=cadastroMarina>Cadastrar nova marina</button>
            <br> <button type="submit" name="op" value=cadastroLocacao>Cadastrar nova locação</button>
            <br> <button type="submit" name="op" value=cadastroVenda>Cadastrar nova venda</button>
        <%
            }
        %>
                    
        <br> <button type="submit" name="op" value=cadastroEmbarcacao>Cadastrar nova embarcação</button>
        
        </form>
        <jsp:include page="subPaginaMensagem.jsp"/>
    </body>
</html>
