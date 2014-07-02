/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.projetIF4.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Moez-pc
 */
@ManagedBean
@RequestScoped
public class MailControleur {

    private String mailDestination;

    private String message;

    private String objet;

    public MailControleur() {
    }

    public String getMailDestination() {
        return mailDestination;
    }

    public void setMailDestination(String mailDestination) {
        this.mailDestination = mailDestination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void envoyerMail() {
        boolean succes = false;
        while (!succes) {
            try {
                mail();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCES",
                                "Le mail a été envoyé avec succés !"));
                succes = true;
            } catch (EmailException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                                "L'envoi du mail a échoué !" + ex));
            }
        }
    }

    public void mail() throws EmailException {
        Email email = new SimpleEmail();
        email.setCharset("UTF-8");
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("fst.rnu.info@gmail.com", "adminFST123456789"));
        email.setSSLOnConnect(true);
        email.setFrom("fst.rnu.info@gmail.com", "Département Informatique FST");
        email.setSubject(objet);
        email.setMsg(message);
        email.addTo(mailDestination);
        email.send();
    }

}
