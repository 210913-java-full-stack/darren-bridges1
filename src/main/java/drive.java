import Models.Flight;
import jdk.nashorn.internal.objects.Global;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import services.FlightService;
import services.GlobalStore;

public class drive {
    public static void main(String args[]) throws ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        Configuration config = new Configuration();
        config.addAnnotatedClass(Flight.class);
        GlobalStore.setSessionFactory(config.buildSessionFactory());
        GlobalStore.setSession(GlobalStore.getSessionFactory().openSession());
        FlightService.setSession(GlobalStore.getSession());

        Flight test = new Flight();
        Flight test2 = new Flight("dallas", "NYC", true);

        Transaction transaction = FlightService.getSession().beginTransaction();
        FlightService.getSession().save(test);
        FlightService.getSession().save(test2);
        transaction.commit();



        //after everything is complete
        GlobalStore.getSession().close();

    }
}
