package com.projetIF4.controller;


import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Section;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
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
public class SectionControleur implements Serializable{

    private Section       section;
    private List<Section> sections;
    private List<Section> filtredSections;
    
     private Section newSection;

    /**
     *
     */
    @PostConstruct
    public void init() {
        sections = getAllSection();
        section  = new Section();
    }
    
    
    public void prepareCreate(){
        newSection =new Section();
    }
    
    public void afficheMsgAnnuler(){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Opération annulée"));    
    }
    public void annuler(){
        sections = getAllSection();
        afficheMsgAnnuler();
    }
    
    public void add(){
        
        if(newSection.save()){            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "ajout avec succes "));
            sections = getAllSection();
            RequestContext.getCurrentInstance().execute("PF('dlgadd').hide();");                    
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout de la section a échoué !"));        
        }
    }
    
    
     public void edit(){
         
         if(newSection.update()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "La modification de la Section a été effectuée avec succès"));
            RequestContext.getCurrentInstance().execute("PF('dlgEdit').hide();");
                   
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "La modification de la section a échoué !"));        
        }
         sections = getAllSection();     
     }

    /**
     *
     * @return
     */
    public Section getSection() {
        return section;
    }

    /**
     *
     * @param section
     */
    public void setSection(Section section) {
        this.section = section;
    }

    /**
     *
     * @return
     */
    public List<Section> getFiltredSections() {
        return filtredSections;
    }

    /**
     *
     * @param filtredSections
     */
    public void setFiltredSections(List<Section> filtredSections) {
        this.filtredSections = filtredSections;
    }

    /**
     *
     * @return
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     *
     * @param sections
     */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    /**
     *
     * @param id
     * @return
     */
    public static Section getSpecialite(Integer id) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Section g = (Section) session.load(Section.class, id);

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
    public static List<Section> getAllSection() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Section> sections = (List<Section>) session.createQuery("from Section").list();

                return sections;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public static List<Section> getAllSectionTerminal(Boolean val) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Query q = session.createQuery("from Section u WHERE  u.terminal = :val");
                q.setParameter("val", val);
                List<Section> sections = q.list();
                return sections;
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
            newSection.save();
            sections.add(section);
            section = new Section();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de la section avec succes !"));
            RequestContext.getCurrentInstance().execute("dlg.hide();");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout de la section a échoué !" + ex));

            throw ex;
        }
    }

    /**
     *
     * @param s
     */
    public void supprimer(Section s) {
        if (s.delete()) {
            sections.remove(s);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Suppression de la section avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Suppression de la section a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Section sec = (Section) event.getObject();

        if (sec.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                                     "Mise à jour de la section avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mise à jour de la section a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void cancelUpdate(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise à jour de la section annulée !"));
    }

    public Section getNewSection() {
        return newSection;
    }

    public void setNewSection(Section newSection) {
        this.newSection = newSection;
    }
    
    
}