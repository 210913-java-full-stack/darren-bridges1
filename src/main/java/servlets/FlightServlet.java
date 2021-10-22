package servlets;


import services.FlightService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlightServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        List<Flight> flightList = repos.FlightRepo.getAvail();
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writeValueAsString(flightList));
//        resp.getWriter().write(mapper.writeValueAsString(flightList));
//        resp.setContentType("application/json");
//        resp.setStatus(200);

        resp.getWriter().write(FlightService.viewFlightManager(req));
        resp.setContentType("application/json");
        resp.setStatus(200);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            FlightService.postRequestManager(req);

    }
}
