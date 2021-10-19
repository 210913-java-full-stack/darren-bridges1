package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GlobalStore {
    private static SessionFactory sessionFactory;
    private static Session session;

    public GlobalStore() {
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        GlobalStore.sessionFactory = sessionFactory;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        GlobalStore.session = session;
    }
}
