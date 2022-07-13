package ifc.edu.br.av2.dao;

import ifc.edu.br.av2.model.Cliente;
import ifc.edu.br.av2.model.Embarcacao;
import ifc.edu.br.av2.model.Usuario;
import ifc.edu.br.av2.model.Vendedor;
import ifc.edu.br.av2.util.Utilitarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    public DAO(boolean createTables) {
        if (createTables)
            this.init();
    }
    
    public DAO() {
    }
    
    private void init() {
        try {
            Connection conn = ConnectionFactory.connection("localhost", "root", "");
            Statement stmt = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append(" create database if not exists jeffersonmendes_pgm4 ");
            stmt.execute(sb.toString());
            sb = new StringBuilder();
            sb.append(" create user if not exists 'root'@'jeffersonmendes_pgm4' identified by 'testando' ");
            stmt.execute(sb.toString());
            conn = ConnectionFactory.connection("jeffersonmendes_pgm4", "root", "testando");
            stmt = conn.createStatement();
            sb = new StringBuilder();
            sb.append(" create table if not exists usuario ")
                    .append(" (id integer not null, ")
                    .append(" nome varchar(255), ")
                    .append(" senha varchar(255), ")
                    .append(" cpf varchar(255), ")
                    .append(" email varchar(255), ")
                    .append(" primary key (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists cliente ")
                    .append(" (id integer not null, ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references usuario(id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists vendedor ")
                    .append(" (id integer not null, ")
                    .append(" matricula varchar(255), ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references usuario(id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists vendedor ")
                    .append(" (id integer not null, ")
                    .append(" tamanho integer, ")
                    .append(" tipo varchar(255), ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references usuario(id)) ");
            stmt.executeUpdate(sb.toString());
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert(Object o) {
        try {
            Connection conn = ConnectionFactory.connection("jeffersonmendes_pgm4", "root", "testando");
            if (o.getClass().isInstance(Cliente.class)) {
                inserirUsuario((Usuario) o, conn);
                inserirCliente((Cliente) o, conn);
            } else if (o.getClass().isInstance(Vendedor.class)) {
                inserirUsuario((Usuario) o, conn);
                inserirVendedor((Vendedor) o, conn);
            } else if (o.getClass().isInstance(Embarcacao.class)) {
                inserirEmbarcacao((Embarcacao) o, conn);
            }
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void inserirUsuario(Usuario u, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into usuario "
                + " (id, nome, cpf, email, senha) "
                + " values (?, ?, ?, ?, ?) ");
        long id = Long.parseLong(executeQuery("usuario", "max(id) + 1", null).get(0).toString());
        u.setId(id);
        ps.setLong(1, id);
        ps.setString(2, u.getNome());
        ps.setString(3, u.getCpf());
        ps.setString(4, u.getEmail());
        ps.setString(5, u.getSenha());
        ps.executeUpdate();
    }
    
    private void inserirCliente(Cliente c, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into cliente "
                + " (id, idUsuario) "
                + " values (?, ?) ");
        ps.setLong(1, Long.parseLong(executeQuery("cliente", "max(id) + 1", null).get(0).toString()));
        ps.setLong(2, c.getIdUsuario());
        ps.executeUpdate();
    }
     
    private void inserirVendedor(Vendedor v, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into vendedor "
                + " (id, matricula, idUsuario) "
                + " values (?, ?, ?) ");
        ps.setLong(1, Long.parseLong(executeQuery("vendedor", "max(id) + 1", null).get(0).toString()));
        ps.setString(2, v.getMatricula());
        ps.setLong(3, v.getIdUsuario());
        ps.executeUpdate();
    }
     
    private void inserirEmbarcacao(Embarcacao e, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into embarcacao "
                + " (id, tamanho, tipo, idUsuario) "
                + " values (?, ?, ?, ?) ");
        ps.setLong(1, Long.parseLong(executeQuery("embarcacao", "max(id) + 1", null).get(0).toString()));
        ps.setInt(2, e.getTamanho());
        ps.setString(3, e.getTipo());
        ps.setLong(4, e.getProprietario().getIdUsuario());
        ps.executeUpdate();
    }
    
    private List executeQuery(String tableName) {
        return executeQuery(tableName, null, null);
    }
    
    private List executeQuery(String tableName, String restrictions) {
        return executeQuery(tableName, null, restrictions);
    }
    
    private List executeQuery(String tableName, String columns, String restrictions) {
        List<?> res = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.connection("jeffersonmendes_pgm4", "root", "testando");
            Statement stmt = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append(" select ").append(columns != null ? columns : "*")
                    .append(" from ").append(tableName);
            if (restrictions != null) {
                sb.append(" where ").append(restrictions);
            }
            ResultSet rs = stmt.executeQuery(sb.toString());
            conn.close();
            res = Utilitarios.resultSetToList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public List executePreparedQuery(String tableName, String columns, String restrictions, List<?> parameters) {
        List<?> res = new ArrayList<>();
        try {
            Connection conn = ConnectionFactory.connection("jeffersonmendes_pgm4", "root", "testando");
            StringBuilder sb = new StringBuilder();
            sb.append(" select ").append(columns != null ? columns : "*")
                    .append(" from ").append(tableName);
            if (restrictions != null) {
                sb.append(" where ").append(restrictions);
            }
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            for (int i = 1; i < parameters.size(); i++) {
                ps.setObject(i, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            conn.close();
            res = Utilitarios.resultSetToList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public List consultarClientes() {        
        return executeQuery("cliente a, usuario b", "a.idUsuario = b.id");
    }
     
    public List consultarVendedores() {
        return executeQuery("vendedor a, usuario b", "a.idUsuario = b.id");
    }
    
    public List consultarEmbarcacoes() {
        return executeQuery("embarcacao");
    }
    
    public List consultarUsuarios() {
        return executeQuery("usuario");
    }
    
    public Cliente consultarCliente(long id) {
        Cliente c = new Cliente();
        StringBuilder sb = new StringBuilder();
        sb.append(" a.idUsuario = b.id ")
                .append(" and a.id = ").append(id);
        Object queryRes = executeQuery("cliente a", sb.toString()).get(0);
        if (queryRes.getClass().isInstance(Cliente.class)) {
            c = (Cliente) queryRes;
        }
        return c;
    }
    
    public Vendedor consultarVendedor(long id) {
        Vendedor v = new Vendedor();
        StringBuilder sb = new StringBuilder();
        sb.append(" a.idUsuario = b.id ")
                .append(" and a.id = ").append(id);
        Object queryRes = executeQuery("vendedor a, usuario b", sb.toString()).get(0);
        if (queryRes.getClass().isInstance(Vendedor.class)) {
            v = (Vendedor) queryRes;
        }
        return v;
    }
    
    public Embarcacao consultarEmbarcacao(long id) {
        Embarcacao e = new Embarcacao();
        Object queryRes = executeQuery("embarcacao a", "a.id = " + id).get(0);
        if (queryRes.getClass().isInstance(Embarcacao.class)) {
            e = (Embarcacao) queryRes;
        }
        return e;
    }
    
    public Usuario consultarUsuario(long id) {
        Usuario u = new Usuario() {
            @Override
            public long getIdUsuario() {
                return id;
            }

            @Override
            protected void createRecord() {
            }
        };
        Object queryRes = executeQuery("usuario a", "a.id = " + id).get(0);
        if (queryRes.getClass().isInstance(Usuario.class)) {
            u = (Usuario) queryRes;
        }
        return u;
    }
    
    public Usuario consultarUsuario(String restriction) {
        Usuario u = new Usuario() {
            @Override
            public long getIdUsuario() {
                return 0;
            }

            @Override
            protected void createRecord() {
            }
        };
        Object queryRes = executeQuery("usuario a", restriction).get(0);
        if (queryRes.getClass().isInstance(Usuario.class)) {
            u = (Usuario) queryRes;
        }
        return u;
    }

}
