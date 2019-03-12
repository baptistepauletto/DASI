/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b3246.dasi.dao;

import fr.insalyon.b3246.dasi.metier.modele.Employe;
import fr.insalyon.b3246.dasi.metier.service.EmployeService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Louis Ung
 */
public class InitDonnees {

    public static void peuplementBase() throws FileNotFoundException, IOException, ParseException {

        File file = new File("donnees_init.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ligne;
            Integer numPersonne = new Integer(0);
            List<String> params = new ArrayList<String>();

            while ((ligne = br.readLine()) != null) {
                if (ligne.equals("</tr>")) {
                    numPersonne += 1;
                    
                    if (numPersonne <=500){
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date paramDate = df.parse(params.get(3));
                        Employe emp = new Employe(params.get(1), params.get(2),params.get(0),paramDate, params.get(4), params.get(5), params.get(6));
                        EmployeService.inscrireEmploye(emp);
                    }
                    
                    params.clear();
                }
                else if (!ligne.equals("<tr>")){
                    params.add(ligne.substring(ligne.indexOf(">")+1, ligne.indexOf("</td>")-1));
                }
            }
        }
    }
}
