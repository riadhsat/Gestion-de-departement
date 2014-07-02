
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetIF4.controller;

//~--- non-JDK imports --------------------------------------------------------
import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Enseignant;
import com.projetIF4.model.Etudiant;
import com.projetIF4.model.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Moez-pc
 */
@SessionScoped
@ManagedBean
public class UsersController implements Serializable {

    @Min(
            value = 1000000,
            message = "Entrer un CIN valide !"
    )
    @Max(
            value = 99999999,
            message = "Entrer un CIN valide !"
    )
    private Integer userCin;
    @Size(
            min = 2,
            message = "Veuiller entrer votre nom et prénom !"
    )
    @Pattern(
            regexp = "[a-zA-Z _-]*",
            message = "Votre nom et prénom ne sont invalides!"
    )
    private String userLogin;
    @Size(
            min = 4,
            message = "Le mot de passe doit contenir au minimum 4 caractères!"
    )
    private String userPass;

    // géré automatiquement
    private String userType;
    private List<Utilisateur> filtredUtilisateur;
    private List<Utilisateur> listUtilisateur;

    private Etudiant etudiant;
    private Enseignant enseignant;
    
    private Utilisateur newutilisateur;

    public UsersController() {
    }  
    
    @PostConstruct
    public void init() {
        listUtilisateur = getAllUtilisateur();
    }
    
    
    
    public void prepareCreate(){
        newutilisateur =new Utilisateur();
        System.err.println("list"+listUtilisateur);
    }
    
    public void add(){    
        newutilisateur.setType("administrateur");
        if (newutilisateur.save()){
        listUtilisateur.add(newutilisateur);
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "ajout avec succes "));
        RequestContext.getCurrentInstance().execute("PF('dlgadd').hide();");
        }else{
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "compte existe déja !"));}
    }
    public void edit(){    
        if (newutilisateur.update()){
        listUtilisateur.add(newutilisateur);
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "La modification du compte a été effectuée avec succès"));
        listUtilisateur=getAllUtilisateur();
        RequestContext.getCurrentInstance().execute("PF('dlgEdit').hide();");
        }else{
            listUtilisateur=getAllUtilisateur();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "erreur de modification !"));}
    }
    
    public void annuler(){
        listUtilisateur=getAllUtilisateur();
        afficheMsgAnnuler();
    }
    public void afficheMsgAnnuler(){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Opération annulée"));    
    }
    
    

    /**
     *
     */
    

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    /**
     *
     * @return
     */
    public List<Utilisateur> getFiltredUtilisateur() {
        return filtredUtilisateur;
    }

    /**
     *
     * @param filtredUtilisateur
     */
    public void setFiltredUtilisateur(List<Utilisateur> filtredUtilisateur) {
        this.filtredUtilisateur = filtredUtilisateur;
    }

    /**
     *
     * @return
     */
    public List<Utilisateur> getListUtilisateur() {
        return listUtilisateur;
    }

    /**
     *
     * @param listUtilisateur
     */
    public void setListUtilisateur(List<Utilisateur> listUtilisateur) {
        this.listUtilisateur = listUtilisateur;
    }

    /**
     *
     * @return
     */
    public Integer getUserCin() {
        return userCin;
    }

    /**
     *
     * @param userCin
     */
    public void setUserCin(Integer userCin) {
        this.userCin = userCin;
    }

    /**
     *
     * @return
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     *
     * @param userLogin
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     *
     * @return
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     *
     * @param userPass
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /**
     *
     * @return
     */
    public String getUserType() {
        return userType;
    }

    /**
     *
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     */
    public void modifierCompte() {
        Utilisateur e = new Utilisateur();

        // algoritme de hashage pour les passes
        e.setCin(userCin);
        e.setPass(userPass);
        e.setLogin(userLogin);
        e.setType(userType);

        if (e.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                            "La modification de votre compte a été effectuée avec succès !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "La modification de votre compte a échoué ! Veuiller réésayer "));
        }
    }

    /**
     *
     */
    public void ajouterEtudiant() {
        try {
            Utilisateur e = new Utilisateur();

            e.setCin(userCin);
            e.setLogin(userLogin);

            // algoritme de hashage pour les passes
//          if (userPass.equals(confirmUserPass)) {
            e.setPass(userPass);
            e.setType("etudiant");

            // on cherche si l'utilisateur est enregistré comme étudiant
            if (!EtudiantControleur.EtudiantExiste(userCin)) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, "Erreur",
                                "Vous n'êtes pas inscrits comme étant étudiants! Veuiller consulter l'administration et rééssayer !"));
            } else {
                e.save();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de compte avec succes !"));
            }
        } // else {
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Les deux mots de passes ne sont pas identiques !"));
        // }
        // }
        catch (ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Votre compte existe déja !"));

            throw e;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "L'ajout de votre compte a échoué ! Veuiller réésayer " + ex));

            throw ex;
        }
    }

    /**
     *
     */
    public void ajouterEnseignant() {
        try {
            Utilisateur e = new Utilisateur();

            e.setCin(userCin);
            e.setLogin(userLogin);

            // algoritme de hashage pour les passes
//          if (userPass.equals(confirmUserPass)) {
            e.setPass(userPass);
            e.setType("ensignant");

            // on cherche si l'utilisateur est enregistré comme étudiant
            if (!EnseignantControleur.EnseignantExiste(userCin)) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, "Erreur",
                                "Vous n'êtes pas inscrits comme étant enseignant! Veuiller consulter l'administration et rééssayer !"));
            } else {
                e.save();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Ajout de compte avec succes !"));
            }
        } // else {
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Les deux mots de passes ne sont pas identiques !"));
        // }
        // }
        catch (ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Votre compte existe déja !"));

            throw e;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "L'ajout de votre compte a échoué ! Veuiller réésayer " + ex));

            throw ex;
        }
    }

    /**
     *
     */
    public void confirmerModif() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification",
                "Vos modifications ont été enregistrés avec succes.");

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     * @return
     */
    public String ajouter() {
        switch (userType) {
            case "etudiant":
                ajouterEtudiant();
                break;
            case "enseignant":
                ajouterEnseignant();
                break;

            // a completer dans le cas d'ajout d'aministratif
        }

        return "/index.jsf";
    }

    /**
     *
     * @return
     */
    public static List<Utilisateur> getAllUtilisateur() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                List<Utilisateur> list = (List<Utilisateur>) session.createQuery("from Utilisateur").list();

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
    public String login() {
        final Session session = HibernateUtil.currentSession();

        try {
            try {
                Query q = session.createQuery("from Utilisateur u WHERE id = :cin AND u.pass = :pass");

                q.setParameter("cin", userCin);
                q.setParameter("pass", userPass);

                List<Utilisateur> list = q.list();

                if (list.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                                    "Vérifier votre CIN et votre mot de passe ! "));

                    return null;
                } else {
                    Utilisateur u = list.get(0);

                    setUserLogin(u.getLogin());
                    String type = u.getType();
                    String returnValue = "";



                    switch (type) {
                        case "etudiant":
//                        etudiant = EtudiantControleur.GetEtudiant(u.getCin());
                            returnValue = "pretty:EtudiantIndex";
                            break;
                        case "enseignant":
//                        enseignant = EnseignantControleur.getEnseignant(u.getCin());
                            returnValue = "pretty:EnseignantIndex";
                            break;
                        case "administratif":
                            returnValue = "pretty:AdministratifIndex";
                            break;
                        case "administrateur":
                            returnValue = "pretty:AdminIndex";
                            break;
                    }
                    return returnValue;
                }
            } catch (HibernateException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur de connexion ! " + ex.getMessage()));
            }
        } finally {
            HibernateUtil.closeSession();
        }

        return null;
    }

    /**
     *
     * @return @throws IOException
     */
    public String deconnect() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        // invalider la session
        ((HttpSession) ec.getSession(true)).invalidate();

        return ("pretty:Index");
    }

    /**
     *
     * @param event
     */
    public void update(RowEditEvent event) {
        Utilisateur u = (Utilisateur) event.getObject();

        if (u.update()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                            "Mise a jour de l'utilisateur avec succes !"));
        } // on peut notifier l'utilisateur par mail
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec",
                            "Mise a jour de l'utilisateur a échoué !"));
        }

        // on peut notifier l'utilisateur par mail
    }

    /**
     *
     * @param event
     */
    public void cancelUpdate(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise a jour de l'utilisateur annulée !"));
    }

    /**
     *
     * @param u
     */
    public void supprimer(Utilisateur u) {
        if (u.delete()) {
            listUtilisateur.remove(u);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                            "Suppression de l'utilisateur avec succès !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur",
                            "Suppression de l'utilisateur a échoué !"));
        }
    }

    public Utilisateur getNewutilisateur() {
        return newutilisateur;
    }

    public void setNewutilisateur(Utilisateur newutilisateur) {
        this.newutilisateur = newutilisateur;
    }
    
    
}
