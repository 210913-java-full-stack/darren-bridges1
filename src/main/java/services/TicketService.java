package services;

import Models.Flight;
import Models.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import repos.FlightRepo;
import repos.TicketRepo;
import repos.UserRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TicketService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void purchaseFlight(int userId, int flightId) {
        TicketRepo.purchaseTicket(FlightRepo.getFlightByNum(flightId), UserRepo.getUserByNum(userId));
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

                } catch (IOException e) {
                    e.printStackTrace(); //Add e logger
                }
                break;
        }
    }
}