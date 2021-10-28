package services;

import Models.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import repos.FlightRepo;
import repos.TicketRepo;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;


/**
 * The FlightService class interprets HTTP requests sent to it and returns strings for
 * a print writer to send to the web page. It also has methods that call the methods
 * from the FlightRepo class.
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


public class FlightService {


    //Create an ObjectMapper to return JSON strings
    private static ObjectMapper mapper = new ObjectMapper();

    /*
    Call the addFlight method from the FlightRepo
     */
    private static void scheduleFlight(Flight flight) {
        FlightRepo.addFlight(flight);
    }

    /*
    Call the deleteByNumber method from the FlightRepo
     */
    private static void deleteFlight(int flightNum) {
       FlightRepo.deleteByNumber(flightNum);
    }

    /*
    Call the makeUnavailable method from the FlightRepo
     */
    private static void makeUn(int flightNum) {
        FlightRepo.makeUnavailable(flightNum);
    }

    /*
    Interpret a POST request from the FlightServlet
     */
    public static void postRequestManager(HttpServletRequest req) {
        //Get the header that denotes type of POST request
        String header = req.getHeader("header");

        //Instantiate the InputStream variable to interpret the body
        InputStream requestBody = null;

        try {
            //Assign the InputStream to variable
            requestBody = req.getInputStream();

        } catch (IOException e) {
            //Log any exceptions
            e.printStackTrace(); //TODO: exception logger
        }

        //Scanner to read the body
        Scanner sc = new Scanner(requestBody, StandardCharsets.UTF_8.name());

        //Perform Different actions based on POST request type
        switch(header) {

            //Add new Flight objects to database
            case "schedule-flight":
                try {
                    //Retrieve Body as a String
                    String jsonText = sc.useDelimiter("\\A").next();

                    //Create New Flight from object in body
                    Flight newFlight = mapper.readValue(jsonText, Flight.class);

                    //Add Flight to database
                    scheduleFlight(newFlight);

                } catch (IOException e) {
                    //Log any exceptions
                    e.printStackTrace(); //TODO Add e logger
                }
                break;

            //Delete Flight objects from database
            case "delete-flight":
                try {
                    //Retrieve body as String
                    String jsonText = sc.useDelimiter("\\A").next();

                    //Create Flight from object in body
                    Flight fn = mapper.readValue(jsonText, Flight.class);

                    //Retrieve the flight from the database that the body represents
                    Flight flight = GlobalStore.getSession().get(Flight.class, fn.getFlightNumber());

                    //Delete the Ticket objects for the flight from the database
                    for (int i = 0; i < flight.getTicketList().size(); i++) {
                        TicketRepo.cancelTicket(flight.getTicketList().get(i).getTicketID());
                    }

                    //Delete the Flight object from the database
                    deleteFlight(fn.getFlightNumber());

                } catch (JsonProcessingException e) {
                    //Log any exceptions
                    e.printStackTrace(); //TODO Add e logger
                }
                break;

            //Make the available field for a Flight object false
            case "un-flight":
                try {
                    //Get body as String
                    String jsonText = sc.useDelimiter("\\A").next();

                    //Send object from string to method to make it unavailable in database
                    makeUn(mapper.readValue(jsonText, Flight.class).getFlightNumber());

                } catch (JsonProcessingException e) {
                    //log any exceptions
                    e.printStackTrace(); //TODO e logger
                }
                break;
        }
    }

    /*
    Interpret GET requests from FlightServlet
     */
    public static String viewFlightManager(HttpServletRequest req) {
        //Assign the header to a String to interpret type of GET request
        String header = req.getHeader("header");

        switch(header) {
            //Retrieve single Flight object from database
            case "view-ind-flight":
                try {
                    //Retrieve the flight number from header and use it to retrieve the Flight
                    //object from the database
                    Flight flight = FlightRepo.getFlightByNum(Integer.parseInt(req.getHeader("Id")));

                    //Return the Flight object as a JSON String
                    return mapper.writeValueAsString(flight);

                } catch (JsonProcessingException e) {
                    //Log any Exceptions
                    e.printStackTrace(); //TODO Add e logger
                }
                break;

            //Retrieve all available Flight objects from the database
            case "view-all-flight":
                //Create a list of Flight objects to convert into a JSON String
                List<Flight> flightList = FlightRepo.getAvail();

                try {
                    //Return the list of Flight objects as a JSON String
                    return mapper.writeValueAsString(flightList);

                } catch (JsonProcessingException e) {
                    //Log any exceptions
                    e.printStackTrace(); //TODO e logger
                }
                break;
        }
        //Return if no Flight objects were retrieved from database
        return null;
    }
}
