/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.modele;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bpauletto
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DemandeIntervention {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    public enum Statut {REJETEE,EN_COURS,FINIE_SUCCES, FINIE_ECHEC};
    private Statut statut;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeb;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;

    private String descriptionClient;
    private String descriptionEmploye;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Employe employe;

    public DemandeIntervention(Date dateDeb, String descriptionClient) {
        this.dateDeb = dateDeb;
        this.descriptionClient = descriptionClient;
        this.statut = Statut.EN_COURS;
    }
    
    public DemandeIntervention(String descriptionClient) {
        this.dateDeb = new Date();
        this.descriptionClient = descriptionClient;
        this.statut = Statut.EN_COURS;
    }

    public DemandeIntervention() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescriptionClient() {
        return descriptionClient;
    }

    public void setDescriptionClient(String descriptionClient) {
        this.descriptionClient = descriptionClient;
    }

    public String getDescriptionEmploye() {
        return descriptionEmploye;
    }

    public void setDescriptionEmploye(String descriptionEmploye) {
        this.descriptionEmploye = descriptionEmploye;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}
