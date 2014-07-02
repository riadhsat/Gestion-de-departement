
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetIF4.controller;

//~--- JDK imports ------------------------------------------------------------
import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Enseignant;
import com.projetIF4.model.Pfe;
import com.projetIF4.model.Salle;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Satellite
 */
@ViewScoped
@ManagedBean
public class PfeControleur implements Serializable {

    private Pfe selected;
    private Pfe newpfe;
    private List<Pfe> pfes;

    public PfeControleur() {
    }
    
    @PostConstruct
    public void init(){
        pfes = getPFENonPlanifies();
        pfes.addAll(getPFEPlanifies());    
    }
    
    public void prepareCreate(){
        newpfe=new Pfe();
    
    }
    
    public void ajouter(){
        newpfe.save();
        pfes.add(newpfe);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Projet ajouté avec succès"));
        System.err.println("code :"+newpfe.getCodepfe());
    }
    
    public void supprimer(){
        pfes.remove(selected);
        delete(selected);
        selected=null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Projet supprimé avec succès"));
        System.err.println("delete");
    
    }
    public void modifier(){        
        selected.update();
        selected=null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Projet modifié avec succès"));
        System.err.println("modifier");    
    }


    
    public void delete(Pfe pfe){
    final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
               session.delete(pfe);
               transaction.commit();
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
    /**
     *
     * @param codePfe
     * @return
     */
    public static Pfe getPfe(int codePfe) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Pfe pfe = (Pfe) session.get(Pfe.class, codePfe);

                return pfe;
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
    public static List<Pfe> getPFENonPlanifies() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list = (List<Pfe>) session.createQuery("from Pfe where datesoutenance is null").list();

                return list;
            } catch (HibernateException ex) {
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
    public static List<Pfe> getPFEPlanifies() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list = (List<Pfe>) session.createQuery("from Pfe where datesoutenance is not null").list();
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public static List<Pfe> getListPfeBySalle(Salle salle){
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list;
                Query q;
                if(salle!=null){
                q = session.createQuery("from Pfe u WHERE salle = :salle AND datesoutenance is not null ORDER BY u.datesoutenance ASC");
                q.setParameter("salle", salle);
                 list = q.list();}
                else
                {list = (List<Pfe>) session.createQuery("from Pfe u where datesoutenance is not null ORDER BY u.datesoutenance ASC").list();}
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }   
    }
    public static List<Pfe> getListPfeByPresident(Enseignant enseignant){
    final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list;
                Query q;
                if(enseignant!=null){
                q = session.createQuery("from Pfe u WHERE chefjury = :enseignant AND datesoutenance is not null ORDER BY u.datesoutenance ASC");
                q.setParameter("enseignant", enseignant);
                 list = q.list();}
                else
                {list = null;}
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    
    }
    
    public static List<Pfe> getListPfeByRapporteur(Enseignant enseignant){
    final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list;
                Query q;
                if(enseignant!=null){
                q = session.createQuery("from Pfe u WHERE rapporteur = :enseignant AND datesoutenance is not null ORDER BY u.datesoutenance ASC");
                q.setParameter("enseignant", enseignant);
                 list = q.list();}
                else
                {list = null;}
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }    
    }
    public static List<Pfe> getListPfeByEncadreur(Enseignant enseignant){
    final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list;
                Query q;
                if(enseignant!=null){
                q = session.createQuery("from Pfe u WHERE encadreur = :enseignant AND datesoutenance is not null ORDER BY u.datesoutenance ASC");
                q.setParameter("enseignant", enseignant);
                 list = q.list();}
                else
                {list = null;}
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }    
    }
    
    public static List<Pfe> getPFEAffectes() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list = (List<Pfe>) session.createQuery("from Pfe where etudiantByEtuCin is not null").list();
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
     public static List<Pfe> getPFENonAffectes() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfe> list = (List<Pfe>) session.createQuery("from Pfe where etudiantByEtuCin is null and etudiantByEtuCin1 is null").list();
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public List<Pfe> getPfes() {
        return pfes;
    }

    public void setPfes(List<Pfe> pfes) {
        this.pfes = pfes;
    }

    public Pfe getNewpfe() {
        return newpfe;
    }

    public void setNewpfe(Pfe newpfe) {
        this.newpfe = newpfe;
    }

    public Pfe getSelected() {
        return selected;
    }

    public void setSelected(Pfe selected) {
        this.selected = selected;
    }
    
}
