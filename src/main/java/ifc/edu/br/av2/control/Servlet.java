package ifc.edu.br.av2.control;

import ifc.edu.br.av2.consts.TableName;
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
import java.util.Map;

@WebServlet(name = "", urlPatterns = {"/"})
public class Servlet extends HttpServlet {
    DAO dao = new DAO(true);
    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(true);
        Cookie ckLogin = retornarCookieLogin(request);
        String op = Utilitarios.validaString(request.getParameter("op"));
        if (ckLogin != null) {
            if (sessao.getAttribute("idLogin") == null) {
                sessao.setAttribute("idLogin", ckLogin.getValue());
                sessao.setAttribute("login", dao.consultarUsuario(ckLogin.getValue()).getNome());
                sessao.setAttribute("mensagem", "Bem vindo de volta!");
            }
            if ("logoff".equals(op)) {
                sessao.setAttribute("login", null);
                ckLogin.setMaxAge(0);
                response.addCookie(ckLogin);
            }
        }
        armanezarAcessosPaginas(request, response, op);
        if (op.length() > 10 && "visualizar".equals(op.substring(0, 10))) {
            forwardVisualizar(request, response);
        } else if (op.length() > 8 && "cadastro".equals(op.substring(0, 8))) {
            forwardCadastrar(request, response, sessao);
        } else if (sessao.getAttribute("login") != null) {
            getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(true);
        String op = request.getParameter("op");
        if ("validaLogin".equals(op)) {
            validaLogin(request, response);
        } else if ("cadastraCliente".equals(op)) {
            if (cadastraCliente(request)) {
                sessao.setAttribute("mensagem", "Cliente cadastrado com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar o cliente.");
            }
        } else if ("cadastraEmbarcacao".equals(op)) {
            if (cadastraEmbarcacao(request)) {
                sessao.setAttribute("mensagem", "Embarca????o cadastrada com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar a embarca????o.");
            }
        } else if ("cadastraVendedor".equals(op)) {
            if (cadastraVendedor(request)) {
                sessao.setAttribute("mensagem", "Vendedor cadastrado com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar o vendedor.");
            }
        } else if ("cadastraVenda".equals(op)) {
            if (cadastraVenda(request)) {
                sessao.setAttribute("mensagem", "Venda cadastrada com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar a venda.");
            }
        } else if ("cadastraLocacao".equals(op)) {
            if (cadastraLocacao(request)) {
                sessao.setAttribute("mensagem", "Loca????o cadastrada com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar a loca????o.");
            }
        } else if ("cadastraMarina".equals(op)) {
            if (cadastraMarina(request)) {
                sessao.setAttribute("mensagem", "Marina cadastrada com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar a marina.");
            }
        } else if ("cadastraVagas".equals(op)) {
            if (cadastraVagas(request)) {
                sessao.setAttribute("mensagem", "Vagas cadastradas com sucesso!");
            } else {
                sessao.setAttribute("mensagem", "Ocorreu um erro ao cadastrar as vagas.");
            }
        }
        if (sessao.getAttribute("login") != null) {
            getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
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
        List<HashMap<String, Object>> res = dao.executePreparedQuery(TableName.USUARIO, null, "nome = ? and senha = SHA(?)", params);
        String idLogin = res.size() > 0 ? Utilitarios.validaString(res.get(0).get("id")) : null;
        oklogin = !res.isEmpty();
        if (oklogin) {
            sessao.setAttribute("login", login);
            sessao.setAttribute("idLogin", idLogin);
            sessao.setAttribute("mensagem", "Login efetuado com sucesso");
            Cookie ckLogin = new Cookie("loginCookie", idLogin);
            ckLogin.setMaxAge(24 * 60 * 60);
            response.addCookie(ckLogin);
            addLoginSessao(request, sessao);
            getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
        } else {
            sessao.setAttribute("mensagem", "Login e/ou senha incorretos, tente novamente.");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    private boolean cadastraCliente(HttpServletRequest request)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        return dao.insert(new Cliente(nome, cpf, email, senha));
    }
    
    private boolean cadastraEmbarcacao(HttpServletRequest request)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        int tamanho = Utilitarios.validaInteger(request.getParameter("tamanho"));
        String tipo = request.getParameter("tipo");
        int idProprietario = Utilitarios.validaInteger(request.getParameter("idUsuario"));
        Usuario proprietario = dao.consultarCliente(idProprietario);
        return dao.insert(new Embarcacao(nome, tamanho, tipo, proprietario));
    }
    
    private boolean cadastraVendedor(HttpServletRequest request)
            throws ServletException, IOException {
        String matricula = request.getParameter("matricula");
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        return dao.insert(new Vendedor(matricula, nome, cpf, email, senha));
    }
    
    private boolean cadastraVenda(HttpServletRequest request)
            throws ServletException, IOException {
        long idCliente = Utilitarios.validaLong(request.getParameter("idUsuario"));
        long idEmbarcacao = Utilitarios.validaLong(request.getParameter("idEmbarcacao"));
        long idVendedor = Utilitarios.validaLong(request.getParameter("idVendedor"));
        Embarcacao embarcacao = dao.consultarEmbarcacao(idEmbarcacao);
        Vendedor vendedor = dao.consultarVendedor(idVendedor);
        Cliente cliente = dao.consultarCliente(idCliente);
        float valor = Utilitarios.validaFloat(request.getParameter("valor"));
        return dao.insert(new VendaBarco(embarcacao, vendedor, cliente, valor));
    }
    
    private boolean cadastraLocacao(HttpServletRequest request)
            throws ServletException, IOException {
        long idEmbarcacao = Utilitarios.validaLong(request.getParameter("idEmbarcacao"));
        long idCliente = Utilitarios.validaLong(request.getParameter("idUsuario"));
        long idMarina = Utilitarios.validaLong(request.getParameter("idMarina"));
        Embarcacao embarcacao = dao.consultarEmbarcacao(idEmbarcacao);
        Cliente cliente = dao.consultarCliente(idCliente);
        Marina marina = dao.consultarMarina(idMarina);
        float valor = Utilitarios.validaFloat(request.getParameter("valor"));
        return dao.insert(new LocacaoGaragemBarco(embarcacao, cliente, valor, marina));
    }
    
    private boolean cadastraMarina(HttpServletRequest request)
            throws ServletException, IOException {
        int vagas = Utilitarios.validaInteger(request.getParameter("vagas"));
        return dao.insert(new Marina(vagas));
    }
    
    private boolean cadastraVagas(HttpServletRequest request)
            throws ServletException, IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", Utilitarios.validaInteger(request.getParameter("idMarina")));
        parameters.put("totalVagas", request.getParameter("vagas"));
        return dao.update("Marina", parameters);
    }
    
    private void addLoginSessao(HttpServletRequest request, HttpSession sessao) {
        if (sessao.getAttribute("login") == null) {
            String loginCookie = retornarCookieLogin(request).getValue();
            if (loginCookie != null) {
                sessao.setAttribute("login", loginCookie);
            }
        }
    }
    
    private Cookie retornarCookie(HttpServletRequest request, String nameCookie) {
        if (!nameCookie.isEmpty()) {
            Cookie listaCookies[] = request.getCookies();
            if (listaCookies != null) {
                for (Cookie c : listaCookies) {
                    if (c.getName().equals(nameCookie)) {
                        return c;
                    }
                }
            }
        }
        return null;
    }
    
    private Cookie retornarCookieLogin(HttpServletRequest request) {
        return retornarCookie(request, "loginCookie");
    }
    
    private void armanezarAcessosPaginas(HttpServletRequest request, HttpServletResponse response, String namePage) {
        Object objCookie = retornarCookie(request, Utilitarios.validaString(namePage));
        if (objCookie != null) {
            Cookie ckPagina = (Cookie) objCookie;
            ckPagina.setMaxAge(24 * 60 * 60);
            int visitas = Utilitarios.validaInteger(ckPagina.getValue()) + 1;
            ckPagina.setValue(Utilitarios.validaString(visitas));
            response.addCookie(ckPagina);
        }
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
            case "visualizarMarinas":
                attribute = (ArrayList<HashMap<String, Object>>) dao.consultarMarinas();
                break;
            case "visualizarVendas":
                attribute = (ArrayList<HashMap<String, Object>>) dao.consultarVendas();
                break;
            case "visualizarAlugueis":
                attribute = (ArrayList<HashMap<String, Object>>) dao.consultarAlugueis();
                break;
            default:
                break;
        }
        request.setAttribute(parameter, attribute);
        getServletContext().getRequestDispatcher("/"+parameter+".jsp").forward(request, response);
    }
    
    private void forwardCadastrar(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
            throws ServletException, IOException {
        String parameter = Utilitarios.validaString(request.getParameterMap().get("op")[0]);
        if ("cadastroEmbarcacao".equals(parameter)) {
            request.setAttribute("clientes", dao.consultarClientes());
        }
        if ("cadastroVenda".equals(parameter)) {
            request.setAttribute("embarcacoes", dao.consultarEmbarcacoes());
            request.setAttribute("clientes", dao.consultarClientes());
            request.setAttribute("vendedores", dao.consultarVendedores());
        }
        if ("cadastroLocacao".equals(parameter)) {
            request.setAttribute("embarcacoes", dao.consultarEmbarcacoes());
            request.setAttribute("clientes", dao.consultarClientes());
            request.setAttribute("marinas", dao.consultarMarinas());
        }
        if ("cadastroVagas".equals(parameter)) {
            List<?> marinas = dao.consultarMarinas();
            abort(request, response, sessao, marinas.isEmpty(),
                    "?? necess??rio ter marinas cadastradas para alterar o n??mero de vagas");
            request.setAttribute("marinas", marinas);
        }
        getServletContext().getRequestDispatcher("/"+parameter+".jsp").forward(request, response);
    }
    
    private void abort(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession sessao,
            boolean condicao,
            String mensagem)
            throws ServletException, IOException {
        if (condicao) {
            sessao.setAttribute("mensagem", mensagem);
            getServletContext().getRequestDispatcher("/inicial.jsp").forward(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
