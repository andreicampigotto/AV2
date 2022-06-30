/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ifc.edu.br.av2.model;

/**
 *
 * @author User
 */
public class Embarcacao {
    
    private String nome;
    private String tipo;

    public Embarcacao(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public Embarcacao() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
