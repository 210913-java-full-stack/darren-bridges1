import Models.Flight;
import Models.Ticket;
import Models.User;
import REPOs.FlightRepo;
import jdk.nashorn.internal.objects.Global;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import services.FlightService;
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
        FlightService.setSession(GlobalStore.getSession());

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
        FlightRepo.addFlight(test);
        FlightRepo.addFlight(test2);
        Transaction transaction = FlightService.getSession().beginTransaction();

        FlightService.getSession().save(test3);
        FlightService.getSession().save(test4);
        FlightService.getSession().save(test5);
        FlightService.getSession().save(test6);
        transaction.commit();

        List<Ticket> l = FlightRepo.getFlightByNum(2).getTicketList();
        System.out.println(l.get(1));
        System.out.println(l.get(0));
        for (Flight flight: FlightRepo.getAvail()) {
            System.out.println(flight.getDepart() + ", " + flight.getArrive());
        }
        FlightRepo.deleteByNumber(1);

        //testing branch
        //after everything is complete
        GlobalStore.getSession().close();

    }
}
