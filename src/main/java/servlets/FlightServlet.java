package servlets;

import Models.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FlightServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Flight> flightList = REPOs.FlightRepo.getAvail();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(flightList));
        resp.getWriter().write(mapper.writeValueAsString(flightList));
        resp.setContentType("application/json");
        resp.setStatus(200);

    }
}
