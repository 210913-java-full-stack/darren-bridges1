package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FlightService {
    private static Session session;

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        FlightService.session = session;
    }
}
