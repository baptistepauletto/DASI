/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.dao.PersonneDAO;
import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import javax.persistence.Query;

/**
 *
 * @author bpauletto
 */
public class PersonneService {
    
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
    
    public static void persister(Personne p){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            PersonneDAO.persister(p);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }
}
