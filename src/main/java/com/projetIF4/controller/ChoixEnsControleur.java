
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.projetIF4.controller;

//~--- non-JDK imports --------------------------------------------------------

import com.projetIF4.model.Enseignant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Moez-pc
 */
@RequestScoped
@ManagedBean
public class ChoixEnsControleur implements Serializable{
    private int              cinetu;
    private int              cinetu2;
    private int              cinenseignant;
    private int              choix;
    private List<Enseignant> enseignants;

    // pour la vue
    private List<Enseignant> enseignantsChoisis;

    /**
     *
     */
    public ChoixEnsControleur() {
        choix              = 0;
        enseignants        = EnseignantControleur.getAllEnseignant();
        enseignantsChoisis = new ArrayList<>(enseignants.size());

        for (Enseignant enseignant : enseignants) {
            enseignantsChoisis.add(null);
        }
    }

    /**
     *
     * @return
     */
    public List<Enseignant> getEnseignantsChoisis() {
        return enseignantsChoisis;
    }

    /**
     *
     * @param enseignantsChoisis
     */
    public void setEnseignantsChoisis(List<Enseignant> enseignantsChoisis) {
        this.enseignantsChoisis = enseignantsChoisis;
    }

    /**
     *
     * @return
     */
    public int getCinetu() {
        return cinetu;
    }

    /**
     *
     * @param cinetu
     */
    public void setCinetu(int cinetu) {
        this.cinetu = cinetu;
    }

    /**
     *
     * @return
     */
    public int getCinetu2() {
        return cinetu2;
    }

    /**
     *
     * @param cinetu2
     */
    public void setCinetu2(int cinetu2) {
        this.cinetu2 = cinetu2;
    }

    /**
     *
     * @return
     */
    public int getCinenseignant() {
        return cinenseignant;
    }

    /**
     *
     * @param cinenseignant
     */
    public void setCinenseignant(int cinenseignant) {
        this.cinenseignant = cinenseignant;
    }

    /**
     *
     * @return
     */
    public int getChoix() {
        return ++choix;
    }

    /**
     *
     * @param choix
     */
    public void setChoix(int choix) {
        this.choix = choix;
    }

    /**
     *
     * @return
     */
    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    /**
     *
     * @param enseignants
     */
    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    /**
     *
     * @param event
     */
    public void choisirEns(DragDropEvent event) {
        System.out.println("source = " + event.getDragId());
        System.out.println("dest = " + event.getDropId());

        String          s  = event.getDropId();
        StringTokenizer st = new StringTokenizer(s, ":");

        st.nextToken();
        st.nextToken();

        int        i   = Integer.parseInt(st.nextToken());
        Enseignant ens = (Enseignant) event.getData();

        enseignants.remove(ens);
        enseignantsChoisis.set(i, ens);

//      enseignantsChoisis.add(ens);
        System.out.println("longueur de ens" + enseignants);
        System.out.println("longueur de ens choisis " + enseignantsChoisis);
    }
}