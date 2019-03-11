/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import fr.insalyon.b3246.dasi.dao.DemandeInterventionDAO;
import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;

/**
 *
 * @author bpauletto
 */
public class DemandeInterventionService {
    
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
}
