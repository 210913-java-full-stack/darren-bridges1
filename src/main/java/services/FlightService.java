package services;

import Models.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FlightService {

    private static ObjectMapper mapper = new ObjectMapper();
    private static Session session;


    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        FlightService.session = session;
    }

    public static void scheduleFlight(Flight flight) {
        REPOs.FlightRepo.addFlight(flight);
    }

    public static void requestManager(HttpServletRequest req) {
        String header = req.getHeader("header");
        switch(header) {
            case "schedule-flight":
                try {
                    InputStream requestBody = req.getInputStream();
                    Scanner sc = new Scanner(requestBody, StandardCharsets.UTF_8.name());
                    String jsonText = sc.useDelimiter("\\A").next();
                    Flight newFlight = mapper.readValue(jsonText, Flight.class);
                    scheduleFlight(newFlight);
                } catch (JsonProcessingException e ) {
                    e.printStackTrace(); //Add e Logger
                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
        }

    }
}
