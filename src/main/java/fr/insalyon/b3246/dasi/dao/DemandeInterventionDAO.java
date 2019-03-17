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
    
    public static void cloreDemandeIntervention(Employe emp, boolean succes, String descriptionEmp ){
        EntityManager em = JpaUtil.obtenirEntityManager();
        String jpql = "select d from DemandeIntervention d where d.statut = fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut.EN_COURS";
        //String jpql = "select d from DemandeIntervention d";
        Query query = em.createQuery(jpql);
        List<DemandeIntervention> resultat = (List<DemandeIntervention>) query.getResultList();
        DemandeIntervention demande = null;
        
        for (int i = 0; i<resultat.size(); i++){
            if (resultat.get(i).getEmploye().getAdresseMail() == emp.getAdresseMail() ){
                demande = resultat.get(i);
                break;
            }
        }
        
        demande.setDateFin(new Date());
        if (succes){
            demande.setStatut(DemandeIntervention.Statut.FINIE_SUCCES);
            demande.setDescriptionEmploye(descriptionEmp);
            Message.envoyerNotification(demande.getClient().getNumTelephone(), "L'intervention que vous avez demandé est accomplie.");
        }
        else {
            demande.setStatut(DemandeIntervention.Statut.FINIE_ECHEC);
            Message.envoyerNotification(demande.getClient().getNumTelephone(), "L'intervention que vous avez demandé a échoué. Veuillez nous recontacter pour plus d'informations.");
        }
        emp.setEstDisponible(true);
        em.persist(demande);
    }
}
