package ifc.edu.br.av2.model;

import ifc.edu.br.av2.dao.DAO;

public class Vendedor extends Usuario {
    
    private String matricula;

    public Vendedor(String matricula, String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
        this.matricula = matricula;
    }

    public Vendedor() {
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
