package com.projetIF4.controller;


import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Etudiant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Satellite
 */
@ViewScoped
@ManagedBean
public class EtudiantControleur implements Serializable {
    private Etudiant       etudiant;
    private List<Etudiant> etudiants;
    private List<Etudiant> filtredEtudiants;

    /**
     *
     */
    @PostConstruct
    public void init() {
        etudiants = EtudiantControleur.getAllEtudiant();
        etudiant  = new Etudiant();
    }
    public void updateInformation() {
        etudiants = EtudiantControleur.getAllEtudiant();
        etudiant  = new Etudiant();        
    }
    

    /**
     *
     * @return
     */
    public Etudiant getEtudiant() {
        return etudiant;
    }

    /**
     *
     * @param etudiant
     */
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    /**
     *
     * @return
     */
    public List<Etudiant> getFiltredEtudiants() {
        return filtredEtudiants;
    }

    /**
     *
     * @param filtredEtudiants
     */
    public void setFiltredEtudiants(List<Etudiant> filtredEtudiants) {
        this.filtredEtudiants = filtredEtudiants;
    }

    /**
     *
     * @return
     */
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    /**
     *
     * @param etudiants
     */
    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    /**
     *
     * @param i
     * @return
     */
    public static Etudiant GetEtudiant(Integer i) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Etudiant e = (Etudiant) session.load(Etudiant.class, i);

                return e;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     *
     * @param i
     * @return
     */
    public static boolean EtudiantExiste(Integer i) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Etudiant e = (Etudiant) session.get(Etudiant.class, i);

                return e != null;
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
    public static List<Etudiant> getAllEtudiant() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Etudiant> etudiant = (List<Etudiant>) session.createQuery("from Etudiant").list();

                return etudiant;
            } catch (HibernateException ex) {
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
            etudiant.save();
            etudiants.add(etudiant);
            etudiant = new Etudiant();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de l'étudiant avec succes !"));
            RequestContext.getCurrentInstance().execute("PF('dlg').hide();");
        } catch (ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'étudiant existe déja !"));

            throw e;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                                     "L'ajout de l'étudiant a échoué !" + ex));
            throw ex;
        }
    }

    /**
     *
     * @param e
     */
    public void supprimer(Etudiant e) {
        if (e.delete()) {
            etudiants.remove(e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Succès de Suppression de l'étudiant "));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Suppression de l'étudiant a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Etudiant etud = (Etudiant) event.getObject();

        if (etud.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                                     "Mise a jour de l'étudiant avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mise à jour de étudiant a échoué !"));
        }
    }

    /**
     *
     * @param event
     */
    public void cancelUpdate(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Annulation", "Mise a jour de l'étudiant annulée !"));
    }
}
