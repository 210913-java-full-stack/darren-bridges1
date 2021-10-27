package services;

import Models.Flight;
import Models.ModTicket;
import Models.Ticket;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

public class TicketService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void purchaseFlight(int userId, int flightId) {
        TicketRepo.purchaseTicket(FlightRepo.getExFlight(flightId), UserRepo.getUserByNum(userId));
    }


    public static String getFlightTickets(int flight) {
        System.out.println("Flight number: " + flight);
        List<User> ret = new LinkedList<>();
        List<Ticket> repoRet = TicketRepo.getTicketsByFlight(2);
        String stringRet = null;
        System.out.println("repoRet size: " + repoRet.size());
        for (int i = 0; i < repoRet.size(); i++) {
            ret.add(repoRet.get(i).getUser());
        }
        try {
            stringRet = mapper.writeValueAsString(ret);
            System.out.println("stringRet: " + stringRet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringRet;
    }

    public static String getAllTickets(int use) {
        List<ModTicket> retList = new LinkedList<>();
        List<Ticket> repoRet = TicketRepo.getMyTickets(use);
        for (int i = 0; i < repoRet.size(); i++) {
            retList.add(new ModTicket(repoRet.get(i).getTicketID(),
                    repoRet.get(i).getFlight().getFlightNumber(),
                    repoRet.get(i).isCheck_IN()));
        }
        String ret = null;
        try {
            ret = mapper.writeValueAsString(retList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static void checkIn(int ticketNum) {
        TicketRepo.checkIn(ticketNum);
    }

    private static void cancel(int ticketNum) {
        TicketRepo.cancelTicket(ticketNum);
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
        switch (header) {
            case "purchase-ticket":
                try {
                    String jsonText = sc.useDelimiter("\\A").next();
                    int userId = Integer.parseInt(req.getHeader("user"));
                    int flightId = mapper.readValue(jsonText, Flight.class).getFlightNumber();
                    System.out.println(userId);
                    System.out.println(flightId);

                    purchaseFlight(userId, flightId);

                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
        }
    }

    public static String viewTicketManager(HttpServletRequest req) {
        String header = req.getHeader("header");

        switch (header) {
            case "view-my-ticket":
                int userId = Integer.parseInt(req.getHeader("user"));
                return getAllTickets(userId);
            case "view-tickets-of-flight":
                int flightId = Integer.parseInt(req.getHeader("flight"));
                return getFlightTickets(flightId);
        }

        return "noSuchTickets";
    }

    public static void putRequestManager(HttpServletRequest req) {
        String header = req.getHeader("header");
        InputStream requestBody = null;
        try {
            requestBody = req.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(requestBody, StandardCharsets.UTF_8.name());
        switch (header) {
            case "check-in":
                try {
                    String jsonText = sc.useDelimiter("\\A").next();
                    int ticketId = mapper.readValue(jsonText, Ticket.class).getTicketID();
                    checkIn(ticketId);
                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
            case "cancel":
                try {
                    String jsonText = sc.useDelimiter("\\A").next();
                    int ticketId = mapper.readValue(jsonText, Ticket.class).getTicketID();
                    cancel(ticketId);
                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
        }
    }
}