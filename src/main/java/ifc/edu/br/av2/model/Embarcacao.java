package ifc.edu.br.av2.model;

public class Embarcacao {
    
    private long id;
    private String nome;
    private int tamanho;
    private String tipo;
    private Usuario proprietario;

    public Embarcacao(String nome, int tamanho, String tipo, Usuario proprietario) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.tipo = tipo;
        this.proprietario = proprietario;
    }

    public Embarcacao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

}
