/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import com.google.maps.model.LatLng;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.util.GeoTest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

/**
 *
 * @author Louis Ung
 */
public class EmployeDAO {
    
     public static Employe verifUtilisateur(String adresseMail, String motDePasse){
        String jpql = "select e from Employe e where e.adresseMail = :adresseMail and e.motDePasse = :motDePasse";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("adresseMail", adresseMail);
        query.setParameter("motDePasse", motDePasse);
        Employe resultat = (Employe) query.getSingleResult();
        return resultat;
    }
     
    public static void modifierDisponibiliteEmploye(Employe emp) {
        emp.setEstDisponible(!emp.isEstDisponible());
    }
    
    public static List<Employe> rechercheEmployeDispo(LatLng adresseClient, Integer heureDemande) throws OptimisticLockException{
        String jpql = "select e from Employe e where e.estDisponible = true and e.heureDebTravail <= :heureDemande and e.heureFinTravail > :heureDemande";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("heureDemande", heureDemande);
        List<Employe> resultat = (List<Employe>) query.getResultList();
        if(!resultat.isEmpty()){ // Si au moins un employé est disponible :
            em.lock(resultat.get(0), LockModeType.OPTIMISTIC); // Verrouillage de la première entité pour empêcher les accès concurrents
        }   
        return resultat;
    }
    
    public static Employe trouver(Integer id){
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.find(Employe.class, id);
    }

    public static List<Employe> trouverTous(){
        EntityManager em = JpaUtil.obtenirEntityManager();
        String jpql = "select e from Employe e";
        Query query = em.createQuery(jpql);
        List<Employe> resultat = (List<Employe>) query.getResultList();
        return resultat;
    }
}
