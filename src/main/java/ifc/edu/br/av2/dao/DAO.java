package ifc.edu.br.av2.dao;

import ifc.edu.br.av2.consts.TableName;
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
import java.util.Map;
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
            Connection conn = ConnectionFactory.connection("mysql", "root", "testando");
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
            sb.append(" create table if not exists ").append(TableName.USUARIO)
                    .append(" (id integer not null, ")
                    .append(" nome varchar(255), ")
                    .append(" senha varchar(255), ")
                    .append(" cpf varchar(255), ")
                    .append(" email varchar(255), ")
                    .append(" primary key (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists ").append(TableName.CLIENTE)
                    .append(" (id integer not null, ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references ").append(TableName.USUARIO).append(" (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists ").append(TableName.VENDEDOR)
                    .append(" (id integer not null, ")
                    .append(" matricula varchar(255), ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references ").append(TableName.USUARIO).append(" (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists ").append(TableName.EMBARCACAO)
                    .append(" (id integer not null, ")
                    .append(" tamanho integer, ")
                    .append(" tipo varchar(255), ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references ").append(TableName.USUARIO).append(" (id)) ");
            stmt.executeUpdate(sb.toString());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert(Object o) {
        try {
            Connection conn = ConnectionFactory.connection("jeffersonmendes_pgm4", "root", "testando");
            if (o instanceof Cliente) {
                inserirUsuario((Usuario) o, conn);
                inserirCliente((Cliente) o, conn);
            } else if (o instanceof Vendedor) {
                inserirUsuario((Usuario) o, conn);
                inserirVendedor((Vendedor) o, conn);
            } else if (o instanceof Embarcacao) {
                inserirEmbarcacao((Embarcacao) o, conn);
            }
            conn.commit();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void inserirUsuario(Usuario u, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.USUARIO
                + " (id, nome, cpf, email, senha) "
                + " values (?, ?, ?, ?, SHA(?)) ");
        long id = Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.USUARIO, "max(id) + 1 id", null).get(0)).get("id")), 1L);
        u.setId(id);
        ps.setLong(1, id);
        ps.setString(2, u.getNome());
        ps.setString(3, u.getCpf());
        ps.setString(4, u.getEmail());
        ps.setString(5, u.getSenha());
        ps.executeUpdate();
    }
    
    private void inserirCliente(Cliente c, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.CLIENTE
                + " (id, idUsuario) "
                + " values (?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.CLIENTE, "max(id) + 1 id", null).get(0)).get("id")), 1L));
        ps.setLong(2, c.getIdUsuario());
        ps.executeUpdate();
    }
     
    private void inserirVendedor(Vendedor v, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.VENDEDOR
                + " (id, matricula, idUsuario) "
                + " values (?, ?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.VENDEDOR, "max(id) + 1 id", null).get(0)).get("id")), 1L));
        ps.setString(2, v.getMatricula());
        ps.setLong(3, v.getIdUsuario());
        ps.executeUpdate();
    }
     
    private void inserirEmbarcacao(Embarcacao e, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.EMBARCACAO
                + " (id, tamanho, tipo, idUsuario) "
                + " values (?, ?, ?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.EMBARCACAO, "max(id) + 1 id", null).get(0)).get("id")), 1L));
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
            res = Utilitarios.resultSetToList(rs);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
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
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            res = Utilitarios.resultSetToList(rs);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public List consultarClientes() {
        return executeQuery(TableName.CLIENTE + " a, " + TableName.USUARIO + " b ", "a.idUsuario = b.id");
    }
     
    public List consultarVendedores() {
        return executeQuery(TableName.VENDEDOR + " a, " + TableName.USUARIO + " b ", "a.idUsuario = b.id");
    }
    
    public List consultarEmbarcacoes() {
        return executeQuery(TableName.EMBARCACAO);
    }
    
    public List consultarUsuarios() {
        return executeQuery(TableName.USUARIO);
    }
    
    public Cliente consultarCliente(long id) {
        Cliente c = new Cliente();
        StringBuilder sb = new StringBuilder();
        sb.append(" a.idUsuario = b.id ")
                .append(" and a.id = ").append(id);
        Object queryRes = executeQuery(TableName.CLIENTE + " a, " + TableName.USUARIO + " b ", sb.toString()).get(0);
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
        Object queryRes = executeQuery(TableName.VENDEDOR + " a, " + TableName.USUARIO + " b ", sb.toString()).get(0);
        if (queryRes.getClass().isInstance(Vendedor.class)) {
            v = (Vendedor) queryRes;
        }
        return v;
    }
    
    public Embarcacao consultarEmbarcacao(long id) {
        Embarcacao e = new Embarcacao();
        Object queryRes = executeQuery(TableName.EMBARCACAO + " a", "a.id = " + id).get(0);
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
        Object queryRes = executeQuery(TableName.USUARIO + " a", "a.id = " + id).get(0);
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
        Object queryRes = executeQuery(TableName.USUARIO + " a", restriction).get(0);
        if (queryRes.getClass().isInstance(Usuario.class)) {
            u = (Usuario) queryRes;
        }
        return u;
    }

}
