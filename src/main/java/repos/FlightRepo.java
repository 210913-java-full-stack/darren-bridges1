package repos;

import Models.Flight;
import Models.Ticket;
import jdk.nashorn.internal.objects.Global;
import org.hibernate.Transaction;
import services.GlobalStore;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class FlightRepo {

    public static List<Flight> getAvail() {
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        query.select(root).where(build.equal( root.get("available"), 1) );
        List<Flight> rl = GlobalStore.getSession().createQuery(query).getResultList();
        List<Flight> ret = new LinkedList<>();
        for (int i = 0; i < rl.size(); i++) {
            ret.add(new Flight(rl.get(i).getDepart(), rl.get(i).getArrive(), rl.get(i).isAvailable(), rl.get(i).getFlightNumber()));
        }
        return ret;
    }

    public static void deleteByNumber(int flightNumber) {
        Transaction del = GlobalStore.getSession().beginTransaction();
        Query query = GlobalStore.getSession().createQuery(
                "DELETE Flight WHERE flightNumber = :flightNumber");
        query.setParameter("flightNumber", flightNumber);
        int result = query.executeUpdate();
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();
        del.commit();


    }

    public static void addFlight(Flight flight){
        Transaction add = GlobalStore.getSession().beginTransaction();
        GlobalStore.getSession().save(flight);
        add.commit();
    }

    public static Flight getExFlight(int flightNum) {
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        query.select(root).where(build.equal( root.get("flightNumber"), flightNum) );
        Flight f = GlobalStore.getSession().createQuery(query).getSingleResult();
        return f;
    }

    public static Flight getFlightByNum(int flightNum) {
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        query.select(root).where(build.equal( root.get("flightNumber"), flightNum) );
        Flight f = GlobalStore.getSession().createQuery(query).getSingleResult();
        Flight ret = new Flight(f.getDepart(), f.getArrive(), f.isAvailable(), f.getFlightNumber());

        return ret;
    }

    public static void makeUnavailable(int flightNum) {
        Transaction upd = GlobalStore.getSession().beginTransaction();
        Query query = GlobalStore.getSession().createQuery(
                "UPDATE Flight SET available = false WHERE flightNumber = :flightNum");
        query.setParameter("flightNum", flightNum);
        int result = query.executeUpdate();
        GlobalStore.getSession().flush();
        GlobalStore.getSession().clear();
        upd.commit();
    }


}
