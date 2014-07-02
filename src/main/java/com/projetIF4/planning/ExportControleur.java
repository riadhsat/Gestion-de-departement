package com.projetIF4.planning;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.projetIF4.controller.PfeControleur;
import com.projetIF4.model.Enseignant;
import com.projetIF4.model.Pfe;
import com.projetIF4.model.Salle;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Satellite
 */
@ManagedBean
@ViewScoped
public class ExportControleur implements Serializable{
    
    private Salle salle;
    private List<Pfe> pfes;
    private Enseignant enseignant;
    private List<Pfe> pfespresident;
    private List<Pfe> pfesrapporteur;
    private List<Pfe> pfesencadreur;
    

    
    public ExportControleur() {
    }
    
    
    @PostConstruct
    public void affiche(){
        pfes=PfeControleur.getListPfeBySalle(salle);
    }
     public void afficheTablePFE(){
       pfespresident=PfeControleur.getListPfeByPresident(enseignant);
       pfesrapporteur=PfeControleur.getListPfeByRapporteur(enseignant);
       pfesencadreur=PfeControleur.getListPfeByEncadreur(enseignant);
    }
     
     public void ajoutEnTetePDF(Object document) throws DocumentException, BadElementException, IOException,DocumentException
     {
         Document pdf = (Document) document;
         pdf.open();
         pdf.setPageSize(PageSize.A4);
         ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
         String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "banner.png";
         pdf.add(Image.getInstance(logo));
     }
    

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public List<Pfe> getPfes() {
        return pfes;
    }

    public void setPfes(List<Pfe> pfes) {
        this.pfes = pfes;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public List<Pfe> getPfespresident() {
        return pfespresident;
    }

    public void setPfespresident(List<Pfe> pfespresident) {
        this.pfespresident = pfespresident;
    }

    public List<Pfe> getPfesrapporteur() {
        return pfesrapporteur;
    }

    public void setPfesrapporteur(List<Pfe> pfesrapporteur) {
        this.pfesrapporteur = pfesrapporteur;
    }

    public List<Pfe> getPfesencadreur() {
        return pfesencadreur;
    }

    public void setPfesencadreur(List<Pfe> pfesencadreur) {
        this.pfesencadreur = pfesencadreur;
    }
    
    
    
}
