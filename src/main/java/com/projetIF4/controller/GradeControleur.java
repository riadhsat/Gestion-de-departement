package com.projetIF4.controller;

import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Grade;
import java.io.Serializable;
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
public class GradeControleur implements Serializable{
    private Grade       grade;
    private List<Grade> grades;
    private List<Grade> filtredGrades;
    
    private Grade newGrade;

    
    @PostConstruct
    public void init() {
        grades = getAllGrade();
        grade  = new Grade();
    }
    
     public void prepareCreate(){
        newGrade =new Grade();
    }
     
      public void afficheMsgAnnuler(){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Opération annulée"));    
    }
       
    public void annuler(){
        grades = getAllGrade();
        afficheMsgAnnuler();
    }
      
      public void add(){
        
        if(newGrade.save()){            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "ajout avec succes "));
            RequestContext.getCurrentInstance().execute("PF('dlgadd').hide();");
            grades = getAllGrade();        
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout de la grade a échoué !"));        
        }
    }
      
      public void edit(){
         
         if(newGrade.update()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "La modification de Grade a été effectuée avec succès"));
            RequestContext.getCurrentInstance().execute("PF('dlgEdit').hide();");
                   
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "La modification de Grade a échoué !"));        
        }
         grades = getAllGrade();     
     }
    

    /**
     *
     * @return
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     *
     * @param grade
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     *
     * @return
     */
    public List<Grade> getFiltredGrades() {
        return filtredGrades;
    }

    /**
     *
     * @param filtredGrades
     */
    public void setFiltredGrades(List<Grade> filtredGrades) {
        this.filtredGrades = filtredGrades;
    }

    /**
     *
     * @return
     */
    public List<Grade> getGrades() {
        return grades;
    }

    /**
     *
     * @param grades
     */
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    /**
     *
     */
    public void ajouter() {
        try {
            grade.save();
            grades.add(grade);
            grade = new Grade();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout du grade avec succes !"));
            RequestContext.getCurrentInstance().execute("dlg.hide();");
        } catch (ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage("messages",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout", "Le grade existe déjà !"));

            throw e;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout du grade a échoué !" + ex));

            throw ex;
        }
    }

    /**
     *
     * @param g
     */
    public void supprimer(Grade g) {
        if (g.delete()) {
            grades=getAllGrade();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Suppression du grade avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Suppression du grade a échoué !"));
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static Grade getGrade(Integer id) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Grade g = (Grade) session.load(Grade.class, id);

                return g;
            } catch (Exception ex) {
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
    public static List<Grade> getAllGrade() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Grade> grades = (List<Grade>) session.createQuery("from Grade").list();

                return grades;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Grade g = (Grade) event.getObject();

        if (g.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise a jour du grade avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mise à jour de grade a échoué !"));
        }
    }

    /**
     *
     */
    public void cancelUpdate() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Annulation", "Mise a jour du grade annulée !"));
    }

    public Grade getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(Grade newGrade) {
        this.newGrade = newGrade;
    }    
    
}
