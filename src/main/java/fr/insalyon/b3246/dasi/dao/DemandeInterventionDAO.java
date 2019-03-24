/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import java.util.Calendar;
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

    
    public static List<DemandeIntervention> tableauDeBordEmploye() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startTime = cal.getTime();
        String jpql = "select d from DemandeIntervention d where d.dateDeb >= :today";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("today",startTime,TemporalType.DATE);
        List<DemandeIntervention> demande = (List<DemandeIntervention>) query.getResultList();
        return demande;
    }

}
