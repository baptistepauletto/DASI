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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author bpauletto
 */
public class Main {
    
     public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        JpaUtil.init();
        InitDonnees.peuplementBase();
        
        String naiss = "2000-02-16";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaiss = df.parse(naiss); 
        Client client = new Client("Bro", "Mario", "Monsieur", dateNaiss, "40 rue de Bruxelles, Villeurbanne", "0601545864", "agb@d", "root");
        ClientService.inscrireClient(client);
        
        Employe employe = new Employe("Gelus", "abc@mail", "bcd");
        EmployeService.inscrireEmploye(employe);
        
        DemandeInterventionAnimal DIA = new DemandeInterventionAnimal("panda", "blabla");
        ClientService.faireDemandeIntervention(DIA, client);
        
        /*
        String debut = "2019-02-16";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDeb = df.parse(debut);        
        DemandeInterventionAnimal DIA = new DemandeInterventionAnimal("panda", dateDeb, "blabla");
        DemandeInterventionService.ajouterDemandeInvervention(DIA);*/

        /*Personne a = PersonneService.authentifier("a@b.com", "abc");
        System.out.println(a.getNom());*/
                
        JpaUtil.destroy();
     }
}
