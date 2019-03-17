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
    
    public static Employe rechercheEmployeDispo(LatLng adresseClient, Integer heureDemande){
        String jpql = "select e from Employe e where e.estDisponible = true and e.heureDebTravail <= :heureDemande and e.heureFinTravail > :heureDemande";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery(jpql);
        query.setParameter("heureDemande", heureDemande);
        List<Employe> resultat = (List<Employe>) query.getResultList();
        
        if (resultat.isEmpty()){
            return null;
        }
        
        Employe empRetenu = resultat.get(0);
        Double dureeTrajet = GeoTest.getTripDurationByBicycleInMinute(empRetenu.getCoords(), adresseClient);
        for (int i = 1; i<resultat.size(); i++){
            if (GeoTest.getTripDurationByBicycleInMinute(resultat.get(i).getCoords(), adresseClient) < dureeTrajet){
                dureeTrajet = GeoTest.getTripDurationByBicycleInMinute(resultat.get(i).getCoords(), adresseClient);
                empRetenu = resultat.get(i);
            }
        }
        empRetenu.setEstDisponible(false);
        
        return empRetenu;
    }
}
