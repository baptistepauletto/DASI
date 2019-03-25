/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.Client;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author bpauletto
 */
public class ClientDAO {
    
    public static Client verifUtilisateur(String adresseMail, String motDePasse){
        String jpql = "select c from Client c where c.adresseMail = :adresseMail and c.motDePasse = :motDePasse";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("adresseMail", adresseMail);
        query.setParameter("motDePasse", motDePasse);
        Client resultat = (Client) query.getSingleResult();
        return resultat;
    }
    
    public static Client trouver(Integer id){
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.find(Client.class, id);
    }
    
    public static List<Client> trouverTous(){
        EntityManager em = JpaUtil.obtenirEntityManager();
        String jpql = "select c from Client c";
        Query query = em.createQuery(jpql);
        List<Client> resultat = (List<Client>) query.getResultList();
        return resultat;
    }
}
