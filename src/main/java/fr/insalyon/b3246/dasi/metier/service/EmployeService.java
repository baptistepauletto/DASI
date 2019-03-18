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
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention.Statut;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import fr.insalyon.b3246.dasi.util.GeoTest;
import fr.insalyon.b3246.dasi.util.Message;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bpauletto
 */
public class EmployeService {

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

}
