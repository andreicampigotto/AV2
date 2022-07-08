package ifc.edu.br.av2.model;

import ifc.edu.br.av2.dao.DAO;

public class Cliente extends Usuario {

    public Cliente(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
    }

    public Cliente() {
    }

    @Override
    public long getIdUsuario() {
        return this.getId();
    }

    @Override
    protected void createRecord() {
        DAO dao = new DAO();
        dao.insert(this);
    }
    
}
