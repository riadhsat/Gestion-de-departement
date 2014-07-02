
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetIF4.controller;

//~--- non-JDK imports --------------------------------------------------------
import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Specialite;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Satellite
 */
@ViewScoped
@ManagedBean
public class SpecialiteControleur implements Serializable {

    private Specialite specialite;
    private List<Specialite> specialites;
    private List<Specialite> filtredSpecialites;
    private List<Specialite> specialitesAjoutees;
    
    private Specialite  newspecialite;

    public SpecialiteControleur() {
    }
    
    
    
    

    /**
     *
     */
    @PostConstruct
    public void init() {
        specialites = SpecialiteControleur.getAllSpecialite();
        specialitesAjoutees = new ArrayList<>();
        specialitesAjoutees.add(new Specialite());
        specialite = new Specialite();
    }
    
    public void prepareCreate(){
        newspecialite=new Specialite();
                
    }
    
    public void afficheMsgAnnuler(){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Opération annulée"));    
    }
       
    public void annuler(){
        specialites = getAllSpecialite();
        afficheMsgAnnuler();
    }
    
    
     public void add(){
        
        if(newspecialite.save()){            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "ajout avec succes "));
            RequestContext.getCurrentInstance().execute("PF('dlgadd').hide();");
            
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout de la specialité a échoué !"));        
        }
        specialites=getAllSpecialite();
    }
      
      public void edit(){
         
         if(newspecialite.update()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "La modification de specialite a été effectuée avec succès"));
            RequestContext.getCurrentInstance().execute("PF('dlgEdit').hide();");
                   
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "La modification de specialité a échoué !"));        
        }
         specialites = getAllSpecialite();     
     }

    /**
     *
     * @return
     */
    public List<Specialite> getSpecialitesAjoutees() {
        return specialitesAjoutees;
    }

    /**
     *
     * @param specialitesAjoutees
     */
    public void setSpecialitesAjoutees(List<Specialite> specialitesAjoutees) {
        this.specialitesAjoutees = specialitesAjoutees;
    }

    /**
     *
     * @return
     */
    public List<Specialite> getFiltredSpecialites() {
        return filtredSpecialites;
    }

    /**
     *
     * @param filtredSpecialites
     */
    public void setFiltredSpecialites(List<Specialite> filtredSpecialites) {
        this.filtredSpecialites = filtredSpecialites;
    }

    /**
     *
     * @return
     */
    public List<Specialite> getSpecialites() {
        return specialites;
    }

    /**
     *
     * @param specialites
     */
    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }

    /**
     *
     * @return
     */
    public Specialite getSpecialite() {
        return specialite;
    }

    /**
     *
     * @param specialite
     */
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    /**
     *
     * @param id
     * @return
     */
    public static Specialite getSpecialite(Integer id) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Specialite g = (Specialite) session.load(Specialite.class, id);

                return g;
            } catch (Exception ex) {

                // Log the exception here
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     *
     * @return
     */
    public static List<Specialite> getAllSpecialite() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Specialite> specialites = (List<Specialite>) session.createQuery("from Specialite").list();

                return specialites;
            } catch (Exception ex) {

                // Log the exception here
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
            specialite.save();
            specialites.add(specialite);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de la spécialité avec succes !"));
            specialite = new Specialite();
            RequestContext.getCurrentInstance().execute("dlg.hide()");
        } catch (ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "La spécialité existe déjà !"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "L'ajout de la spécialité a échoué !" + ex));

            throw ex;
        }
    }

    /**
     *
     * @param s
     */
    public void supprimer(Specialite s) {
        if (s.delete()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                            "Suppression de la spécialité avec succes !"));
            specialites=getAllSpecialite();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "Suppression de la spécialité a échoué !"));
        }
    }

    /**
     *
     */
    public void ajouterChampTable() {
        specialitesAjoutees.add(new Specialite());
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Specialite spe = (Specialite) event.getObject();

        if (spe.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                            "Mise a jour de la spécialité avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "Mise à jour de la spécialité a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void cancelUpdate(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise a jour de la spécialité annulée !"));
    }

    public Specialite getNewspecialite() {
        return newspecialite;
    }

    public void setNewspecialite(Specialite newspecialite) {
        this.newspecialite = newspecialite;
    }
    
    
}
