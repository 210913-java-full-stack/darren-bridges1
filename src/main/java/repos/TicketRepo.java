package repos;

import Models.Flight;
import Models.Ticket;
import Models.User;
import jdk.nashorn.internal.objects.Global;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import services.GlobalStore;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class TicketRepo {

    public static void purchaseTicket(Flight flight, User user) {
        Ticket newTicket = new Ticket();
        flight.bookFlight(newTicket);
        user.buyTicket(newTicket);
        Transaction buy = GlobalStore.getSession().beginTransaction();
        GlobalStore.getSession().save(newTicket);
        buy.commit();
    }

    public static void checkIn(int ticketNum) {
        Transaction upd = GlobalStore.getSession().beginTransaction();
        Query query = GlobalStore.getSession().createQuery(
                "UPDATE Ticket SET check_IN = true WHERE ticketID = :ticketNum");
        query.setParameter("ticketNum", ticketNum);
        int result = query.executeUpdate();
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();
        upd.commit();

    }

    public static void cancelTicket(int ticketNumber) {
        Transaction del = GlobalStore.getSession().beginTransaction();
        Query query = GlobalStore.getSession().createQuery(
                "DELETE Ticket WHERE ticketID = :ticketNumber");
        query.setParameter("ticketNumber", ticketNumber);
        int result = query.executeUpdate();
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();
        del.commit();


    }
    public static List<Ticket> getTicketListForFlight(int flightId){
        List<Ticket> res = new LinkedList<>();
        Flight flight = GlobalStore.getSession().get(Flight.class, flightId);

        for(Ticket newAdd : flight.getTicketList()) {
            int findId = newAdd.getTicketID();
            res.add(GlobalStore.getSession().get(Ticket.class, findId));
        }
        return res;
    }
}
