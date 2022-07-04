package ifc.edu.br.av2.model;

public class Embarcacao {
    
    private int tamanho;
    private String tipo;
    private Usuario proprietario;

    public Embarcacao(int tamanho, String tipo, Usuario proprietario) {
        this.tamanho = tamanho;
        this.tipo = tipo;
        this.proprietario = proprietario;
    }

    public Embarcacao() {
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

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

}
