import Models.Flight;
import Models.Ticket;
import Models.User;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import services.GlobalStore;

import java.util.List;

public class drive {
    public static void main(String args[]) throws ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        Configuration config = new Configuration();
        config.addAnnotatedClass(Flight.class);
        config.addAnnotatedClass(Ticket.class);
        config.addAnnotatedClass(User.class);
        GlobalStore.setSessionFactory(config.buildSessionFactory());
        GlobalStore.setSession(GlobalStore.getSessionFactory().openSession());


        Flight test = new Flight("austin", "San Diego", true);
        Flight test2 = new Flight("dallas", "NYC", true);
        User test3 = new User();
        User test4 = new User(1, "username", "password", "admin");
        Ticket test5 = new Ticket();
        Ticket test6 = new Ticket();
        test4.buyTicket(test5);
        test2.bookFlight(test5);
        test2.bookFlight(test6);
        test4.buyTicket(test6);
        repos.FlightRepo.addFlight(test);
        repos.FlightRepo.addFlight(test2);
        Transaction transaction = GlobalStore.getSession().beginTransaction();

        GlobalStore.getSession().save(test3);
        GlobalStore.getSession().save(test4);
        GlobalStore.getSession().save(test5);
        GlobalStore.getSession().save(test6);
        transaction.commit();

        List<Ticket> l = repos.FlightRepo.getFlightByNum(2).getTicketList();
        System.out.println(l.get(1));
        System.out.println(l.get(0));
        for (Flight flight: repos.FlightRepo.getAvail()) {
            System.out.println(flight.getDepart() + ", " + flight.getArrive());
        }
        repos.FlightRepo.deleteByNumber(1);

        //testing branch
        //after everything is complete
        GlobalStore.getSession().close();

    }
}
