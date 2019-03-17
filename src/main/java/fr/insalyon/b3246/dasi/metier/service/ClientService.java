/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.dao.PersonneDAO;
import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.util.GeoTest;
import fr.insalyon.b3246.dasi.util.Message;
import java.time.LocalDateTime;
import java.util.Calendar;
import javax.persistence.EntityManager;

/**
 *
 * @author bpauletto
 */
public class ClientService {
    
    public static void inscrireClient(Client c){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            c.setCoords(GeoTest.getLatLng(c.getAdresse()));
            PersonneDAO.persister(c);
            JpaUtil.validerTransaction();
            Message.envoyerMail("noreply@proactif.com", c.getAdresseMail(), "Confirmation d'inscription sur Proact'if", "L'équipe Proact'if vous confirme que votre inscription s'est bien déroulée.");
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            Message.envoyerMail("noreply@proactif.com", c.getAdresseMail(), "Echer de l'inscription sur Proact'if", "Un problème a eu lieu lors de l'inscription sur Proact'if. Veuillez réessayer ultérieurement.");
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
    public static void faireDemandeIntervention(DemandeIntervention demande, Client c){
        demande.setClient(c);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(demande.getDateDeb());
        //Employe emp = EmployeService.rechercheEmployeDispo(c.getCoords(), calendar.get(Calendar.HOUR_OF_DAY));
        Employe emp = EmployeService.rechercheEmployeDispo(c.getCoords(), 15);
        if (emp == null){
            demande.setStatut(Statut.REJETEE);
            Message.envoyerNotification(c.getNumTelephone(), "Pas d'employé disponible. Nous vous prions de réessayer ultérieurement");        }
        else {
            demande.setEmploye(emp);
            Message.envoyerNotification(emp.getNumTelephone(), "Vous avez une intervention à faire");
        }
        
        DemandeInterventionService.ajouterDemandeInvervention(demande);
    }
}
