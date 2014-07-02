package com.projetIF4.controller;

import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Enseignant;
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

@ViewScoped
@ManagedBean
public class EnseignantControleur implements Serializable {


   private Enseignant enseignant;

    // for the view
    private  List<Enseignant> listEnseignants;

    /**
     * *pour les vues
     */
    private List<Enseignant> filtredEnseignants;

    // pour charger une seule fois la liste des enseignants

    /**
     *
     */
    @PostConstruct
    public void init() {
        enseignant      = new Enseignant();
        listEnseignants = getAllEnseignant();
    }

    /**
     *
     * @return
     */
    public Enseignant getEnseignant() {
        return enseignant;
    }

    /**
     *
     * @param enseignant
     */
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    /**
     *
     * @return
     */
    public List<Enseignant> getFiltredEnseignants() {
        return filtredEnseignants;
    }

    /**
     *
     * @param filtredEnseignants
     */
    public void setFiltredEnseignants(List<Enseignant> filtredEnseignants) {
        this.filtredEnseignants = filtredEnseignants;
    }

    /**
     *
     * @return
     */
    public List<Enseignant> getListEnseignants() {
        return listEnseignants;
    }

    /**
     *
     * @param listEnseignants
     */
    public void setListEnseignants(List<Enseignant> listEnseignants) {
        this.listEnseignants = listEnseignants;
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Enseignant ens = (Enseignant) event.getObject();

        if (ens.update()) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise à jour avec succés."));
            enseignant = new Enseignant();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mise à jour échouée."));
        }
    }

    /**
     *
     * @param event
     */
    public void cancelUpdate(RowEditEvent event) {
        String       nom1    = ((Enseignant) event.getObject()).getNom();
        String       prenom1 = ((Enseignant) event.getObject()).getPrenom();
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_INFO, "Annulation",
                               "Mise à jour de " + nom1 + " " + prenom1 + " annulée."));
    }

    /**
     *
     */
    public void ajouter() {
        try {
            enseignant.save();
            // ajouter l'enseignant a la liste
            listEnseignants.add(enseignant);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de l'enseignant avec succes !"));
            RequestContext.getCurrentInstance().execute("dlg.hide();");
        } catch (ConstraintViolationException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'enseignant existe déja !"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                                     "L'ajout de l'enseignant a échoué !" + ex));
        }
    }

    /**
     *
     * @param e
     */
    public void supprimer(Enseignant e) {
        if (e.delete()) {
            listEnseignants.remove(e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                                     "Suppression de l'enseignant avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                                     "Suppression de l'enseignant a échoué !"));
        }
    }

    /**
     *
     * @param cin
     * @return
     */
    public static Enseignant getEnseignant(int cin) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Enseignant ens = (Enseignant) session.get(Enseignant.class, cin);

                return ens;
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
    public static boolean EnseignantExiste(Integer i) {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                Enseignant e = (Enseignant) session.get(Enseignant.class, i);

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
    public static List<Enseignant> getAllEnseignant() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Enseignant> list = (List<Enseignant>) session.createQuery("from Enseignant").list();

                return list;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
}

