/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import com.google.maps.model.LatLng;
import fr.insalyon.b3246.dasi.dao.ClientDAO;
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
            Message.envoyerMail("noreply@proactif.com", c.getAdresseMail(), "Echec de l'inscription sur Proact'if", "Un problème a eu lieu lors de l'inscription sur Proact'if. Veuillez réessayer ultérieurement.");
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
            resultat = EmployeDAO.rechercheEmployeDispo(coordClient, heureDemande);  
            // Recherche de l'employe le plus proche geographiquement du client
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

    // Cette méthode existe uniquement pour tester le fonctionnement correct de la base de données
    // sinon elle ne doit pas être utilisée en tant que service pour les employés
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

        DemandeIntervention demande = null;
        Employe EMP = EmployeDAO.trouver(emp.getId());
        try {
            demande = DemandeInterventionDAO.interventionEmployeEnCours(EMP);
            demande.setDateFin(new Date());
            if (succes) {
                demande.setStatut(DemandeIntervention.Statut.FINIE_SUCCES);
                demande.setDescriptionEmploye(descriptionEmp);
                Message.envoyerNotification(demande.getClient().getNumTelephone(), "L'intervention que vous avez demandé est accomplie. " + descriptionEmp);
            } else {
                demande.setStatut(DemandeIntervention.Statut.FINIE_ECHEC);
                Message.envoyerNotification(demande.getClient().getNumTelephone(), "L'intervention que vous avez demandé a échoué. Veuillez nous recontacter pour plus d'informations." + descriptionEmp);
            }
            EMP.setEstDisponible(true);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static Employe authentifierEmploye(String adresseMail, String motDePasse) {
        JpaUtil.creerEntityManager();
        Employe utilisateurCourant = null;

        JpaUtil.ouvrirTransaction();

        try {
            utilisateurCourant = EmployeDAO.verifUtilisateur(adresseMail, motDePasse);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        return utilisateurCourant;
    }

    public static Client authentifierClient(String adresseMail, String motDePasse) {
        JpaUtil.creerEntityManager();
        Client utilisateurCourant = null;

        JpaUtil.ouvrirTransaction();

        try {
            utilisateurCourant = ClientDAO.verifUtilisateur(adresseMail, motDePasse);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        return utilisateurCourant;
    }

    public static DemandeIntervention interventionEmployeEnCours(Employe emp) {
        JpaUtil.creerEntityManager();
        DemandeIntervention intervention = null;

        JpaUtil.ouvrirTransaction();

        try {
            intervention = DemandeInterventionDAO.interventionEmployeEnCours(emp);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        return intervention;
    }

    public static List<DemandeIntervention> historiqueClient(Client c) {
        JpaUtil.creerEntityManager();
        List<DemandeIntervention> intervention = null;

        JpaUtil.ouvrirTransaction();

        try {
            intervention = DemandeInterventionDAO.historiqueClient(c);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        return intervention;
    }
    
    
    public static List <DemandeIntervention> tableauDeBordEmploye (){
        JpaUtil.creerEntityManager();
        List <DemandeIntervention> intervention = null;
        JpaUtil.ouvrirTransaction();  
         try {
            intervention = DemandeInterventionDAO.tableauDeBordEmploye();
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return intervention;
    }
    
}
