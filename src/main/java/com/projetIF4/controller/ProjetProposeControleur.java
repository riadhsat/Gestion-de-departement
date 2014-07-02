
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetIF4.controller;

//~--- non-JDK imports --------------------------------------------------------
import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Etudiant;
import com.projetIF4.model.Projetpropose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Satellite
 */
@ViewScoped
@ManagedBean
public class ProjetProposeControleur implements Serializable {

    private final Integer cinUtilisateur;

    private Projetpropose projetPropose;

    private List<Projetpropose> projets;

    private List<Projetpropose> projetsChoisis;

    private boolean binome;

    public ProjetProposeControleur() {
        projetPropose = new Projetpropose();
        FacesContext context = FacesContext.getCurrentInstance();
        UsersController u = (UsersController) context.getApplication().evaluateExpressionGet(context,
                "#{usersController}", UsersController.class);
        cinUtilisateur = u.getUserCin();
        projetsChoisis = new ArrayList<>();
        binome = false;
    }

    public List<Projetpropose> getProjetsChoisis() {
        return projetsChoisis;
    }

    public void setProjetsChoisis(List<Projetpropose> projetsChoisis) {
        this.projetsChoisis = projetsChoisis;
    }

    public boolean isBinome() {
        return binome;
    }

    public void setBinome(boolean binome) {
        this.binome = binome;
    }

    public Projetpropose getProjetPropose() {
        System.err.println("Projet selectionne "+projetPropose.getDescription());
        return projetPropose;
    }

    public void setProjetPropose(Projetpropose projetPropose) {
        this.projetPropose = projetPropose;
    }

    /**
     *
     * @return
     */
    public List<Projetpropose> getProjets() {
        projets = getAllProjetsProposes();

        return projets;
    }

    /**
     *
     * @param projets
     */
    public void setProjets(List<Projetpropose> projets) {
        this.projets = projets;
    }

    public String getNomEtudiant(Integer cin) {
        Etudiant e = EtudiantControleur.GetEtudiant(cin);
        if (e != null) {
            return e.getNomPrenom();
        }
        return null;
    }

    /**
     *
     */
    public void ajouterparEtudiant() {
        projetPropose.setProposePar("etudiant");
        projetPropose.setEtatProjet("en attente");
        // on recupere les informations de l'utilisateur connecté
        FacesContext context = FacesContext.getCurrentInstance();
        UsersController u = (UsersController) context.getApplication().evaluateExpressionGet(context,
                "#{usersController}", UsersController.class);

        projetPropose.setEtudiantByCinetu(u.getEtudiant());

        String sec =  (u.getEtudiant().getSection()).getSection();
        if (sec.equals("IF4")) {
            projetPropose.setTypeProjet("pfa");
        } else {
            projetPropose.setTypeProjet("pfe");
        }
        if (projetPropose.save()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout du projet avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout du projet a échoué ! section : " + projetPropose.getSection()));

        }
    }

    /**
     *
     */
    public void ajouterparDepartement() {
        projetPropose.setProposePar("departement");
        projetPropose.setEtatProjet("non affecte");
        if ((projetPropose.getSection().getSection()).equals("IF4")) {
            projetPropose.setTypeProjet("pfa");
        } else {
            projetPropose.setTypeProjet("pfe");
        }
        if (projetPropose.save()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout du projet avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout du projet a échoué ! section : " + projetPropose.getSection()));
        }
    }

    public void ajouterparEnseignant() {
        projetPropose.setProposePar("enseignant");
        projetPropose.setEtatProjet("non affecte");
        if ((projetPropose.getSection().getSection()).equals("IF4")) {
            projetPropose.setTypeProjet("pfa");
        } else {
            projetPropose.setTypeProjet("pfe");
        }
        projetPropose.setEncadreur(EnseignantControleur.getEnseignant(cinUtilisateur));
        if (projetPropose.save()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout du projet avec succes !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ajout du projet a échoué ! section : " + projetPropose.getSection()));
        }
    }

    /**
     *
     * @return
     */
    public static List<Projetpropose> getAllProjetsProposes() {
        final Session session = HibernateUtil.currentSession();
        try {
            try {
                List<Projetpropose> projets = (List<Projetpropose>) session.createQuery("from Projetpropose").list();
                return projets;
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
    public void update() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                for (Projetpropose listProjetpropose : projets) {
                    session.merge(listProjetpropose);
                }

                transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                                "Mise a jour des projets avec succes !"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                                "Mise à jour des projets a échoué !" + ex));
                transaction.rollback();

                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void choisirProjet(DragDropEvent event)
    {
        Projetpropose p = (Projetpropose) event.getData();
        projetsChoisis.add(p);
//        projets.remove(p);
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                                "Projet choisi "+p.getNomProjet()));
    }

}
