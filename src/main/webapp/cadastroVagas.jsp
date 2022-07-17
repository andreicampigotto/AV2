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
        <form action="Servlet?op=cadastraVagas" method="post">
            Insira a quantidade total de vagas: <input type="text" name="vagas"> <br>
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
            <input type="submit" value="Cadastrar">
        </form>
        <a href="index.html">Retornar ao início</a>
    </body>
</html>
