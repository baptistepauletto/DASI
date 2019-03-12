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
import javax.persistence.Transient;

/**
 *
 * @author bpauletto
 */
@Entity
public class Employe extends Personne{
    @Transient
    private boolean estDisponible;
    
    private Integer heureDebTravail;
    private Integer heureFinTravail;

    public Employe(Integer heureDebTravail, Integer heureFinTravail, String nom, String prenom, String civilite, Date date, String adresse, String ville, String numTelephone, String adresseMail, String motDePasse) {
        super(nom, prenom, civilite, date, adresse, ville, numTelephone, adresseMail, motDePasse);
        this.heureDebTravail = heureDebTravail;
        this.heureFinTravail = heureFinTravail;
        this.estDisponible = true;
    }

    public Employe(String nom, String prenom, String civilite, Date date, String adresse, String numTelephone, String adresseMail) {
        super(nom, prenom, civilite, date, adresse, numTelephone, adresseMail);
    }


    public Employe(String nom, String adresseMail, String motDePasse) {
        super(nom, adresseMail, motDePasse);
    }

    public Employe(String nom) {
        super(nom);
    }
    
    public Employe() {
    }
    
}
