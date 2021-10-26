import Models.Flight;
import Models.Ticket;
import Models.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import repos.FlightRepo;
import repos.TicketRepo;
import repos.UserRepo;
import services.GlobalStore;
import services.TicketService;

import java.util.List;

public class drive {
    public static void main(String args[]) throws ClassNotFoundException, JsonProcessingException {
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


        System.out.println(UserRepo.getUser("username"));


        ObjectMapper om = new ObjectMapper();


        TicketService.testMeth();


        //System.out.println(om.writeValueAsString(ret));




        GlobalStore.getSession().close();

    }
}
