/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ifc.edu.br.av2.control;

import ifc.edu.br.av2.dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "", urlPatterns = {"/"})
public class Servlet extends HttpServlet {
    DAO dao = new DAO();
    
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
        } 
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
