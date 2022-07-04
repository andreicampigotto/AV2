package ifc.edu.br.av2.model;

public class VendaBarco {
    
    private Embarcacao embarcacao;
    private Vendedor vendedor;
    private Cliente cliente;
    private float valor;

    public VendaBarco(Embarcacao embarcacao, Vendedor vendedor, Cliente cliente, Float valor) {
        this.embarcacao = embarcacao;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.valor = valor;
    }

    public VendaBarco() {
    }

    public Embarcacao getEmbarcacao() {
        return embarcacao;
    }

    public void setEmbarcacao(Embarcacao embarcacao) {
        this.embarcacao = embarcacao;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
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

}
