package com.projetIF4.model;

//Generated 24 mars 2014 18:11:52 by Hibernate Tools 3.6.0

//~--- non-JDK imports --------------------------------------------------------

import com.projetIF4.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;

/**
 * Etudiant generated by hbm2java
 */
public class Etudiant implements java.io.Serializable {
    private Set        choixEnseignantsForCinetu  = new HashSet(0);
    private Set        choixEnseignantsForCinetu2 = new HashSet(0);
    private Set        pfasForEtuCin1             = new HashSet(0);
    private Set        pfasForEtuCin              = new HashSet(0);
    private Set        pfesForEtuCin1             = new HashSet(0);
    private Set        choixProjetsForCinetud1    = new HashSet(0);
    private Set        projetproposesForEtuCinetu = new HashSet(0);
    private Set        pfesForEtuCin              = new HashSet(0);
    private Set        choixProjetsForCinetud2    = new HashSet(0);
    private Set        projetproposesForCinetu    = new HashSet(0);
    private int        cinetu;
    private Specialite specialite;
    private Section    section;
    private String     nomPrenom;
    private String     mail;
    private Date       datenaissance;
    private Integer    ins;
    private Integer    score;

    /**
     *
     */
    public Etudiant() {}

    /**
     *
     * @param cinetu
     * @param specialite
     * @param section
     */
    public Etudiant(int cinetu, Specialite specialite, Section section) {
        this.cinetu     = cinetu;
        this.specialite = specialite;
        this.section    = section;
    }

    /**
     *
     * @param cinetu
     * @param specialite
     * @param section
     * @param nomPrenom
     * @param mail
     * @param datenaissance
     * @param ins
     * @param score
     * @param choixEnseignantsForCinetu
     * @param choixEnseignantsForCinetu2
     * @param pfasForEtuCin1
     * @param pfasForEtuCin
     * @param pfesForEtuCin1
     * @param choixProjetsForCinetud1
     * @param projetproposesForEtuCinetu
     * @param pfesForEtuCin
     * @param choixProjetsForCinetud2
     * @param projetproposesForCinetu
     */
    public Etudiant(int cinetu, Specialite specialite, Section section, String nomPrenom, String mail,
                    Date datenaissance, Integer ins, Integer score, Set choixEnseignantsForCinetu,
                    Set choixEnseignantsForCinetu2, Set pfasForEtuCin1, Set pfasForEtuCin, Set pfesForEtuCin1,
                    Set choixProjetsForCinetud1, Set projetproposesForEtuCinetu, Set pfesForEtuCin,
                    Set choixProjetsForCinetud2, Set projetproposesForCinetu) {
        this.cinetu                     = cinetu;
        this.specialite                 = specialite;
        this.section                    = section;
        this.nomPrenom                  = nomPrenom;
        this.mail                       = mail;
        this.datenaissance              = datenaissance;
        this.ins                        = ins;
        this.score                      = score;
        this.choixEnseignantsForCinetu  = choixEnseignantsForCinetu;
        this.choixEnseignantsForCinetu2 = choixEnseignantsForCinetu2;
        this.pfasForEtuCin1             = pfasForEtuCin1;
        this.pfasForEtuCin              = pfasForEtuCin;
        this.pfesForEtuCin1             = pfesForEtuCin1;
        this.choixProjetsForCinetud1    = choixProjetsForCinetud1;
        this.projetproposesForEtuCinetu = projetproposesForEtuCinetu;
        this.pfesForEtuCin              = pfesForEtuCin;
        this.choixProjetsForCinetud2    = choixProjetsForCinetud2;
        this.projetproposesForCinetu    = projetproposesForCinetu;
    }

    /**
     *
     * @return
     */
    public int getCinetu() {
        return this.cinetu;
    }

    /**
     *
     * @param cinetu
     */
    public void setCinetu(int cinetu) {
        this.cinetu = cinetu;
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
        return this.section;
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
    public String getNomPrenom() {
        return this.nomPrenom;
    }

    /**
     *
     * @param nomPrenom
     */
    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    /**
     *
     * @return
     */
    public String getMail() {
        return this.mail;
    }

    /**
     *
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     *
     * @return
     */
    public Date getDatenaissance() {
        return this.datenaissance;
    }

    /**
     *
     * @param datenaissance
     */
    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    /**
     *
     * @return
     */
    public Integer getIns() {
        return this.ins;
    }

    /**
     *
     * @param ins
     */
    public void setIns(Integer ins) {
        this.ins = ins;
    }

    /**
     *
     * @return
     */
    public Integer getScore() {
        return this.score;
    }

    /**
     *
     * @param score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     *
     * @return
     */
    public Set getChoixEnseignantsForCinetu() {
        return this.choixEnseignantsForCinetu;
    }

    /**
     *
     * @param choixEnseignantsForCinetu
     */
    public void setChoixEnseignantsForCinetu(Set choixEnseignantsForCinetu) {
        this.choixEnseignantsForCinetu = choixEnseignantsForCinetu;
    }

    /**
     *
     * @return
     */
    public Set getChoixEnseignantsForCinetu2() {
        return this.choixEnseignantsForCinetu2;
    }

    /**
     *
     * @param choixEnseignantsForCinetu2
     */
    public void setChoixEnseignantsForCinetu2(Set choixEnseignantsForCinetu2) {
        this.choixEnseignantsForCinetu2 = choixEnseignantsForCinetu2;
    }

    /**
     *
     * @return
     */
    public Set getPfasForEtuCin1() {
        return this.pfasForEtuCin1;
    }

    /**
     *
     * @param pfasForEtuCin1
     */
    public void setPfasForEtuCin1(Set pfasForEtuCin1) {
        this.pfasForEtuCin1 = pfasForEtuCin1;
    }

    /**
     *
     * @return
     */
    public Set getPfasForEtuCin() {
        return this.pfasForEtuCin;
    }

    /**
     *
     * @param pfasForEtuCin
     */
    public void setPfasForEtuCin(Set pfasForEtuCin) {
        this.pfasForEtuCin = pfasForEtuCin;
    }

    /**
     *
     * @return
     */
    public Set getPfesForEtuCin1() {
        return this.pfesForEtuCin1;
    }

    /**
     *
     * @param pfesForEtuCin1
     */
    public void setPfesForEtuCin1(Set pfesForEtuCin1) {
        this.pfesForEtuCin1 = pfesForEtuCin1;
    }

    /**
     *
     * @return
     */
    public Set getChoixProjetsForCinetud1() {
        return this.choixProjetsForCinetud1;
    }

    /**
     *
     * @param choixProjetsForCinetud1
     */
    public void setChoixProjetsForCinetud1(Set choixProjetsForCinetud1) {
        this.choixProjetsForCinetud1 = choixProjetsForCinetud1;
    }

    /**
     *
     * @return
     */
    public Set getProjetproposesForEtuCinetu() {
        return this.projetproposesForEtuCinetu;
    }

    /**
     *
     * @param projetproposesForEtuCinetu
     */
    public void setProjetproposesForEtuCinetu(Set projetproposesForEtuCinetu) {
        this.projetproposesForEtuCinetu = projetproposesForEtuCinetu;
    }

    /**
     *
     * @return
     */
    public Set getPfesForEtuCin() {
        return this.pfesForEtuCin;
    }

    /**
     *
     * @param pfesForEtuCin
     */
    public void setPfesForEtuCin(Set pfesForEtuCin) {
        this.pfesForEtuCin = pfesForEtuCin;
    }

    /**
     *
     * @return
     */
    public Set getChoixProjetsForCinetud2() {
        return this.choixProjetsForCinetud2;
    }

    /**
     *
     * @param choixProjetsForCinetud2
     */
    public void setChoixProjetsForCinetud2(Set choixProjetsForCinetud2) {
        this.choixProjetsForCinetud2 = choixProjetsForCinetud2;
    }

    /**
     *
     * @return
     */
    public Set getProjetproposesForCinetu() {
        return this.projetproposesForCinetu;
    }

    /**
     *
     * @param projetproposesForCinetu
     */
    public void setProjetproposesForCinetu(Set projetproposesForCinetu) {
        this.projetproposesForCinetu = projetproposesForCinetu;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 13 * hash + this.cinetu;
        hash = 13 * hash + Objects.hashCode(this.nomPrenom);
        hash = 13 * hash + Objects.hashCode(this.mail);
        hash = 13 * hash + Objects.hashCode(this.datenaissance);
        hash = 13 * hash + Objects.hashCode(this.ins);
        hash = 13 * hash + Objects.hashCode(this.score);

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

        final Etudiant other = (Etudiant) obj;

        if (this.cinetu != other.cinetu) {
            return false;
        }

        if (!Objects.equals(this.nomPrenom, other.nomPrenom)) {
            return false;
        }

        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }

        if (!Objects.equals(this.datenaissance, other.datenaissance)) {
            return false;
        }

        if (!Objects.equals(this.ins, other.ins)) {
            return false;
        }

        if (!Objects.equals(this.score, other.score)) {
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     */
     public boolean save() {
        final Session session = HibernateUtil.currentSession();
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

    /**
     *
     * @return
     */
    public boolean delete() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                session.delete(this);
                transaction.commit();

                return true;
            } catch (Exception ex) {

                // Log the exception here
                transaction.rollback();

                return false;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /**
     *
     * @return
     */
    public boolean update() {
        final Session session = HibernateUtil.currentSession();

        try {
            final Transaction transaction = session.beginTransaction();

            try {
                session.merge(this);
                transaction.commit();

                return true;
            } catch (Exception ex) {

                // Log the exception here
                transaction.rollback();

                return false;
            }
        } finally {
            HibernateUtil.closeSession();
        }
    }
}



