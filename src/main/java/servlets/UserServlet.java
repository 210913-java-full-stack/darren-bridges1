package servlets;

import javax.servlet.ServletException;
import services.UserService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The UserServlet class handles all requests pertaining to User resources
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = UserService.viewUser(req);
        System.out.println(string);
        resp.getWriter().write(string);
        resp.setContentType("application/json");
        resp.setStatus(200);
    }
}


