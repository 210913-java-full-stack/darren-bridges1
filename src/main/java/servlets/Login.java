package servlets;

import Models.Flight;
import Models.User;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import services.FlightService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends HttpServlet {
    /*
    This is a sample servlet get function, we need to edit
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hi");

//        User u = new User(1, "username", "password", "user");
//        String useIn = req.getParameter("username");
//        String passIn = req.getParameter("password");
//        if (useIn.equals(u.getUsername()) && passIn.equals(u.getPassword())) {
//            resp.getWriter().print("Welcome User");
//        } else {
//            resp.getWriter().print("Invalid Username or password");
//        }
//
//
//        Flight test = new Flight();
//        Flight test2 = new Flight("dallas", "NYC", true);
//
//        Transaction transaction = FlightService.getSession().beginTransaction();
//        FlightService.getSession().save(test);
//        FlightService.getSession().save(test2);
//        transaction.commit();


    }
}