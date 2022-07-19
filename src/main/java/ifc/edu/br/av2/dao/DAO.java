package ifc.edu.br.av2.dao;

import ifc.edu.br.av2.consts.TableName;
import ifc.edu.br.av2.model.Cliente;
import ifc.edu.br.av2.model.Embarcacao;
import ifc.edu.br.av2.model.LocacaoGaragemBarco;
import ifc.edu.br.av2.model.Marina;
import ifc.edu.br.av2.model.Usuario;
import ifc.edu.br.av2.model.VendaBarco;
import ifc.edu.br.av2.model.Vendedor;
import ifc.edu.br.av2.util.Utilitarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    
    static final String BD = "jeffersonmendes_pgm4";
    static final String BD_USER = "root";
    static final String BD_PASSWORD = "testando";

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
            sb.append(" create database if not exists ").append(BD);
            stmt.execute(sb.toString());
            sb = new StringBuilder();
            sb.append(" create user if not exists 'root'@'").append(BD)
                    .append("' identified by '").append(BD_PASSWORD).append("' ");
            stmt.execute(sb.toString());
            conn = ConnectionFactory.connection(BD, BD_USER, BD_PASSWORD);
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
                    .append(" nome varchar(255), ")
                    .append(" tamanho integer, ")
                    .append(" tipo varchar(255), ")
                    .append(" idUsuario integer, ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idUsuario) references ").append(TableName.USUARIO).append(" (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists ").append(TableName.VENDA_BARCO)
                    .append(" (id integer not null, ")
                    .append(" idEmbarcacao integer, ")
                    .append(" idVendedor integer, ") 
                    .append(" idCliente integer, ")
                    .append(" valor varchar(255), ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idEmbarcacao) references ").append(TableName.EMBARCACAO).append(" (id), ")
                    .append(" foreign key (idVendedor) references ").append(TableName.VENDEDOR).append(" (id), ")
                    .append(" foreign key (idCliente) references ").append(TableName.CLIENTE).append(" (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists ").append(TableName.MARINA)
                    .append(" (id integer not null, ")
                    .append(" totalVagas integer, ")
                    .append(" primary key (id)) ");
            stmt.executeUpdate(sb.toString());
            sb = new StringBuilder();
            sb.append(" create table if not exists ").append(TableName.LOCACAO_GARAGEM_BARCO)
                    .append(" (id integer not null, ")
                    .append(" idEmbarcacao integer, ")
                    .append(" idCliente integer, ")
                    .append(" idMarina integer, ")
                    .append(" valor varchar(255), ")
                    .append(" primary key (id), ")
                    .append(" foreign key (idEmbarcacao) references ").append(TableName.EMBARCACAO).append(" (id), ")
                    .append(" foreign key (idCliente) references ").append(TableName.CLIENTE).append(" (id), ")
                    .append(" foreign key (idMarina) references ").append(TableName.MARINA).append(" (id)) ");
            stmt.executeUpdate(sb.toString());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean insert(Object o) {
        try {
            Connection conn = ConnectionFactory.connection(BD, BD_USER, BD_PASSWORD);
            if (o instanceof Cliente) {
                inserirUsuario((Usuario) o, conn);
                inserirCliente((Cliente) o, conn);
            } else if (o instanceof Vendedor) {
                inserirUsuario((Usuario) o, conn);
                inserirVendedor((Vendedor) o, conn);
            } else if (o instanceof Embarcacao) {
                inserirEmbarcacao((Embarcacao) o, conn);
            } else if (o instanceof Marina) {
                inserirMarina((Marina) o , conn);
            } else if (o instanceof VendaBarco) {
                inserirVenda((VendaBarco) o, conn);
            } else if (o instanceof LocacaoGaragemBarco) {
                inserirAluguel((LocacaoGaragemBarco) o, conn);
            }
            commit(conn);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
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
                + " (id, nome, tamanho, tipo, idUsuario) "
                + " values (?, ?, ?, ?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.EMBARCACAO, "max(id) + 1 id", null).get(0)).get("id")), 1L));
        ps.setString(2, e.getNome());
        ps.setInt(3, e.getTamanho());
        ps.setString(4, e.getTipo());
        ps.setLong(5, e.getProprietario().getId());
        ps.executeUpdate();
    }
    
    private void inserirMarina(Marina m, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.MARINA
                + " (id, totalVagas) "
                + " values (?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.MARINA, "max(id) + 1 id", null).get(0)).get("id")), 1L));
        ps.setInt(2, m.getTotalVagas());
        ps.executeUpdate();
    }
    
    private void inserirVenda(VendaBarco vb, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.VENDA_BARCO
                + " (id, idEmbarcacao, idVendedor, idCliente, valor) "
                + " values (?, ?, ?, ?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.MARINA, "max(id) + 1 id", null).get(0)).get("id")), 1L));
        ps.setInt(2, Utilitarios.validaInteger(vb.getEmbarcacao().getId()));
        ps.setInt(3, Utilitarios.validaInteger(vb.getVendedor().getId()));
        ps.setInt(4, Utilitarios.validaInteger(vb.getCliente().getId()));
        ps.setFloat(5, Utilitarios.validaFloat(vb.getValor()));
        ps.executeUpdate();
    }
    
    private void inserirAluguel(LocacaoGaragemBarco lgb, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(" insert into " + TableName.LOCACAO_GARAGEM_BARCO
                + " (id, idEmbarcacao, idCliente, idMarina, valor) "
                + " values (?, ?, ?, ?, ?) ");
        ps.setLong(1, Utilitarios.validaLong(Utilitarios.validaString(((Map) executeQuery(TableName.MARINA, "max(id) + 1 id", null).get(0)).get("id")), 1L));
        ps.setInt(2, Utilitarios.validaInteger(lgb.getEmbarcacao().getId()));
        ps.setInt(3, Utilitarios.validaInteger(lgb.getCliente().getId()));
        ps.setInt(4, Utilitarios.validaInteger(lgb.getMarina().getId()));
        ps.setFloat(5, Utilitarios.validaFloat(lgb.getValor()));
        ps.executeUpdate();
    }
    
    public boolean update(Object o, Map<String, Object> parameters) {
        try {
            Connection conn = ConnectionFactory.connection(BD, BD_USER, BD_PASSWORD);
            if (o.toString().equals("Marina")) {
                updateWithId(TableName.MARINA, parameters, conn);
            }
            commit(conn);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    private void updateWithId(String tableName, Map<String, Object> parameters, Connection conn) throws SQLException {
        Object id = parameters.get("id");
        parameters.remove("id");
        StringBuilder sb = new StringBuilder();
        sb.append(" update ").append(tableName);
        int i = 0;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (i == 0) {
                sb.append(" set ");
            } else {
                sb.append(", ");
            }
            sb.append(key).append(" = ").append(value);
            i++;
        }
        sb.append(" where id = ").append(id);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sb.toString());        
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
            Connection conn = ConnectionFactory.connection(BD, BD_USER, BD_PASSWORD);
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
            Connection conn = ConnectionFactory.connection(BD, BD_USER, BD_PASSWORD);
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
        return executeQuery(TableName.CLIENTE + " a, " + TableName.USUARIO + " b ",
                "a.*, b.nome, b.senha, b.cpf, b.email",
                "a.idUsuario = b.id");
    }
     
    public List consultarVendedores() {
        return executeQuery(TableName.VENDEDOR + " a, " + TableName.USUARIO + " b ",
                "a.*, b.nome, b.senha, b.cpf, b.email",
                "a.idUsuario = b.id");
    }
    
    public List consultarEmbarcacoes() {
        return executeQuery(TableName.EMBARCACAO);
    }
    
    public List consultarUsuarios() {
        return executeQuery(TableName.USUARIO);
    }
    
    public List consultarMarinas() {
        return executeQuery(TableName.MARINA);
    }
    
    public List consultarVendas() {
        return executeQuery(TableName.VENDA_BARCO);
    }
    
    public List consultarAlugueis() {
        return executeQuery(TableName.LOCACAO_GARAGEM_BARCO);
    }
    
    public Cliente consultarCliente(long id) {
        Cliente c = new Cliente();
        StringBuilder sb = new StringBuilder();
        sb.append(" a.idUsuario = b.id ")
                .append(" and a.id = ").append(id);
        Map<String, Object> queryRes = (Map<String, Object>) executeQuery(TableName.CLIENTE + " a, " + TableName.USUARIO + " b ",
                "a.*, b.nome, b.senha, b.cpf, b.email",
                sb.toString()).get(0);
        if (queryRes.size() > 0) {
            c.setId(Utilitarios.validaLong(queryRes.get("id")));
            c.setNome(Utilitarios.validaString(queryRes.get("nome")));
            c.setEmail(Utilitarios.validaString(queryRes.get("email")));
            c.setCpf(Utilitarios.validaString(queryRes.get("cpf")));
            c.setSenha(Utilitarios.validaString(queryRes.get("senha")));
        }
        return c;
    }
    
    public Vendedor consultarVendedor(long id) {
        Vendedor v = new Vendedor();
        StringBuilder sb = new StringBuilder();
        sb.append(" a.idUsuario = b.id ")
                .append(" and a.id = ").append(id);
        Map<String, Object> queryRes = (Map<String, Object>) executeQuery(TableName.VENDEDOR + " a, " + TableName.USUARIO + " b ",
                "a.*, b.nome, b.senha, b.cpf, b.email",
                sb.toString()).get(0);
        if (queryRes.size() > 0) {
            v.setId(Utilitarios.validaLong(queryRes.get("id")));
            v.setNome(Utilitarios.validaString(queryRes.get("nome")));
            v.setEmail(Utilitarios.validaString(queryRes.get("email")));
            v.setCpf(Utilitarios.validaString(queryRes.get("cpf")));
            v.setSenha(Utilitarios.validaString(queryRes.get("senha")));
            v.setMatricula(Utilitarios.validaString(queryRes.get("matricula")));
        }
        return v;
    }
    
    public boolean isVendedor(long id) {
        List<?> queryRes = executeQuery(TableName.VENDEDOR + " a ", " a.idUsuario = " + id);
        return queryRes.size() > 0;
    }
    
    public Embarcacao consultarEmbarcacao(long id) {
        Embarcacao e = new Embarcacao();
        Map<String, Object> queryRes = (Map<String, Object>) executeQuery(TableName.EMBARCACAO + " a", "a.id = " + id).get(0);
        if (queryRes.size() > 0) {
            e.setId(Utilitarios.validaInteger(queryRes.get("id")));
            e.setNome(Utilitarios.validaString(queryRes.get("nome")));
            e.setTipo(Utilitarios.validaString(queryRes.get("tipo")));
            e.setTamanho(Utilitarios.validaInteger(queryRes.get("tamanho")));
            e.setProprietario(consultarCliente(Utilitarios.validaLong(queryRes.get("idUsuario"))));
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
        Map<String, Object> queryRes = (Map<String, Object>) executeQuery(TableName.USUARIO + " a", "a.id = " + id).get(0);
        if (queryRes.size() > 0) {
            u.setId(Utilitarios.validaLong(queryRes.get("id")));
            u.setNome(Utilitarios.validaString(queryRes.get("nome")));
            u.setEmail(Utilitarios.validaString(queryRes.get("email")));
            u.setCpf(Utilitarios.validaString(queryRes.get("cpf")));
            u.setSenha(Utilitarios.validaString(queryRes.get("senha")));
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
        Map<String, Object> queryRes = (Map<String, Object>) executeQuery(TableName.USUARIO + " a", restriction).get(0);
        if (queryRes.size() > 0) {
            u.setId(Utilitarios.validaLong(queryRes.get("id")));
            u.setNome(Utilitarios.validaString(queryRes.get("nome")));
            u.setEmail(Utilitarios.validaString(queryRes.get("email")));
            u.setCpf(Utilitarios.validaString(queryRes.get("cpf")));
            u.setSenha(Utilitarios.validaString(queryRes.get("senha")));
        }
        return u;
    }
    
    public Marina consultarMarina(long id) {
        Marina marina = new Marina();
        Map<String, Object> queryRes = (Map<String, Object>) executeQuery(TableName.MARINA + " a", "a.id = " + id).get(0);
        if (queryRes.size() > 0) {
            marina.setId(Utilitarios.validaLong(queryRes.get("id")));
            marina.setTotalVagas(Utilitarios.validaInteger(queryRes.get("totalVagas")));
        }
        return marina;
    }
    
    private void commit(Connection conn) throws SQLException {
        if (!conn.getAutoCommit()) {
            conn.commit();
        }
    }

}
