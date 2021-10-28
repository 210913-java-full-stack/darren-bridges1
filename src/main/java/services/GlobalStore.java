package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * The GlobalStore class is a small class that contains a session for this program's
 * purposes. It's primary purpose is to ensure Singleton creation of the Session object.
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


public class GlobalStore {

    //Instantiate the SessionFactory object
    private static SessionFactory sessionFactory;

    //Instantiate the Session object
    private static Session session;

    //No args constructor
    public GlobalStore() {
    }

    //SessionFactory getter
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //SessionFactory Setter
    public static void setSessionFactory(SessionFactory sessionFactory) {
        GlobalStore.sessionFactory = sessionFactory;
    }

    //Session Setter
    public static Session getSession() {
        return session;
    }

    //SessionGetter
    public static void setSession(Session session) {
        GlobalStore.session = session;
    }
}
