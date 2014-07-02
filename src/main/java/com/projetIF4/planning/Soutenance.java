/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetIF4.planning;

import com.projetIF4.model.Pfa;
import com.projetIF4.model.Pfe;
import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author Moez-pc
 */
public class Soutenance extends DefaultScheduleEvent implements Serializable {

    private Pfa pfa;
    private Pfe pfe;

    /**
     *
     */
    public Soutenance() {
    }

    /**
     *
     * @param pfe
     * @param start
     * @param end
     */
    public Soutenance(Pfe pfe, Date start, Date end) {
        super(pfe.getNomprojet(), start, end);
        this.pfa = null;
        this.pfe = pfe;
    }

    /**
     *
     * @param title
     * @param start
     * @param end
     */
    public Soutenance(String title, Date start, Date end) {
        super(title, start, end);
    }

    /**
     *
     * @param pfa
     * @param start
     * @param end
     */
    public Soutenance(Pfa pfa, Date start, Date end) {
        super(pfa.getNomprojet(), start, end);
        this.pfa = pfa;
        this.pfe = null;
    }

    /**
     *
     * @return
     */
    public Pfa getPfa() {
        return pfa;
    }

    /**
     *
     * @param pfa
     */
    public void setPfa(Pfa pfa) {
        this.pfa = pfa;
    }

    /**
     *
     * @return
     */
    public Pfe getPfe() {
        return pfe;
    }

    /**
     *
     * @param pfe
     */
    public void setPfe(Pfe pfe) {
        this.pfe = pfe;
    }

}
