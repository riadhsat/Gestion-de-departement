package com.projetIF4.model;

//Generated 29 mars 2014 16:50:57 by Hibernate Tools 3.6.0

/**
 * ChoixProjetId generated by hbm2java
 */
public class ChoixProjetId implements java.io.Serializable {
    private int cinetud1;
    private int codeProjet;

    /**
     *
     */
    public ChoixProjetId() {}

    /**
     *
     * @param cinetud1
     * @param codeProjet
     */
    public ChoixProjetId(int cinetud1, int codeProjet) {
        this.cinetud1   = cinetud1;
        this.codeProjet = codeProjet;
    }

    /**
     *
     * @return
     */
    public int getCinetud1() {
        return this.cinetud1;
    }

    /**
     *
     * @param cinetud1
     */
    public void setCinetud1(int cinetud1) {
        this.cinetud1 = cinetud1;
    }

    /**
     *
     * @return
     */
    public int getCodeProjet() {
        return this.codeProjet;
    }

    /**
     *
     * @param codeProjet
     */
    public void setCodeProjet(int codeProjet) {
        this.codeProjet = codeProjet;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 59 * hash + this.cinetud1;
        hash = 59 * hash + this.codeProjet;

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

        final ChoixProjetId other = (ChoixProjetId) obj;

        if (this.cinetud1 != other.cinetud1) {
            return false;
        }

        if (this.codeProjet != other.codeProjet) {
            return false;
        }

        return true;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
