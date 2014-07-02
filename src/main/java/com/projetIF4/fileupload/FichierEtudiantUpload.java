
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.projetIF4.fileupload;

//~--- non-JDK imports --------------------------------------------------------

import org.primefaces.model.UploadedFile;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Moez-pc
 */
@ManagedBean
@RequestScoped
public class FichierEtudiantUpload {
    private UploadedFile file;

    /**
     *
     * @return
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     *
     * @param file
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     *
     */
    public void upload() {
        if ((file != null) && file.getFileName().matches(".+\\.xls[x]?$")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès",
                                     "Le fichier a été téléchargé avec succes."));

            // faire le traitement de conversion (parser) pour l'enregistrer dans la base
            try {
                Vector data = ExcellConverter.read(file.getInputstream());

                System.out.println(data);

                for (int i = 1; i < data.size(); i++) {
                    Vector row = (Vector) data.elementAt(i);
                }
            } catch (IOException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur d'importation de fichier !"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Entrer un fichier Excell valide!"));
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
