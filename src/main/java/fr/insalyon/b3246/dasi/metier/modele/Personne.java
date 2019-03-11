/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.modele;

import java.util.Date;

/**
 *
 * @author bpauletto
 */
public abstract class Personne {
    private Integer Id;
    private String nom;
    private String prenom;
    private String civilite;
    private Date date;
    private String adresse;
    private Integer codePostal;
    private String ville;
    private String numTelephone;
    private String adresseMail;
    private String motDePasse;
    //TODO : COORDS GPS
}
