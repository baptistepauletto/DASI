/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import javax.persistence.EntityManager;

/**
 *
 * @author bpauletto
 */
public class DemandeInterventionDAO {
    
    public static void persister(DemandeIntervention p){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(p);
    }
}
