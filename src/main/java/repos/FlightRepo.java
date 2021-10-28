package repos;

import Models.Flight;
import org.hibernate.Transaction;
import services.GlobalStore;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;


/**
 * The FlightRepo class contains methods for all hibernate queries for accessing a
 * database to retrieve information about flight objects. Utilized by the
 * FlightService class primarily but can be called anywhere.
 *
 *
 * @author  Chris Oh and Darren Bridges
 * @version 1.0
 * @since   2021-10-27
 */


public class FlightRepo {

    /*
    getAvail is a method to retrieve a list of Flight Objects that have their
    available method set to true. It does not return the exact flight object, but a copy
    that contains null for the ticketList field in order to properly be mapped to a
    JSON string by Jackson
    */
    public static List<Flight> getAvail() {
        //Create CriteriaBuilder
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();

        //Create a query for a Flight object
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);

        //Root for the query
        Root<Flight> root = query.from(Flight.class);

        //Selecting FLight objects where available field is true
        query.select(root).where(build.equal( root.get("available"), 1) );

        //Store the exact objects from database in a list
        List<Flight> rl = GlobalStore.getSession().createQuery(query).getResultList();

        //Create a new list to copy the flights into
        List<Flight> ret = new LinkedList<>();

        //Loop to add flights into new list without TicketList field
        for (int i = 0; i < rl.size(); i++) {
            ret.add(new Flight(rl.get(i).getDepart(), rl.get(i).getArrive(), rl.get(i).isAvailable(), rl.get(i).getFlightNumber()));
        }

        //Return the list of copies
        return ret;
    }

    /*
    Remove a Flight object from the database based on primary key
     */
    public static void deleteByNumber(int flightNumber) {
        //Start Transaction
        Transaction del = GlobalStore.getSession().beginTransaction();

        //Create Query
        Query query = GlobalStore.getSession().createQuery(
                "DELETE Flight WHERE flightNumber = :flightNumber");

        query.setParameter("flightNumber", flightNumber);

        //Execute Deletion
        int result = query.executeUpdate();

        //Clear Hibernate Cache in case it is used before a Select Query
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();

        //End Transaction
        del.commit();
    }

    /*
    Add a Flight Object to the database
     */
    public static void addFlight(Flight flight){
        //Begin Transaction
        Transaction add = GlobalStore.getSession().beginTransaction();

        //Add Flight to the database
        GlobalStore.getSession().save(flight);

        //End Transaction
        add.commit();
    }

    /*
    Retrieve the exact Flight object from the database. Needed when the
    program needs to interact with the TicketList field
     */
    public static Flight getExFlight(int flightNum) {
        //Create CriteriaBuilder
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();

        //Create CriteriaQuery for a Flight object
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);

        //Root for query
        Root<Flight> root = query.from(Flight.class);

        //Select Flight based on flightNumber field
        query.select(root).where(build.equal( root.get("flightNumber"), flightNum) );

        //Create variable for Flight from database
        Flight f = GlobalStore.getSession().createQuery(query).getSingleResult();

        //Return Flight from database
        return f;
    }

    /*
    Get a flight from the database and create a clone without the TicketList field
    for appropriate display
     */
    public static Flight getFlightByNum(int flightNum) {
        //Create CriteriaBuilder
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();

        //Create query for Flight object
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);

        //Create root for query
        Root<Flight> root = query.from(Flight.class);

        //Select Flight object based on flightNumber field
        query.select(root).where(build.equal( root.get("flightNumber"), flightNum) );

        //Retrieve Flight Object from database
        Flight f = GlobalStore.getSession().createQuery(query).getSingleResult();

        //Create clone of flight object without the TicketList field
        Flight ret = new Flight(f.getDepart(), f.getArrive(), f.isAvailable(), f.getFlightNumber());

        //Return Clone
        return ret;
    }

    /*
    Update a Flight to Make it Unavailable (Pilot Story, but did not get to making a pilot role)
     */
    public static void makeUnavailable(int flightNum) {
        //Begin Transaction
        Transaction upd = GlobalStore.getSession().beginTransaction();

        //Update a Flight Object's availability to false based on flightNumber field
        Query query = GlobalStore.getSession().createQuery(
                "UPDATE Flight SET available = false WHERE flightNumber = :flightNum");
        query.setParameter("flightNum", flightNum);

        //Update
        int result = query.executeUpdate();

        //Clear hibernate cache in case this flight is viewed again after update
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();

        //End Transaction
        upd.commit();
    }


}
