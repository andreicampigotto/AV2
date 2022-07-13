package ifc.edu.br.av2.control;

import ifc.edu.br.av2.dao.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "", urlPatterns = {"/"})
public class Servlet extends HttpServlet {
    DAO dao = new DAO(true);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        if (request.getParameter("visualizar") != null) {
            request.setAttribute("clientes", dao.consultarClientes());
            getServletContext().getRequestDispatcher("/clientes.jsp").forward(request, response);
        } else if (request.getParameter("cadastroCliente") != null) {
            request.setAttribute("usuario", dao.consultarUsuarios());
            getServletContext().getRequestDispatcher("/cadastroClientes.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        HttpSession sessao = request.getSession(true);
        
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        boolean oklogin;
        List<Object> params = new ArrayList<>();
        params.add(login);
        params.add(senha);
        List<?> res = dao.executePreparedQuery("usuario", null, "nome = ? and senha = SHA(?)", params);
        oklogin = !res.isEmpty();
        if (oklogin) {
            sessao.setAttribute("login", login);
            sessao.setAttribute("mensagem", "Login efetuado com sucesso");
            Cookie ckLogin = new Cookie("loginCookie", login);
            ckLogin.setMaxAge(24 * 60 * 60);
            response.addCookie(ckLogin);
            response.sendRedirect(request.getContextPath() + "/Servlet");
        } else {
            sessao.setAttribute("mensagem", "Login e/ou senha incorretos, tente novamente.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
