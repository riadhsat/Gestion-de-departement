package com.projetIF4.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Moez-pc
 */
public class HibernateUtil {

    /**
     *
     */
    public static final ThreadLocal session = new ThreadLocal();
    private static final SessionFactory sessionFactory;

    static {
        try {

            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory
                    = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (HibernateException ex) {

            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);

            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     *
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     *
     * @return
     * @throws HibernateException
     */
    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();

        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }

        return s;
    }

    /**
     *
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();

        session.set(null);

        if (s != null) {
            s.close();
        }
    }
}
