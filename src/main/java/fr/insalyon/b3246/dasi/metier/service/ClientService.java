/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.service;

import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.dao.PersonneDAO;
import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.util.Message;
import javax.persistence.EntityManager;

/**
 *
 * @author bpauletto
 */
public class ClientService {
    
    public static void inscrireClient(Client c){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            PersonneDAO.persister(c);
            JpaUtil.validerTransaction();
            Message.envoyerMail("noreply@proactif.com", c.getAdresseMail(), "Confirmation d'inscription sur Proact'if", "L'équipe Proact'if vous confirme votre inscription s'est bien déroulée.");
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            Message.envoyerMail("noreply@proactif.com", c.getAdresseMail(), "Echer de l'inscription sur Proact'if", "Un problème a eu lieu lors de l'inscription sur Proact'if. Veuillez réessayer ultérieurement.");
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
}
