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
    private enum Statut {EN_ATTENTE,EN_COURS,FINIE};
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeb;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    
    private String descriptionClient;
    private String descriptionEmployé;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Employe employe;

    public DemandeIntervention(Integer id, Date dateDeb, Date dateFin, String descriptionClient, String descriptionEmployé) {
        this.id = id;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.descriptionClient = descriptionClient;
        this.descriptionEmployé = descriptionEmployé;
    }

    public DemandeIntervention() {
    }
    
    
}
