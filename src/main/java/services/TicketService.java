package services;

import Models.Flight;
import Models.ModTicket;
import Models.Ticket;
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
        TicketRepo.purchaseTicket(FlightRepo.getFlightByNum(flightId), UserRepo.getUserByNum(userId));
    }

    public static int testMeth() {
        return 1;
    }

    public static String getAllTickets(int use) throws JsonProcessingException {
        List<ModTicket> retList = new LinkedList<>();
        List<Ticket> repoRet = TicketRepo.getMyTickets(use);
        for (int i = 0; i < repoRet.size(); i++) {
            retList.add(new ModTicket(repoRet.get(i).getTicketID(),
                    repoRet.get(i).getFlight().getFlightNumber(),
                    repoRet.get(i).isCheck_IN()));
        }
        String ret = mapper.writeValueAsString(retList);
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
                    purchaseFlight(userId, flightId);
                    System.out.println(testMeth());

                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
        }
    }

    public static String viewTicketManager(HttpServletRequest req) {
        String header = req.getHeader("header");
        int userId = Integer.parseInt(req.getHeader("user"));
        switch (header) {
            case "view-my-ticket":
                try {
                    return getAllTickets(userId);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
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