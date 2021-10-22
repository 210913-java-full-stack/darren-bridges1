package services;

import Models.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import repos.FlightRepo;


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

    private static void scheduleFlight(Flight flight) {
        FlightRepo.addFlight(flight);
    }

    private static void deleteFlight(int flightNum) {
       FlightRepo.deleteByNumber(flightNum);
    }

    public static void requestManager(HttpServletRequest req) {
        String header = req.getHeader("header");
        InputStream requestBody = null;
        try {
            requestBody = req.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(requestBody, StandardCharsets.UTF_8.name());
        switch(header) {
            case "schedule-flight":
                try {
                    String jsonText = sc.useDelimiter("\\A").next();
                    Flight newFlight = mapper.readValue(jsonText, Flight.class);
                    scheduleFlight(newFlight);

                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
            case "delete-flight":

                String jsonText = sc.useDelimiter("\\A").next();
                try {

                    deleteFlight(mapper.readValue(jsonText, Flight.class).getFlightNumber());
                } catch (JsonProcessingException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;


        }

    }
}
