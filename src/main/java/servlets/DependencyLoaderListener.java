package servlets;



import Models.Flight;
import Models.Ticket;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import services.FlightService;
import services.GlobalStore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//TODO: change this to log
        }
        Configuration config = new Configuration();
        config.addAnnotatedClass(Flight.class);
        config.addAnnotatedClass(Ticket.class);
        GlobalStore.setSessionFactory(config.buildSessionFactory());
        GlobalStore.setSession(GlobalStore.getSessionFactory().openSession());


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        FlightService.getSession().close();
    }
}