/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.dao.PersonneDAO;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import javax.persistence.Query;

/**
 *
 * @author bpauletto
 */
public class PersonneService {
    
    public static Personne authentifier(String adresseMail, String motDePasse){
        JpaUtil.creerEntityManager();
        String jpql = "select p from Personne p where p.adresseMail = :adresseMail and p.motDePasse = :motDePasse";
        JpaUtil.ouvrirTransaction();
        Personne utilisateurCourant = null;
        
        try {
            utilisateurCourant = PersonneDAO.verifUtilisateur(jpql, adresseMail, motDePasse);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        
        if (utilisateurCourant != null){
            return utilisateurCourant;
        }
        else {
            return null;
        }
    }
}
