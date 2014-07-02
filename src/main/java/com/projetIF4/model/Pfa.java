package com.projetIF4.model;

//Generated 29 mars 2014 16:50:57 by Hibernate Tools 3.6.0

//~--- JDK imports ------------------------------------------------------------

import com.projetIF4.hibernate.HibernateUtil;
import java.util.Date;
import java.util.Objects;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Pfa generated by hbm2java
 */
public class Pfa implements java.io.Serializable {
    private Integer    codepfa;
    private Specialite specialite;
    private Enseignant encardeur;
    private Section section;
    private Salle      salle;
    private Etudiant   etudiantByEtuCin1;
    private Etudiant   etudiantByEtuCin;
    private String     nomprojet;
    private String     description;
    private Date       datesoutenance;
    private String     dureesoutenance;

    /**
     *
     */
    public Pfa() {}

    /**
     *
     * @param specialite
     * @param enseignant
     * @param section
     * @param salle
     * @param etudiantByEtuCin1
     * @param etudiantByEtuCin
     * @param nomprojet
     * @param description
     * @param datesoutenance
     * @param duree
     */
    public Pfa(Specialite specialite, Enseignant enseignant,Section section, Salle salle, Etudiant etudiantByEtuCin1,
               Etudiant etudiantByEtuCin, String nomprojet, String description, Date datesoutenance, String duree) {
        this.specialite        = specialite;
        this.encardeur        = enseignant;
        this.salle             = salle;
        this.section=section;
        this.etudiantByEtuCin1 = etudiantByEtuCin1;
        this.etudiantByEtuCin  = etudiantByEtuCin;
        this.nomprojet         = nomprojet;
        this.description       = description;
        this.datesoutenance    = datesoutenance;
        this.dureesoutenance= duree;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 89 * hash + Objects.hashCode(this.codepfa);
        hash = 89 * hash + Objects.hashCode(this.nomprojet);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.datesoutenance);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Pfa other = (Pfa) obj;

        if (!Objects.equals(this.codepfa, other.codepfa)) {
            return false;
        }

        if (!Objects.equals(this.nomprojet, other.nomprojet)) {
            return false;
        }

        if (!Objects.equals(this.description, other.description)) {
            return false;
        }

        if (!Objects.equals(this.datesoutenance, other.datesoutenance)) {
            return false;
        }
        if (!Objects.equals(this.dureesoutenance, other.dureesoutenance)) {
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     */
    public Enseignant getEncardeur() {
        return encardeur;
    }

    /**
     *
     * @param encardeur
     */
    public void setEncardeur(Enseignant encardeur) {
        this.encardeur = encardeur;
    }

    /**
     *
     * @return
     */
    public String getDureesoutenance() {
        return dureesoutenance;
    }

    /**
     *
     * @param dureesoutenance
     */
    public void setDureesoutenance(String dureesoutenance) {
        this.dureesoutenance = dureesoutenance;
    }

    /**
     *
     * @return
     */
    public Integer getCodepfa() {
        return this.codepfa;
    }

    /**
     *
     * @param codepfa
     */
    public void setCodepfa(Integer codepfa) {
        this.codepfa = codepfa;
    }

    /**
     *
     * @return
     */
    public Specialite getSpecialite() {
        return this.specialite;
    }

    /**
     *
     * @param specialite
     */
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    /**
     *
     * @return
     */
    public Section getSection() {
        return section;
    }

    /**
     *
     * @param section
     */
    public void setSection(Section section) {
        this.section = section;
    }

    /**
     *
     * @return
     */
    public Enseignant getEnseignant() {
        return this.encardeur;
    }

    /**
     *
     * @param enseignant
     */
    public void setEnseignant(Enseignant enseignant) {
        this.encardeur = enseignant;
    }

    /**
     *
     * @return
     */
    public Salle getSalle() {
        return this.salle;
    }

    /**
     *
     * @param salle
     */
    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    /**
     *
     * @return
     */
    public Etudiant getEtudiantByEtuCin1() {
        return this.etudiantByEtuCin1;
    }

    /**
     *
     * @param etudiantByEtuCin1
     */
    public void setEtudiantByEtuCin1(Etudiant etudiantByEtuCin1) {
        this.etudiantByEtuCin1 = etudiantByEtuCin1;
    }

    /**
     *
     * @return
     */
    public Etudiant getEtudiantByEtuCin() {
        return this.etudiantByEtuCin;
    }

    /**
     *
     * @param etudiantByEtuCin
     */
    public void setEtudiantByEtuCin(Etudiant etudiantByEtuCin) {
        this.etudiantByEtuCin = etudiantByEtuCin;
    }

    /**
     *
     * @return
     */
    public String getNomprojet() {
        return this.nomprojet;
    }

    /**
     *
     * @param nomprojet
     */
    public void setNomprojet(String nomprojet) {
        this.nomprojet = nomprojet;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Date getDatesoutenance() {
        return this.datesoutenance;
    }

    /**
     *
     * @param datesoutenance
     */
    public void setDatesoutenance(Date datesoutenance) {
        this.datesoutenance = datesoutenance;
    }
    
    public boolean save() {
        final Session session = HibernateUtil.currentSession();
        session.setFlushMode(FlushMode.COMMIT);
        session.setCacheMode(CacheMode.REFRESH);
        try {
            final Transaction transaction = session.beginTransaction();

            try {
                session.save(this);
                transaction.commit();
                session.refresh(this);
                return true;
            } catch (HibernateException ex) {
                transaction.rollback();
                return false;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public boolean update() {
        final Session session = HibernateUtil.currentSession();
        session.setFlushMode(FlushMode.COMMIT);
        session.setCacheMode(CacheMode.REFRESH);
        try {
            final Transaction transaction = session.beginTransaction();

            try {
                session.merge(this);
                transaction.commit();
                session.refresh(this);
                return true;
            } catch (HibernateException ex) {
                transaction.rollback();
                return false;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
}

