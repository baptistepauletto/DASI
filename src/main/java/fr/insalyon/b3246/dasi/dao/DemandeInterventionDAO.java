/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.util.Message;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author bpauletto
 */
public class DemandeInterventionDAO {
    
    public static void persister(DemandeIntervention demande){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(demande);
    }
    
    public static DemandeIntervention interventionEmployeEnCours(Employe emp){
        String jpql = "select d from DemandeIntervention d where d.employe =:employe and d.statut = fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut.EN_COURS";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("employe",emp);
        DemandeIntervention demande = (DemandeIntervention) query.getSingleResult();
        return demande;
    }

    public static List<DemandeIntervention> historiqueClient(Client c) {
        String jpql = "select d from DemandeIntervention d where d.client =:client";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("client",c);
        List<DemandeIntervention> demande = (List<DemandeIntervention>) query.getResultList();
        return demande;
    }
/*
    public static List<DemandeIntervention> tableauDeBordEmploye() {
        Date now = new Date();
       String jpql = "select d from DemandeIntervention d where d.date = :today";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("today",now);
        List<DemandeIntervention> demande = (List<DemandeIntervention>) query.getResultList();
        return demande;
    }*/

}
