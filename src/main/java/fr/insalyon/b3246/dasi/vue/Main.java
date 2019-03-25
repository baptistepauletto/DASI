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
import fr.insalyon.b3246.dasi.metier.modele.DemandeInterventionLivraison;
import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.modele.Personne;
import fr.insalyon.b3246.dasi.metier.service.Service;
import fr.insalyon.b3246.dasi.util.DebugLogger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 *
 * @author bpauletto
 */
public class Main {
     
     public static void testUsageClassique() throws ParseException{
        //Fixation du format de date pour les futurs inserts en base de données.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        //Exemple : Inscription de clients
        String naiss = "2000-02-16";
        Date dateNaiss = df.parse(naiss); 
        Client client = new Client("Bro", "Mario", "Monsieur", dateNaiss, "40 rue de Bruxelles, Villeurbanne", "0601545864", "agb@d", "root");
        Service.inscrireClient(client);
        
        Date dateNaiss2 = df.parse(naiss); 
        Client client2 = new Client("Bro", "Luigi", "Monsieur", dateNaiss2, "20 avenue Albert Einstein, Villeurbanne", "0601545863", "agy@d", "root");
        Service.inscrireClient(client2);
        
        //Exemple : Création de demande d'intervention de type Animal et attribution de cette dernière à un client
        DemandeInterventionAnimal DIA = new DemandeInterventionAnimal("panda", "blabla");
        Service.faireDemandeIntervention(DIA, client);
        
        //Exemple : Création de demande d'intervention de type Livraison et attribution de cette dernière à un client
        DemandeInterventionLivraison DIL = new DemandeInterventionLivraison("chaussures","Laposte","attention fragile");
        Service.faireDemandeIntervention(DIL, client);
        
        //Exemple : Création de demande d'intervention et attribution de cette dernière à un client
        DemandeInterventionIncident DII = new DemandeInterventionIncident("blabla");
        Service.faireDemandeIntervention(DII, client2);
        
        //Exemple: Connexion d'un employé à Proact'if
        Employe e = Service.authentifierEmploye("nolmeadamarais1551@gmail.com", "root");
        DebugLogger.log("Affichage de l'adresse mail de l'employé connecté : " + e.getAdresseMail());
        
        Client c = Service.authentifierClient("agb@d", "root");
        DebugLogger.log("Affichage de l'adresse mail du client connecté : " + c.getAdresseMail());
        
        //Exemple : Récupération de la demande d'intervention qui est en train d'être réalisée par l'employé
        DemandeInterventionAnimal d = (DemandeInterventionAnimal) Service.interventionEmployeEnCours(e);
        DebugLogger.log("Affichage de l'animal lié à cette DIA : " + d.getAnimal());
        
        //Exemple: Clotûre d'une demande d'intervention par un employé, avec l'ajout de son commentaire
        Service.cloreDemandeIntervention(e, true, "Rien à signaler, tout s'est déroulé à merveille");

        //Exemple: Consultation de l'historique lié à un employé
        List <DemandeIntervention> historique = Service.historiqueClient(client);
        DebugLogger.log("Identifiant des deux premières DI de l'historique : " + historique.get(0).getId()+" "+historique.get(1).getId());
        
        //Exemple: Consultation du tableau de bord lié à l'interface employé
        List <DemandeIntervention> tab = Service.tableauDeBordEmploye();
        DebugLogger.log("Identifiant de la première DI du tableau de bord : " + tab.get(0).getId().toString());
     }
     
        public static void testPasEmployeDispo() throws ParseException{
         //Fixation du format de date pour les futurs inserts en base de données.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        //Exemple : Inscription de clients
        String naiss = "2000-02-16";
        Date dateNaiss = df.parse(naiss); 
        Client client = new Client("Bro", "Mario", "Monsieur", dateNaiss, "40 rue de Bruxelles, Villeurbanne", "0601545864", "agb@d", "root");
        Service.inscrireClient(client);
        
         //Exemple : Création de demande d'intervention de type Animal et attribution de cette dernière à un client
        DemandeInterventionAnimal DIA = new DemandeInterventionAnimal("panda", "blabla");
        Service.faireDemandeIntervention(DIA, client);
     }
    
     public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        //Initialisation de l'utilitaire lié à l'utilisation de JPA
        JpaUtil.init();
        
        //utilisation de la méthode statique permettant de peupler notre base d'une quinzaine d'employés
        InitDonnees.peuplementBase();
        
        //Test n°1: Cas d'usage classique :
        testUsageClassique();
        
        //Test n°2: Cas où aucun employé n'est disponible :
        //testPasEmployeDispo(); // Modification heure travail dans InitDonnees
                
        //Destruction de l'utilitaire lié à JPA
        JpaUtil.destroy();
     }
}
