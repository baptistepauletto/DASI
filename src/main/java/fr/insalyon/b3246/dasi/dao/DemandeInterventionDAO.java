/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.util.Message;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author bpauletto
 */
public class DemandeInterventionDAO {
    
    public static void persister(DemandeIntervention demande){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(demande);
    }
    
    public static DemandeIntervention executerRequete(Employe emp, String jpql ){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("employe",emp);
        DemandeIntervention demande = (DemandeIntervention) query.getSingleResult();
        return demande;
    }
}
