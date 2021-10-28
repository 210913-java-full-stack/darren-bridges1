package repos;

import Models.Flight;
import Models.Ticket;
import Models.User;
import org.hibernate.Transaction;
import services.GlobalStore;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;


/**
 * The TicketRepo class contains methods for all hibernate queries for Ticket objects.
 * Primarily called by the TicketService class, but can be called anywhere.
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


public class TicketRepo {

    /*
    Method to add a Ticket object to the database.
     */
    public static void purchaseTicket(Flight flight, User user) {
        //Create new Ticket Object to add to the database
        Ticket newTicket = new Ticket();

        //Add Ticket object to a Flight object for ManyToOne Relationship
        flight.bookFlight(newTicket);

        //Add Ticket object to a User object for ManyToOne Relationship
        user.buyTicket(newTicket);

        //Begin Transaction
        Transaction buy = GlobalStore.getSession().beginTransaction();

        //Save Ticket object to database
        GlobalStore.getSession().save(newTicket);

        //End Transaction
        buy.commit();
    }

    /*
    Update Ticket object checkIn field to true
     */
    public static void checkIn(int ticketNum) {
        //Begin Transaction
        Transaction upd = GlobalStore.getSession().beginTransaction();

        //Update Ticket object in database based on ticketId field
        Query query = GlobalStore.getSession().createQuery(
                "UPDATE Ticket SET check_IN = true WHERE ticketID = :ticketNum");
        query.setParameter("ticketNum", ticketNum);

        //Execute update
        int result = query.executeUpdate();

        //Clear hibernate caches in case this ticket is viewed again
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();

        //End transaction
        upd.commit();
    }

    /*
    Delete Ticket objects from the database based on flightNumber. Method
    specifically for when a flight is deleted to cascade the related tickets
    to be deleted
     */
    public static void cancelTicketF(int flightNum) {
        //Begin Transaction
        Transaction del = GlobalStore.getSession().beginTransaction();

        //Select Ticket object to delete based on flightNumber
        Query query = GlobalStore.getSession().createQuery(
                "DELETE Ticket WHERE flight = :flightNum");
        query.setParameter("flightNum", flightNum);

        //Execute Delete
        int result = query.executeUpdate();

        //Clear hibernate caches in case this ticket is viewed again
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();

        //End Transaction
        del.commit();

    }

    /*
    Delete Ticket object from database based on ticketID field
     */
    public static void cancelTicket(int ticketNumber) {
        //Begin Transaction
        Transaction del = GlobalStore.getSession().beginTransaction();

        //Delete ticket based on ticketId field
        Query query = GlobalStore.getSession().createQuery(
                "DELETE Ticket WHERE ticketID = :ticketNumber");
        query.setParameter("ticketNumber", ticketNumber);

        //Execute deletion
        int result = query.executeUpdate();

        //Clear hibernate caches in case someone attempts to access this ticket again
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();

        //End Transaction
        del.commit();
    }

    /*
    Get a list of Ticket objects with only a ticketId field
    for a given flight based on flightId field
     */
    public static List<Ticket> getTicketListForFlight(int flightId){
        //Create list of Ticket objects to be returned
        List<Ticket> res = new LinkedList<>();

        //Retrieve the flight from the database
        Flight flight = GlobalStore.getSession().get(Flight.class, flightId);

        //Add ticketId to ticketList from flight. Only adds this field
        for(Ticket newAdd : flight.getTicketList()) {
            int findId = newAdd.getTicketID();
            res.add(GlobalStore.getSession().get(Ticket.class, findId));
        }

        //Return List of ticketId
        return res;
    }

    /*
    Get a list of Ticket objects that are related to a User object based on
    the userId field of that object
     */
    public static List<Ticket> getMyTickets(int userId) {
        //Create CriteriaBuilder
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();

        //Create criteriaQuery for Ticket object
        CriteriaQuery<Ticket> query = build.createQuery(Ticket.class);

        //Create root for query
        Root<Ticket> root = query.from(Ticket.class);

        //Select all ticket objects related to a User object based on userID field
        query.select(root).where(build.equal( root.get("user"), userId) );

        //Create variable for returning the Ticket objects
        List<Ticket> ret = GlobalStore.getSession().createQuery(query).getResultList();

        //Return the Ticket objects
        return ret;
    }

    /*
    Get a list of Ticket objects that are related to a Flight object based on
    the flightNumber field of that object
     */
    public static List<Ticket> getTicketsByFlight(int flightNum) {
        //Create CriteriaBuilder
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();

        //Create Query for flight Object
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);

        //Create root for query
        Root<Flight> root = query.from(Flight.class);

        //Select Flight object based on flightNumber field
        query.select(root).where(build.equal( root.get("flightNumber"), flightNum) );

        //Create variable for Flight object retrieved from database
        Flight ret = GlobalStore.getSession().createQuery(query).getSingleResult();

        //Return list of Ticket objects related to Flight object
        return ret.getTicketList();
    }

}
