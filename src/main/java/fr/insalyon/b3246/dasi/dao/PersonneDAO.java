/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.Personne;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author bpauletto
 */
public class PersonneDAO {

    
    public static void persister(Personne p){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(p);
    }
    
    public static Personne trouver(Integer id) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Personne pTrouvee = em.find(Personne.class, id);
        return pTrouvee;
    }
    
    public static List<Personne> trouverTous(){
        EntityManager em = JpaUtil.obtenirEntityManager();
        String jpql = "select p from Personne p";
        Query query = em.createQuery(jpql);
        List<Personne> resultat = (List<Personne>) query.getResultList();
        return resultat;
    }
}
