/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.vue;

import fr.insalyon.b3246.dasi.dao.InitDonnees;
import fr.insalyon.b3246.dasi.dao.JpaUtil;
import fr.insalyon.b3246.dasi.metier.modele.Client;
import fr.insalyon.b3246.dasi.metier.modele.DemandeIntervention;
import fr.insalyon.b3246.dasi.metier.modele.DemandeInterventionAnimal;
import fr.insalyon.b3246.dasi.metier.modele.DemandeInterventionIncident;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import fr.insalyon.b3246.dasi.metier.service.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        
        String naiss = "2000-02-16";
        Date dateNaiss = df.parse(naiss); 
        Client client = new Client("Bro", "Mario", "Monsieur", dateNaiss, "40 rue de Bruxelles, Villeurbanne", "0601545864", "agb@d", "root");
        Service.inscrireClient(client);
        
        Date dateNaiss2 = df.parse(naiss); 
        Client client2 = new Client("Bro", "Luigi", "Monsieur", dateNaiss2, "20 avenue Albert Einstein, Villeurbanne", "0601545863", "agy@d", "root");
        Service.inscrireClient(client2);
        
        /*Employe employe = new Employe("Gelus", "abc@mail", "bcd");
        EmployeService.inscrireEmploye(employe);*/
        
        DemandeInterventionAnimal DIA = new DemandeInterventionAnimal("panda", "blabla");
        Service.faireDemandeIntervention(DIA, client);
        
        DemandeInterventionAnimal DIA2 = new DemandeInterventionAnimal("ours", "blabla");
        Service.faireDemandeIntervention(DIA2, client);
        
        DemandeInterventionIncident DII = new DemandeInterventionIncident("blabla");
        Service.faireDemandeIntervention(DII, client2);
        
        Employe e = Service.authentifierEmploye("nolmeadamarais1551@gmail.com", "root");
        //System.out.println("e : "+e.getAdresseMail());
        /*Client c = (Client) PersonneService.authentifier("agb@d", "root", false);
        System.out.println("c : "+c.getAdresseMail());*/
        
        DemandeInterventionAnimal d = (DemandeInterventionAnimal) Service.interventionEmployeEnCours(e);
        System.out.println(d.getAnimal());
        
        Service.cloreDemandeIntervention(e, true, "RAS");

        List <DemandeIntervention> historique = Service.historiqueClient(client);
        System.out.println(historique.get(0).getId()+" "+historique.get(1).getId());
        
        JpaUtil.destroy();
     }
}
