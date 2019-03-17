/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import com.google.maps.model.LatLng;
import fr.insalyon.b3246.dasi.dao.EmployeDAO;
import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.dao.PersonneDAO;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;

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
        if (resultat != null){
            PersonneService.persister(resultat);
        }
        return resultat;
    }
    
}
