package com.projetIF4.model;

//Generated 24 mars 2014 18:11:52 by Hibernate Tools 3.6.0

//~--- non-JDK imports --------------------------------------------------------

import com.projetIF4.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Salle generated by hbm2java
 */
public class Salle implements java.io.Serializable {
    private Set     pfas = new HashSet(0);
    private Set     pfes = new HashSet(0);
    private Integer codesal;
    private String  salle;

    /**
     *
     */
    public Salle() {}

    /**
     *
     * @param salle
     */
    public Salle(String salle) {
        this.salle = salle;
    }

    /**
     *
     * @param salle
     * @param pfas
     * @param pfes
     */
    public Salle(String salle, Set pfas, Set pfes) {
        this.salle = salle;
        this.pfas  = pfas;
        this.pfes  = pfes;
    }

    /**
     *
     * @return
     */
    public Integer getCodesal() {
        return this.codesal;
    }

    /**
     *
     * @param codesal
     */
    public void setCodesal(Integer codesal) {
        this.codesal = codesal;
    }

    /**
     *
     * @return
     */
    public String getSalle() {
        return this.salle;
    }

    /**
     *
     * @param salle
     */
    public void setSalle(String salle) {
        this.salle = salle;
    }

    /**
     *
     * @return
     */
    public Set getPfas() {
        return this.pfas;
    }

    /**
     *
     * @param pfas
     */
    public void setPfas(Set pfas) {
        this.pfas = pfas;
    }

    /**
     *
     * @return
     */
    public Set getPfes() {
        return this.pfes;
    }

    /**
     *
     * @param pfes
     */
    public void setPfes(Set pfes) {
        this.pfes = pfes;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 71 * hash + Objects.hashCode(this.codesal);
        hash = 71 * hash + Objects.hashCode(this.salle);

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

        final Salle other = (Salle) obj;

        if (!Objects.equals(this.codesal, other.codesal)) {
            return false;
        }

        if (!Objects.equals(this.salle, other.salle)) {
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



