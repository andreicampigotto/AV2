/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ifc.edu.br.av2.dao;

import ifc.edu.br.av2.model.Cliente;
import ifc.edu.br.av2.model.Embarcacao;
import ifc.edu.br.av2.model.Usuario;
import ifc.edu.br.av2.model.Vendedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author andre
 */
public class DAO {
     
    EntityManagerFactory emf;
    EntityManager em;

    public DAO() {
        emf = Persistence.createEntityManagerFactory("meuPU");
        em = emf.createEntityManager();
    }
    
     public void inserirCliente(Cliente c) {
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        em.persist(c);
        tx.commit();
    }
     
     public void inserirVendedor(Vendedor v) {
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        em.persist(v);
        tx.commit();
    }
     
    public void inserirEmbarcacao(Embarcacao e) {
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        em.persist(e);
        tx.commit();
    }
    
     public List consultarClientes() {
        List clientes = em.createQuery("from Cliente", Cliente.class).getResultList();
        return clientes;
    }
     
    public List consultarVendedores() {
        List vendedores = em.createQuery("from Vendedor", Vendedor.class).getResultList();
        return vendedores;
    }
    
    public List consultarEmbarcacoes() {
        List embarcacoes = em.createQuery("from Embarcacao", Embarcacao.class).getResultList();
        return embarcacoes;
    }
    
    public Cliente consultarCliente(Long id) {
        Query q = em.createQuery("from Cliente where id = :id", Cliente.class);
        q.setParameter("id", id);
        return (Cliente) q.getSingleResult();
    }
    
    public Vendedor consultarVendedor(Long id) {
        Query q = em.createQuery("from Vendedor where id = :id", Vendedor.class);
        q.setParameter("id", id);
        return (Vendedor) q.getSingleResult();
    }
    
    public Embarcacao consultarEmbarcacao(Long id) {
        Query q = em.createQuery("from Embarcacao where id = :id", Embarcacao.class);
        q.setParameter("id", id);
        return (Embarcacao) q.getSingleResult();
    }
}
