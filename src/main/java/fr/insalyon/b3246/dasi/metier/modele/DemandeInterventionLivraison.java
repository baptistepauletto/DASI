/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.metier.modele;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author bpauletto
 */
@Entity
public class DemandeInterventionLivraison extends DemandeIntervention {
    private String objet;
    private String entreprise;

    public DemandeInterventionLivraison(String objet, String entreprise, Date dateDeb, String descriptionClient) {
        super(dateDeb, descriptionClient);
        this.objet = objet;
        this.entreprise = entreprise;
    }

    public DemandeInterventionLivraison() {
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
    
    
}
