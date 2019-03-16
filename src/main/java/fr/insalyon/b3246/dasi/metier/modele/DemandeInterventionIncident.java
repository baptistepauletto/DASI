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
public class DemandeInterventionIncident extends DemandeIntervention {

    public DemandeInterventionIncident(Date dateDeb, String descriptionClient) {
        super(dateDeb, descriptionClient);
    }
    
    public DemandeInterventionIncident() {
    }
    
}
