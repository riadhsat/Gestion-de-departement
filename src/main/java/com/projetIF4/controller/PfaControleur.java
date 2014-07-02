
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.projetIF4.controller;



import com.projetIF4.hibernate.HibernateUtil;
import com.projetIF4.model.Etudiant;
import com.projetIF4.model.Pfa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class PfaControleur implements Serializable{


    private Pfa selected;
    private Pfa newpfa;
    private List<Pfa> pfas;
    private List<Pfa> filtrepfas;
    private List<Etudiant> etudiants;
    private List<Etudiant> etudiantsNonAffecte;
    private List<Etudiant> filtreetudiants;
    
    
    
    
    @PostConstruct
    public void init(){
        pfas = getPFANonPlanifies();
        pfas.addAll(getPFAPlanifies());
        etudiants= getAllEtudiantNonTerminal();
        
    }
    
    
    public void prepareCreate(){
        newpfa=new Pfa();
    
    }
    public boolean testEtudiantInProjets(Etudiant e){
        for(int i=0;i<pfas.size();i++){
            if(Objects.equals(e, pfas.get(i).getEtudiantByEtuCin())||Objects.equals(e, pfas.get(i).getEtudiantByEtuCin1()))
                    return true;
        }    
        return false;
    }
    
    public void updateInformation(){
        pfas = getPFANonPlanifies();
        pfas.addAll(getPFAPlanifies());
        etudiantsNonAffecte=new ArrayList<>();
        for(int i=0;i<etudiants.size();i++){
            if(!testEtudiantInProjets(etudiants.get(i))){
                etudiantsNonAffecte.add(etudiants.get(i));
            }
        }
    }
    
    public void ajouter(){
        newpfa.save();
        pfas.add(newpfa);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Projet ajouté avec succès"));
        System.err.println("code :"+newpfa.getCodepfa());
    }
    
    public void supprimer(){
        pfas.remove(selected);
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
   public void filterCinNom(){
       System.err.println("looool");
   
   }
   
   public void mettreAJourEtudiant2(){
       filtreetudiants=new ArrayList<>();
       for(int i=0;i<etudiants.size();i++){
           if (Objects.equals(newpfa.getEtudiantByEtuCin().getSection(), etudiants.get(i).getSection()))
               filtreetudiants.add(etudiants.get(i));
       }
       filtreetudiants.remove(newpfa.getEtudiantByEtuCin());
       
       newpfa.setSection(newpfa.getEtudiantByEtuCin().getSection());
       newpfa.setSpecialite(newpfa.getEtudiantByEtuCin().getSpecialite());
   }
   
   public static List<Etudiant> getAllEtudiantNonTerminal() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                Query q = session.createQuery("select e from Etudiant e  WHERE  e.section.terminal = :val");
                q.setParameter("val", false);
                List<Etudiant> Etudiants = q.list();
                return Etudiants;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

 public static List<Pfa> getPFANonPlanifies() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfa> list = (List<Pfa>) session.createQuery("from Pfa where datesoutenance is null").list();

                return list;
            } catch (HibernateException ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
 
 
    public static List<Pfa> getPFAPlanifies() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfa> list = (List<Pfa>) session.createQuery("from Pfa where datesoutenance is not null").list();
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
     public void delete(Pfa pfa){
    final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
               session.delete(pfa);
               transaction.commit();
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
     public static List<Pfa> getPFAAffectes() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfa> list = (List<Pfa>) session.createQuery("from Pfa where etudiantByEtuCin is not null or etudiantByEtuCin1 is not null").list();
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
      public static List<Pfa> getPFANonAffectes() {
        final Session session = HibernateUtil.currentSession();
        try {
            final Transaction transaction = session.beginTransaction();
            try {
                List<Pfa> list = (List<Pfa>) session.createQuery("from Pfa where etudiantByEtuCin is null and etudiantByEtuCin1 is null").list();
                return list;
            } catch (HibernateException ex) {
                System.err.println(ex);
                throw ex;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public Pfa getSelected() {
        return selected;
    }

    public void setSelected(Pfa selected) {
        this.selected = selected;
    }

    public Pfa getNewpfa() {
        return newpfa;
    }

    public void setNewpfa(Pfa newpfa) {
        this.newpfa = newpfa;
    }

    public List<Pfa> getPfas() {
        return pfas;
    }

    public void setPfas(List<Pfa> pfas) {
        this.pfas = pfas;
    }

    public List<Pfa> getFiltrepfas() {
        return filtrepfas;
    }

    public void setFiltrepfas(List<Pfa> filtrepfas) {
        this.filtrepfas = filtrepfas;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public List<Etudiant> getFiltreetudiants() {
        return filtreetudiants;
    }

    public void setFiltreetudiants(List<Etudiant> filtreetudiants) {
        this.filtreetudiants = filtreetudiants;
    }

    
    
    
}

