package services;

import Models.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import repos.FlightRepo;
import repos.TicketRepo;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FlightService {

    private static ObjectMapper mapper = new ObjectMapper();





    private static void scheduleFlight(Flight flight) {
        FlightRepo.addFlight(flight);
    }

    private static void deleteFlight(int flightNum) {
       FlightRepo.deleteByNumber(flightNum);
    }

    private static void makeUn(int flightNum) {
        FlightRepo.makeUnavailable(flightNum);
    }

    public static void postRequestManager(HttpServletRequest req) {
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

                try {
                    String jsonText = sc.useDelimiter("\\A").next();
                    Flight fn = mapper.readValue(jsonText, Flight.class);
                    Flight flight = GlobalStore.getSession().get(Flight.class, fn.getFlightNumber());
                    System.out.println(flight.getTicketList().size());
                    for (int i = 0; i < flight.getTicketList().size(); i++) {
                        TicketRepo.cancelTicket(flight.getTicketList().get(i).getTicketID());
                    }
                    deleteFlight(fn.getFlightNumber());
                } catch (JsonProcessingException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
            case "un-flight":
                try {
                    String jsonText = sc.useDelimiter("\\A").next();
                    makeUn(mapper.readValue(jsonText, Flight.class).getFlightNumber());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;


        }


    }

    public static String viewFlightManager(HttpServletRequest req) {
        String header = req.getHeader("header");
        switch(header) {
            case "view-ind-flight":
                try {
                    Flight flight = FlightRepo.getFlightByNum(Integer.parseInt(req.getHeader("Id")));
                    return mapper.writeValueAsString(flight);
                } catch (JsonProcessingException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
            case "view-all-flight":

                List<Flight> flightList = FlightRepo.getAvail();

                try {
                    return mapper.writeValueAsString(flightList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;


        }
        return "NoSuchFlight";
    }
}
