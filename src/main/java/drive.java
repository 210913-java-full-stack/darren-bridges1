import Models.Flight;
import Models.Ticket;
import Models.User;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import repos.FlightRepo;
import repos.TicketRepo;
import repos.UserRepo;
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

        FlightRepo.addFlight(test);
        FlightRepo.addFlight(test2);

        Transaction transaction = GlobalStore.getSession().beginTransaction();

        GlobalStore.getSession().save(test3);
        GlobalStore.getSession().save(test4);

        transaction.commit();

        TicketRepo.purchaseTicket(test2, test4);
        TicketRepo.purchaseTicket(test2, test4);

        List<Ticket> l = FlightRepo.getFlightByNum(2).getTicketList();

        for (Flight flight: repos.FlightRepo.getAvail()) {
            System.out.println(flight.getDepart() + ", " + flight.getArrive());
        }
        TicketRepo.checkIn(1);
        List<Ticket> t = TicketRepo.getTicketListForFlight(2);


        repos.FlightRepo.deleteByNumber(1);

        TicketRepo.cancelTicket(2);
        System.out.println(UserRepo.getUser("username"));

        GlobalStore.getSession().close();

    }
}
