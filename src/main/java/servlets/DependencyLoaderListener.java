package servlets;



import Models.Flight;
import Models.Ticket;
import Models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import repos.FlightRepo;
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
        config.addAnnotatedClass(User.class);

        GlobalStore.setSessionFactory(config.buildSessionFactory());
        GlobalStore.setSession(GlobalStore.getSessionFactory().openSession());

        Flight test = new Flight("austin", "San Diego", true);




    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        GlobalStore.getSession().close();
    }
}