/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.vue;

import fr.insalyon.b3246.dasi.metier.modele.Client;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author bpauletto
 */
public class Main {
    
     public static void main(String[] args) {
        Client client = new Client("Pauletto");
         
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DASI-TP1-PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        em.persist(client);
        tx.commit();
        
       
        
     }
}
