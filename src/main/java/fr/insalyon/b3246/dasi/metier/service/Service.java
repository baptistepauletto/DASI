/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import com.google.maps.model.LatLng;
import fr.insalyon.b3246.dasi.dao.DemandeInterventionDAO;
import fr.insalyon.b3246.dasi.dao.EmployeDAO;
import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.dao.PersonneDAO;
import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import fr.insalyon.b3246.dasi.util.GeoTest;
import fr.insalyon.b3246.dasi.util.Message;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bpauletto
 */
public class Service {
    
    public static void inscrireClient(Client c) {
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

    public static void faireDemandeIntervention(DemandeIntervention demande, Client c) {
        demande.setClient(c);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(demande.getDateDeb());

        LatLng coordClient = c.getCoords();
        Integer heureDemande = calendar.get(Calendar.HOUR_OF_DAY);
        JpaUtil.creerEntityManager();
        List<Employe> resultat = null;
        Employe empRetenu = null;

        JpaUtil.ouvrirTransaction();

        try {
            String jpql = "select e from Employe e where e.estDisponible = true and e.heureDebTravail <= :heureDemande and e.heureFinTravail > :heureDemande";
            resultat = EmployeDAO.rechercheEmployeDispo(coordClient, heureDemande, jpql);
            // recherche de l'employe le plus proche geographiquement du client
            if (!resultat.isEmpty()) {
                empRetenu = resultat.get(0);
                Double dureeTrajet = GeoTest.getTripDurationByBicycleInMinute(empRetenu.getCoords(), coordClient);
                for (int i = 1; i < resultat.size(); i++) {
                    if (GeoTest.getTripDurationByBicycleInMinute(resultat.get(i).getCoords(), coordClient) < dureeTrajet) {
                        dureeTrajet = GeoTest.getTripDurationByBicycleInMinute(resultat.get(i).getCoords(), coordClient);
                        empRetenu = resultat.get(i);
                    }
                }
                empRetenu.setEstDisponible(false);
            }

            if (empRetenu == null) {
                demande.setStatut(DemandeIntervention.Statut.REJETEE);
                Message.envoyerNotification(c.getNumTelephone(), "Pas d'employé disponible. Nous vous prions de réessayer ultérieurement");
            } else {
                demande.setEmploye(empRetenu);
                Message.envoyerNotification(empRetenu.getNumTelephone(), empRetenu.getNom() + " Vous avez une intervention à faire");
            }

            DemandeInterventionDAO.persister(demande);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
    public static void ajouterDemandeInvervention(DemandeIntervention demande){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            DemandeInterventionDAO.persister(demande);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
    public static void inscrireEmploye(Employe emp) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            PersonneDAO.persister(emp);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static void cloreDemandeIntervention(Employe emp, boolean succes, String descriptionEmp) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        String jpql = "select d from DemandeIntervention d where d.employe =:employe and d.statut = fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut.EN_COURS";
        DemandeIntervention demande = null;
        try {
            demande = DemandeInterventionDAO.executerRequete(emp, jpql);
            demande.setDateFin(new Date());
            if (succes) {
                demande.setStatut(DemandeIntervention.Statut.FINIE_SUCCES);
                demande.setDescriptionEmploye(descriptionEmp);
                Message.envoyerNotification(demande.getClient().getNumTelephone(), "L'intervention que vous avez demandé est accomplie." + descriptionEmp);
            } else {
                demande.setStatut(DemandeIntervention.Statut.FINIE_ECHEC);
                Message.envoyerNotification(demande.getClient().getNumTelephone(), "L'intervention que vous avez demandé a échoué. Veuillez nous recontacter pour plus d'informations." + descriptionEmp);
            }
            emp.setEstDisponible(true);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
    public static Personne authentifier(String adresseMail, String motDePasse, boolean estEmploye){
        JpaUtil.creerEntityManager();
        Personne utilisateurCourant = null;
        String jpql = null;
        if (estEmploye){
            jpql = "select e from Employe e where e.adresseMail = :adresseMail and e.motDePasse = :motDePasse";
            utilisateurCourant = (Employe) utilisateurCourant;
        }
        else {
            jpql = "select c from Client c where c.adresseMail = :adresseMail and c.motDePasse = :motDePasse";
            utilisateurCourant = (Client) utilisateurCourant;
        }
        JpaUtil.ouvrirTransaction();
        
        try {
            utilisateurCourant = PersonneDAO.verifUtilisateur(jpql, adresseMail, motDePasse);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        
        return utilisateurCourant;
    }
    
}
