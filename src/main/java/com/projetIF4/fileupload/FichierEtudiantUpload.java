
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetIF4.fileupload;

//~--- non-JDK imports --------------------------------------------------------
import com.projetIF4.model.Etudiant;
import com.projetIF4.model.Section;
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
    private int premLine;

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

    public int getPremLine() {
        return premLine;
    }

    public void setPremLine(int premLine) {
        this.premLine = premLine;
    }

    /**
     *
     */
    public void upload() {

        // faire le traitement de conversion (parser) pour l'enregistrer dans la base
        try {
            Vector data = ExcellConverter.read(file.getInputstream(), premLine);
            Vector row = new Vector();
            for (int i = premLine + 1; i < data.size(); i++) {
                row = (Vector) data.get(i);
                System.out.println(row);

                Etudiant e = new Etudiant();
                e.setCinetu(Integer.valueOf(String.valueOf(row.get(2))));
                e.setSection(new Section(String.valueOf(row.get(0))));
                e.setIns(Integer.valueOf(String.valueOf(row.get(1))));
                e.setNomPrenom(String.valueOf(row.get(3)));

                System.err.println(e.save());

            }

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur d'importation de fichier !"));
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
