
package com.projetIF4.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Satellite
 */
@ManagedBean
@ViewScoped
public class ImportRapport implements Serializable {
    
    //pour envoi de fichier
    private UploadedFile file;
    
    //pour affichage de fichier
    private String nomfile;
    private Date dateupload;
    private String taille;
    private StreamedContent filedown;
    
    @PostConstruct
    public void init(){
        try {
            getFileRapport();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportRapport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void getFileRapport() throws FileNotFoundException{
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String FileName = servletContext.getRealPath("") + File.separator + "Rapport";
        File dossier = new File(FileName);//test existance de dossier rapport
        if(!dossier.exists()){
        dossier.mkdir();
        }
        File[] Files=dossier.listFiles();
        if(Files.length!=0){
            nomfile=Files[0].getName();
            if(Files[0].length()>=1048576){
                taille=Files[0].length()/(1024*1024)+"Mo";
            }
            else{
                taille=Files[0].length()/1024+"Ko";
            }        
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Files[0].lastModified());
        dateupload = c.getTime();
        preparationFileDownload();        
        }       
    }
    
    public void preparationFileDownload() throws FileNotFoundException{
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "Rapport";
        File dossier = new File(newFileName);
        File[] files=dossier.listFiles();        
        newFileName=newFileName+File.separator+files[0].getName();
        String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(newFileName);
        InputStream stream;
        stream = new FileInputStream(new File(newFileName));
        filedown= new DefaultStreamedContent(stream, contentType, files[0].getName());
    }
    
    
    public void upload(FileUploadEvent  event) {
        
        file=event.getFile();
        FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " a été envoyée avec succès");
        FacesContext.getCurrentInstance().addMessage(null, message);
        EnregisrementRapport();
        try {
            getFileRapport();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportRapport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void EnregisrementRapport(){
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "Rapport";
        File dossier = new File(newFileName);//test existance de dossier rapport
        if(!dossier.exists()){
        dossier.mkdir();
        }
        File[] files=dossier.listFiles();
        if(files.length!=0){
            files[0].delete();
        }        
        try{
            InputStream in=file.getInputstream();
            OutputStream out = new FileOutputStream(new File(newFileName+File.separator+file.getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            FacesMessage message = new FacesMessage("Succes", file.getFileName() + " a été enregistré avec succès");
            FacesContext.getCurrentInstance().addMessage(null, message);
        
        }
        catch(IOException e){
        FacesMessage message = new FacesMessage("Erreur", file.getFileName() + " : Erreur d'enregistrement.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
     public StreamedContent getFiledown(){   
        try {
            preparationFileDownload();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportRapport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filedown;
    }   

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getNomfile() {
        return nomfile;
    }

    public void setNomfile(String nomfile) {
        this.nomfile = nomfile;
    }

    public Date getDateupload() {
        return dateupload;
    }

    public void setDateupload(Date dateupload) {
        this.dateupload = dateupload;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }  
}
