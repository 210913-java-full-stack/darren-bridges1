package servlets;


import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import services.FlightService;
import services.UserService;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static UserServlet userServlet = new UserServlet();

//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            PrintWriter out = resp.getWriter();
//
//            List<User> users = UserServlet.getAllUsers();
//            String usersJSON = mapper.writeValueAsString(users);
//            resp.setStatus(200);
//            out.write(usersJSON);
//
//        } catch (NumberFormatException nfe) {
////			log.error(nfe.getMessage());
//            resp.setStatus(400);
//        } catch (Exception e) {
//            e.printStackTrace();
////		log.error(e.getMessage());
//            resp.setStatus(500);
//        }
//
//    }
//
//    private static List<User> getAllUsers() {
//        return null;
//    }
//
//}


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = UserService.viewUser(req);
        System.out.println(string);
        resp.getWriter().write(string);
        resp.setContentType("application/json");
        resp.setStatus(200);

    }
}


