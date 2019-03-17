/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.modele;

import com.google.maps.model.LatLng;
import fr.insalyon.b3246.dasi.util.GeoTest;
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
    private String ville;
    private String numTelephone;
    private String adresseMail;
    private String motDePasse;
    
    //Hypothèse : l'employé part toujours de chez lui
    private LatLng coords;

    public Personne(String nom, String prenom, String civilite, Date date, String adresse, String ville, String numTelephone, String adresseMail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.date = date;
        this.adresse = adresse;
        this.ville = ville;
        this.numTelephone = numTelephone;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.coords = GeoTest.getLatLng(this.adresse);
    }

    public Personne(String nom, String prenom, String civilite, Date date, String adresse, String numTelephone, String adresseMail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.date = date;
        this.adresse = adresse;
        this.numTelephone = numTelephone;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.coords = GeoTest.getLatLng(this.adresse);

    }

    public LatLng getCoords() {
        return coords;
    }

    public void setCoords(LatLng coords) {
        this.coords = coords;
    }


    public Personne(String nom, String adresseMail, String motDePasse) {
        this.nom = nom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCivilite() {
        return civilite;
    }

    public Date getDate() {
        return date;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    
    
    public Personne(String nom) {
        this.nom = nom;
    }

    public Personne() {
    }
    
    
    
    
    
    
}
