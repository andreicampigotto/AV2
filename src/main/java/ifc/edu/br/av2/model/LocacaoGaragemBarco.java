package ifc.edu.br.av2.model;

public class LocacaoGaragemBarco {
    
    private Embarcacao embarcacao;
    private Cliente cliente;
    private float valor;
    private Marina marina;

    public LocacaoGaragemBarco(Embarcacao embarcacao, Cliente cliente, Float valor, Marina marina) {
        this.embarcacao = embarcacao;
        this.cliente = cliente;
        this.valor = valor;
        this.marina = marina;
    }

    public LocacaoGaragemBarco() {
    }

    public Embarcacao getEmbarcacao() {
        return embarcacao;
    }

    public void setEmbarcacao(Embarcacao embarcacao) {
        this.embarcacao = embarcacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Marina getMarina() {
        return marina;
    }

    public void setMarina(Marina marina) {
        this.marina = marina;
    }
    
}
