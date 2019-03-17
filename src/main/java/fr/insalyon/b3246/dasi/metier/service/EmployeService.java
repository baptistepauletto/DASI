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
import fr.insalyon.b3246.dasi.util.Message;
import java.util.Date;

/**
 *
 * @author bpauletto
 */
public class EmployeService {
    
    public static void inscrireEmploye(Employe emp){
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
    
    public static Employe rechercheEmployeDispo(LatLng coordClient, Integer heureDemande){
        JpaUtil.creerEntityManager();
        Employe resultat = null;
        JpaUtil.ouvrirTransaction();
        
        try {
            resultat = EmployeDAO.rechercheEmployeDispo(coordClient, heureDemande);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        return resultat;
    }
    
    public static void cloreDemandeIntervention (Employe emp, boolean succes, String descriptionEmp){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            DemandeInterventionDAO.cloreDemandeIntervention(emp, succes, descriptionEmp);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }        
    }
    
}
