
package com.projetIF4.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Satellite
 */
@ManagedBean
@ViewScoped
public class ImportRapport implements Serializable {
    
    
    private UploadedFile file;

public void upload(FileUploadEvent  event) {
 System.err.println("sucess");
 file=event.getFile();
FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " envoyer avec succes");
            FacesContext.getCurrentInstance().addMessage(null, message);
            EnregisrementRapport();
}
    
    public void EnregisrementRapport(){
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "Rapport";
        System.err.println("lien : "+newFileName);
        
        File dossier = new File(newFileName);//test existance de dossier rapport
        if(!dossier.exists()){
        dossier.mkdir();
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
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        
        }
        catch(IOException e){
        FacesMessage message = new FacesMessage("Erreur", file.getFileName() + " is not uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }    
}
