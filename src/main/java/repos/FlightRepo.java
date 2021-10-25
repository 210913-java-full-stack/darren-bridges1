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
import java.util.List;

public class FlightRepo {

    public static List<Flight> getAvail() {
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();
        CriteriaQuery<Flight> query = build.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        query.select(root).where(build.equal( root.get("available"), 1) );
        List<Flight> ret = GlobalStore.getSession().createQuery(query).getResultList();
        for (int i = 0; i < ret.size(); i++) {
            ret.get(i).clearList();
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

    public static Flight getFlightByNum(int flightNum) {
        return GlobalStore.getSession().get(Flight.class, flightNum);
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
