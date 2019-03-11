/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bpauletto
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    private String nom;
    private String prenom;
    private String civilite;
    
    @Temporal(TemporalType.DATE)
    private Date date;

    private String adresse;
    private Integer codePostal;
    private String ville;
    private String numTelephone;
    private String adresseMail;
    private String motDePasse;
    //TODO : COORDS GPS

    public Personne(String nom, String prenom, String civilite, Date date, String adresse, Integer codePostal, String ville, String numTelephone, String adresseMail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.date = date;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.numTelephone = numTelephone;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
    }

    public Personne(String nom) {
        this.nom = nom;
    }

    public Personne() {
    }
    
    
    
    
    
    
}
