package ifc.edu.br.av2.control;

import ifc.edu.br.av2.dao.DAO;
import ifc.edu.br.av2.model.Cliente;
import ifc.edu.br.av2.model.Embarcacao;
import ifc.edu.br.av2.model.LocacaoGaragemBarco;
import ifc.edu.br.av2.model.Marina;
import ifc.edu.br.av2.model.Usuario;
import ifc.edu.br.av2.model.VendaBarco;
import ifc.edu.br.av2.model.Vendedor;
import ifc.edu.br.av2.util.Utilitarios;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        HttpSession sessao = request.getSession(true);
        String op = Utilitarios.validaString(request.getParameter("op"));
        if ("venda".equals(op)) {
            forwardVenda(request, response);
        } else if ("locacao".equals(op)) {
            forwardLocacao(request, response);
        } else if (op.length() > 10 && "visualizar".equals(op.substring(0, 10))) {
            forwardVisualizar(request, response);
        } else if (op.length() > 8 && "cadastro".equals(op.substring(0, 8))) {
            forwardCadastrar(request, response);
        } else if (sessao.getAttribute("login") != null) {
            getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String op = request.getParameter("op");
        if ("validaLogin".equals(op)) {
            validaLogin(request, response);
        } else if ("cadastraCliente".equals(op)) {
            cadastraCliente(request);
        } else if ("cadastraEmbarcacao".equals(op)) {
            cadastraEmbarcacao(request);
        } else if ("cadastraVendedor".equals(op)) {
            cadastraVendedor(request);
        } else if ("cadastraVenda".equals(op)) {
            cadastraVenda(request);
        } else if ("cadastraLocacao".equals(op)) {
            cadastraLocacao(request);
        }
        getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
    }

    private void validaLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            addLoginSessao(request, sessao);
            getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
        } else {
            sessao.setAttribute("mensagem", "Login e/ou senha incorretos, tente novamente.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    
    private void cadastraCliente(HttpServletRequest request)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        dao.insert(new Cliente(nome, cpf, email, senha));
    }
    
    private void cadastraEmbarcacao(HttpServletRequest request)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        int tamanho = Utilitarios.validaInteger(request.getParameter("tamanho"));
        String tipo = request.getParameter("tipo");
        int idProprietario = Utilitarios.validaInteger(request.getParameter("idUsuario"));
        Usuario proprietario = dao.consultarCliente(idProprietario);
        dao.insert(new Embarcacao(nome, tamanho, tipo, proprietario));
    }
    
    private void cadastraVendedor(HttpServletRequest request)
            throws ServletException, IOException {
        String matricula = request.getParameter("matricula");
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        dao.insert(new Vendedor(matricula, nome, cpf, email, senha));
    }
    
    private void cadastraVenda(HttpServletRequest request)
            throws ServletException, IOException {
        long idCliente = Utilitarios.validaLong(request.getParameter("idUsuario"));
        long idEmbarcacao = Utilitarios.validaLong(request.getParameter("idEmbarcacao"));
        long idVendedor = Utilitarios.validaLong(request.getParameter("idVendedor"));
        Embarcacao embarcacao = dao.consultarEmbarcacao(idEmbarcacao);
        Vendedor vendedor = dao.consultarVendedor(idVendedor);
        Cliente cliente = dao.consultarCliente(idCliente);
        float valor = Utilitarios.validaFloat(request.getParameter("valor"));
        dao.insert(new VendaBarco(embarcacao, vendedor, cliente, valor));
    }
    
    private void cadastraLocacao(HttpServletRequest request)
            throws ServletException, IOException {
        long idEmbarcacao = Utilitarios.validaLong(request.getParameter("idEmbarcacao"));
        long idCliente = Utilitarios.validaLong(request.getParameter("idUsuario"));
        long idMarina = Utilitarios.validaLong(request.getParameter("idMarina"));
        Embarcacao embarcacao = dao.consultarEmbarcacao(idEmbarcacao);
        Cliente cliente = dao.consultarCliente(idCliente);
        Marina marina = dao.consultarMarina(idMarina);
        float valor = Utilitarios.validaFloat(request.getParameter("valor"));
        dao.insert(new LocacaoGaragemBarco(embarcacao, cliente, valor, marina));
    }
    
    private void addLoginSessao(HttpServletRequest request, HttpSession sessao) {
        if (sessao.getAttribute("login") == null) {
            String loginCookie = retornarCookieLogin(request);
            if (loginCookie != null) {
                sessao.setAttribute("login", loginCookie);
            }
        }
    }
    
    private String retornarCookieLogin(HttpServletRequest request) {
        Cookie listaCookies[] = request.getCookies();
        if (listaCookies != null) {
            for (Cookie c : listaCookies) {
                if (c.getName().equals("login")) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
    
    private void forwardVisualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parameter = Utilitarios.validaString(request.getParameterMap().get("op")[0]);
        ArrayList<HashMap<String, Object>> attribute = new ArrayList<>();
        if (null != parameter) switch (parameter) {
            case "visualizarClientes":
                attribute = (ArrayList<HashMap<String, Object>>) dao.consultarClientes();
                break;
            case "visualizarVendedores":
                attribute = (ArrayList<HashMap<String, Object>>) dao.consultarVendedores();
                break;
            case "visualizarEmbarcacoes":
                attribute = (ArrayList<HashMap<String, Object>>) dao.consultarEmbarcacoes();
                break;
            default:
                break;
        }
        request.setAttribute(parameter, attribute);
        getServletContext().getRequestDispatcher("/"+parameter+".jsp").forward(request, response);
    }
    
    private void forwardCadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parameter = Utilitarios.validaString(request.getParameterMap().get("op")[0]);
        if ("cadastroEmbarcacao".equals(parameter)) {
            request.setAttribute("clientes", dao.consultarClientes());
        }
        getServletContext().getRequestDispatcher("/"+parameter+".jsp").forward(request, response);
    }
    
    private void forwardVenda(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("embarcacoes", dao.consultarEmbarcacoes());
        request.setAttribute("clientes", dao.consultarClientes());
        request.setAttribute("vendedores", dao.consultarVendedores());
        getServletContext().getRequestDispatcher("/venda.jsp").forward(request, response);
    }
    
    private void forwardLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("embarcacoes", dao.consultarEmbarcacoes());
        getServletContext().getRequestDispatcher("/locacao.jsp").forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
