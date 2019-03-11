/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author bpauletto
 */
@Entity
public class Client extends Personne {

    public Client(String nom, String prenom, String civilite, Date date, String adresse, Integer codePostal, String ville, String numTelephone, String adresseMail, String motDePasse) {
        super(nom, prenom, civilite, date, adresse, codePostal, ville, numTelephone, adresseMail, motDePasse);
    }

    public Client(String nom, String adresseMail, String motDePasse) {
        super(nom, adresseMail, motDePasse);
    }
    
    public Client(String nom) {
        super(nom);
    }

    public Client() {
    }
    
    
}
