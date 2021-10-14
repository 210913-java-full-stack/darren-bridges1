package servlets;

import Models.User;

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
        User u = new User(1, "username", "password", "user");
        String useIn = req.getParameter("username");
        String passIn = req.getParameter("password");
        if (useIn.equals(u.getUsername()) && passIn.equals(u.getPassword())) {
            resp.getWriter().print("Welcome User");
        } else {
            resp.getWriter().print("Invalid Username or password");
        }
    }
}