/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.vue;

import fr.insalyon.b3246.dasi.dao.InitDonnees;
import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.DemandeInterventionAnimal;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import fr.insalyon.b3246.dasi.metier.service.ClientService;
import fr.insalyon.b3246.dasi.metier.service.DemandeInterventionService;
import fr.insalyon.b3246.dasi.metier.service.EmployeService;
import fr.insalyon.b3246.dasi.metier.service.PersonneService;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author bpauletto
 */
public class Main {
    
     public static void main(String[] args) throws IOException {
        JpaUtil.init();
        
        Client client = new Client("Chungus Jr.","a@b.com","abc");
        ClientService.inscrireClient(client);
        
        Employe employe = new Employe("Gelus", "abc@mail", "bcd");
        EmployeService.inscrireEmploye(employe);
        
        DemandeInterventionAnimal DIA = new DemandeInterventionAnimal();
        DemandeInterventionService.ajouterDemandeInvervention(DIA);

        /*Personne a = PersonneService.authentifier("a@b.com", "abc");
        System.out.println(a.getNom());*/
        
        InitDonnees.peuplementBase();
        
        JpaUtil.destroy();
     }
}
