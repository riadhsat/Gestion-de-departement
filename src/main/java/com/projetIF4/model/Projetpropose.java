package com.projetIF4.model;

//Generated 24 mars 2014 18:11:52 by Hibernate Tools 3.6.0

//~--- non-JDK imports --------------------------------------------------------

import com.projetIF4.hibernate.HibernateUtil;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Moez-pc
 */
public class Projetpropose implements java.io.Serializable {
    private Set        choixProjets = new HashSet(0);
    private Integer    codeProjet;
    private Etudiant   etudiantByCinetu;
    private Etudiant   etudiantByEtuCinetu;
    private Enseignant encadreur;
    private Section section;
    private String     typeProjet;
    private String     nomProjet;
    private String     description;
    private String     langageprog;
    private String     etatProjet;
    private String     proposePar;

    /**
     *
     */
    public Projetpropose() {}

    /**
     *
     * @param typeProjet
     * @param nomProjet
     * @param description
     * @param section
     * @param etatProjet
     * @param proposePar
     */
    public Projetpropose(String typeProjet, String nomProjet, String description,Section section ,String etatProjet,
                         String proposePar) {
        this.typeProjet  = typeProjet;
        this.nomProjet   = nomProjet;
        this.description = description;
        this.etatProjet  = etatProjet;
        this.proposePar  = proposePar;
        this.section = section;
    }

    /**
     *
     * @param etudiantByCinetu
     * @param etudiantByEtuCinetu
     * @param section
     * @param enseignant
     * @param typeProjet
     * @param nomProjet
     * @param description
     * @param langageprog
     * @param etatProjet
     * @param proposePar
     * @param choixProjets
     */
    public Projetpropose(Etudiant etudiantByCinetu, Etudiant etudiantByEtuCinetu,Section section ,Enseignant enseignant,
                         String typeProjet, String nomProjet, String description, String langageprog,
                         String etatProjet, String proposePar, Set choixProjets) {
        this.etudiantByCinetu    = etudiantByCinetu;
        this.etudiantByEtuCinetu = etudiantByEtuCinetu;
        this.encadreur          = enseignant;
        this.typeProjet          = typeProjet;
        this.nomProjet           = nomProjet;
        this.description         = description;
        this.langageprog         = langageprog;
        this.etatProjet          = etatProjet;
        this.proposePar          = proposePar;
        this.choixProjets        = choixProjets;
        this.section=section;
    }

    /**
     *
     * @return
     */
    public Integer getCodeProjet() {
        return this.codeProjet;
    }

    /**
     *
     * @param codeProjet
     */
    public void setCodeProjet(Integer codeProjet) {
        this.codeProjet = codeProjet;
    }

    /**
     *
     * @return
     */
    public Etudiant getEtudiantByCinetu() {
        return this.etudiantByCinetu;
    }

    /**
     *
     * @param etudiantByCinetu
     */
    public void setEtudiantByCinetu(Etudiant etudiantByCinetu) {
        this.etudiantByCinetu = etudiantByCinetu;
    }

    /**
     *
     * @return
     */
    public Etudiant getEtudiantByEtuCinetu() {
        return this.etudiantByEtuCinetu;
    }

    /**
     *
     * @param etudiantByEtuCinetu
     */
    public void setEtudiantByEtuCinetu(Etudiant etudiantByEtuCinetu) {
        this.etudiantByEtuCinetu = etudiantByEtuCinetu;
    }

    /**
     *
     * @return
     */
    public Enseignant getEncadreur() {
        return this.encadreur;
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
     * @param enseignant
     */
    public void setEncadreur(Enseignant enseignant) {
        this.encadreur = enseignant;
    }

    /**
     *
     * @return
     */
    public String getTypeProjet() {
        return this.typeProjet;
    }

    /**
     *
     * @param typeProjet
     */
    public void setTypeProjet(String typeProjet) {
        this.typeProjet = typeProjet;
    }

    /**
     *
     * @return
     */
    public String getNomProjet() {
        return this.nomProjet;
    }

    /**
     *
     * @param nomProjet
     */
    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
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
    public String getLangageprog() {
        return this.langageprog;
    }

    /**
     *
     * @param langageprog
     */
    public void setLangageprog(String langageprog) {
        this.langageprog = langageprog;
    }

    /**
     *
     * @return
     */
    public String getEtatProjet() {
        return this.etatProjet;
    }

    /**
     *
     * @param etatProjet
     */
    public void setEtatProjet(String etatProjet) {
        this.etatProjet = etatProjet;
    }

    /**
     *
     * @return
     */
    public String getProposePar() {
        return this.proposePar;
    }

    /**
     *
     * @param proposePar
     */
    public void setProposePar(String proposePar) {
        this.proposePar = proposePar;
    }

    /**
     *
     * @return
     */
    public Set getChoixProjets() {
        return this.choixProjets;
    }

    /**
     *
     * @param choixProjets
     */
    public void setChoixProjets(Set choixProjets) {
        this.choixProjets = choixProjets;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 29 * hash + Objects.hashCode(this.codeProjet);
        hash = 29 * hash + Objects.hashCode(this.typeProjet);
        hash = 29 * hash + Objects.hashCode(this.nomProjet);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.langageprog);
        hash = 29 * hash + Objects.hashCode(this.etatProjet);
        hash = 29 * hash + Objects.hashCode(this.proposePar);

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

        final Projetpropose other = (Projetpropose) obj;

        if (!Objects.equals(this.codeProjet, other.codeProjet)) {
            return false;
        }

        if (!Objects.equals(this.typeProjet, other.typeProjet)) {
            return false;
        }

        if (!Objects.equals(this.nomProjet, other.nomProjet)) {
            return false;
        }

        if (!Objects.equals(this.description, other.description)) {
            return false;
        }

        if (!Objects.equals(this.langageprog, other.langageprog)) {
            return false;
        }

        if (!Objects.equals(this.etatProjet, other.etatProjet)) {
            return false;
        }

        if (!Objects.equals(this.proposePar, other.proposePar)) {
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

    /**
     *
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
}



