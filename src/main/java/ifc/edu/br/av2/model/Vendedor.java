package ifc.edu.br.av2.model;

public class Vendedor extends Usuario {
    
    private String matricula;

    public Vendedor(String matricula, String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
        this.matricula = matricula;
    }

    public Vendedor() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
