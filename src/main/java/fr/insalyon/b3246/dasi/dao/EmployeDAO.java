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
import javax.persistence.Query;

/**
 *
 * @author Louis Ung
 */
public class EmployeDAO {
    
    public static void modifierDisponibiliteEmploye(Employe emp) {
        emp.setEstDisponible(!emp.isEstDisponible());
    }
    
    public static List<Employe> rechercheEmployeDispo(LatLng adresseClient, Integer heureDemande, String jpql){
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("heureDemande", heureDemande);
        List<Employe> resultat = (List<Employe>) query.getResultList();
        return resultat;
    }

    
}
