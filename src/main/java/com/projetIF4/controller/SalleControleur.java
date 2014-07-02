
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.projetIF4.controller;

//~--- non-JDK imports --------------------------------------------------------

import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Salle;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Satellite
 */
@ViewScoped
@ManagedBean
public class SalleControleur implements Serializable{
    private Salle       salle;
    private List<Salle> salles;
    private List<Salle> filtredSalles;
    
    
    private Salle newSalle;

    /**
     *
     */
    @PostConstruct
    public void init() {
        salles = getAllSalles();
        salle  = new Salle();
    }
    
    public void prepareCreate(){
        newSalle =new Salle();
    }
    
    public void afficheMsgAnnuler(){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Opération annulée"));    
    }
       
    public void annuler(){
        salles = getAllSalles();
        afficheMsgAnnuler();
    }
    
     public void add(){
        
        if(newSalle.save()){            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "ajout avec succes "));
            RequestContext.getCurrentInstance().execute("PF('dlgadd').hide();");
            salles = getAllSalles();        
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout de la Salle a échoué !"));        
        }
    }
      
      public void edit(){
         
         if(newSalle.update()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "La modification de Salle a été effectuée avec succès"));
            RequestContext.getCurrentInstance().execute("PF('dlgEdit').hide();");
                   
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "La modification de Salle a échoué !"));        
        }
         salles = getAllSalles();     
     }

    
    
    
    public Salle getSalle() {
        return salle;
    }

    /**
     *
     * @param salle
     */
    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    /**
     *
     * @return
     */
    public List<Salle> getFiltredSalles() {
        return filtredSalles;
    }

    /**
     *
     * @param filtredSalles
     */
    public void setFiltredSalles(List<Salle> filtredSalles) {
        this.filtredSalles = filtredSalles;
    }

    /**
     *
     * @return
     */
    public List<Salle> getSalles() {
        return salles;
    }

    /**
     *
     * @param salles
     */
    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }

    /**
     *
     * @return
     */
    public static List<Salle> getAllSalles() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Salle> salles = (List<Salle>) session.createQuery("from Salle").list();

                return salles;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     *
     */
    public void ajouter() {
        try {
            salle.save();
            salles.add(salle);
            salle = new Salle();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de la salle avec succes !"));
            RequestContext.getCurrentInstance().execute("dlg.hide();");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout de la salle a échoué !" + ex));

            throw ex;
        }
    }

    /**
     *
     * @param s
     */
    public void supprimer(Salle s) {
        if (s.delete()) {
            salles.remove(s);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Suppression de la salle avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Suppression de la salle a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Salle s = (Salle) event.getObject();

        if (s.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise à jour de la salle avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mise à jour de la salle a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void cancelUpdate(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Annulation", "Mise à jour de la salle annulée !"));
    }

    public Salle getNewSalle() {
        return newSalle;
    }

    public void setNewSalle(Salle newSalle) {
        this.newSalle = newSalle;
    }
    
    
}
