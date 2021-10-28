package services;

import Models.Flight;
import Models.ModTicket;
import Models.Ticket;
import Models.User;
import Util.FileLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import repos.FlightRepo;
import repos.TicketRepo;
import repos.UserRepo;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/**
 * The TicketService class is the class that calls many of the TicketRepo methods
 * Additionally it handles requests sent by the TicketServlet.
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


public class TicketService {
    //Create ObjectMapper object to convert objects into JSON Strings
    private static ObjectMapper mapper = new ObjectMapper();

    /*
    Calls the purchaseTicket method from the TicketRepo
     */
    public static void purchaseFlight(int userId, int flightId) {
        TicketRepo.purchaseTicket(FlightRepo.getExFlight(flightId), UserRepo.getUserByNum(userId));
    }

    /*
    Calls the getTicketsByFlight method from the TicketRepo in order
    to create a new list of User objects for a given flight and converts
    it into a JSON String.
     */
    public static String getFlightTickets(int flight) {
        //Instantiate the new List of Users to return
        List<User> ret = new LinkedList<>();

        //Get List of Tickets from the database based on flightNumber they are related to
        List<Ticket> repoRet = TicketRepo.getTicketsByFlight(flight);

        //Instantiate String for returning
        String stringRet = null;

        //Add the User related to a ticket to the User list
        for (int i = 0; i < repoRet.size(); i++) {
            ret.add(repoRet.get(i).getUser());
        }

        try {
            //Convert List of User objects to a JSON String
            stringRet = mapper.writeValueAsString(ret);

        } catch (JsonProcessingException e) {
            //Log any exception
            FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
        }
        //Return JSON String
        return stringRet;
    }

    /*
    Get all of a given User object's tickets and return them as a JSON String.
    This method also converts the Ticket object into a ModTicket object which includes
    the userID of the user associated with the ticket.
     */
    public static String getAllTickets(int use) {
        //Instantiate new list of ModTicket objects
        List<ModTicket> retList = new LinkedList<>();

        //Get list of Ticket objects that correspond to a given User objects userID field
        List<Ticket> repoRet = TicketRepo.getMyTickets(use);

        //Convert each Ticket to a ModTicket and add it to the list
        for (int i = 0; i < repoRet.size(); i++) {
            retList.add(new ModTicket(repoRet.get(i).getTicketID(),
                    repoRet.get(i).getFlight().getFlightNumber(),
                    repoRet.get(i).isCheck_IN()));
        }

        //Instantiate String to return
        String ret = null;

        try {
            //Convert List of ModTicket objects to JSON String
            ret = mapper.writeValueAsString(retList);

        } catch (JsonProcessingException e) {
            //Log any exceptions
            FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
        }
        //Return JSON String
        return ret;
    }

    /*
    Call checkIn method from TicketRepo
     */
    private static void checkIn(int ticketNum) {
        TicketRepo.checkIn(ticketNum);
    }

    /*
    Call cancelTicket method from TicketRepo
     */
    private static void cancel(int ticketNum) {
        TicketRepo.cancelTicket(ticketNum);
    }

    /*
    This method handles POST requests sent to the TicketServlet
     */
    public static void postRequestManager(HttpServletRequest req) {
        //Retrieve the header from the request denoting the type of POST
        String header = req.getHeader("header");

        //Instantiate an InputStream for reading the body
        InputStream requestBody = null;

        try {
            //Assign the InputStream to the body
            requestBody = req.getInputStream();

        } catch (IOException e) {
            //Log any Exceptions
            FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
        }

        //Create Scanner to read the body
        Scanner sc = new Scanner(requestBody, StandardCharsets.UTF_8.name());

        switch (header) {
            //Adds Ticket objects to the database
            case "purchase-ticket":
                try {
                    //Convert the body to a String to be read
                    String jsonText = sc.useDelimiter("\\A").next();

                    //Get the userID field of a user from the header of the request
                    int userId = Integer.parseInt(req.getHeader("user"));

                    //Read the flightNumber field from the body and assign it to an int
                    int flightId = mapper.readValue(jsonText, Flight.class).getFlightNumber();

                    //Add the Ticket object to the database
                    purchaseFlight(userId, flightId);

                } catch (IOException e) {
                    //Log any exceptions
                    e.printStackTrace(); //Add e logger
                }
                break;
        }
    }

    /*
    Interpret GET requests sent to the TicketServlet
     */
    public static String viewTicketManager(HttpServletRequest req) {
        //Read the header to determine the type of GET request
        String header = req.getHeader("header");
        switch (header) {
            //View all Tickets a User has
            case "view-my-ticket":
                //Retrieve userID field from the header
                int userId = Integer.parseInt(req.getHeader("user"));

                //Return a JSON String of the TicketList
                return getAllTickets(userId);

            //Return a JSON String of all User objects who have a Ticket object for a Flight object
            case "view-tickets-of-flight":
                //Retrieve flightNumber field from the header
                int flightId = Integer.parseInt(req.getHeader("flight"));

                //Return a list of User objects in a JSON String
                return getFlightTickets(flightId);
        }
        //Alternative return
        return null;
    }

    /*
    This method handles all PUT requests sent to the TicketServlet
     */
    public static void putRequestManager(HttpServletRequest req) {
        //Get the header to denote type of PUT request
        String header = req.getHeader("header");

        //Instantiate an InputStream to read the body
        InputStream requestBody = null;

        try {
            //Get the InputStream from the body
            requestBody = req.getInputStream();

        } catch (IOException e) {
            //Log any exceptions
            e.printStackTrace();
        }

        //Create a Scanner to read the body
        Scanner sc = new Scanner(requestBody, StandardCharsets.UTF_8.name());

        switch (header) {
            //Update a ticket to be checked in
            case "check-in":
                try {
                    //Convert the body to a String
                    String jsonText = sc.useDelimiter("\\A").next();

                    //Read the ticketID field from the body
                    int ticketId = mapper.readValue(jsonText, Ticket.class).getTicketID();

                    //Update Ticket object
                    checkIn(ticketId);

                } catch (IOException e) {
                    //Log any exceptions
                    FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
                }
                break;

            //Delete a Ticket object from the database
            case "cancel":
                try {
                    //Convert the body to a String
                    String jsonText = sc.useDelimiter("\\A").next();

                    //Get the ticketID field from the body
                    int ticketId = mapper.readValue(jsonText, Ticket.class).getTicketID();

                    //Delete the Ticket object
                    cancel(ticketId);

                } catch (IOException e) {
                    //Log any exceptions
                    FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
                }
                break;
        }
    }
}