package services;

import Models.User;
import Util.FileLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import repos.UserRepo;
import javax.servlet.http.HttpServletRequest;


/**
 * The UserService class implements the sole method from the UserRepo and does so
 * in order to validate a user. It does so to send a user object to the front end
 * that can be persisted across web pages.
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


 public class UserService {

    //Create ObjectMapper to convert objects to JSON Strings
    private static ObjectMapper mapper = new ObjectMapper();

    /*
    This method interprets GET requests sent to the UserServlet
     */
     public static String viewUser(HttpServletRequest req) {
         //Read the header to denote the type of GET request
         String header = req.getHeader("header");

         switch(header) {
             //Get a User object from the database based on username field
             case "view-ind-user":
                 try {
                     //Get User object from database
                     User user = UserRepo.getUser(req.getHeader("Username"));

                     //Convert the User object to a JSON String and return it
                     return mapper.writeValueAsString(user);

                 } catch (JsonProcessingException e) {
                     //Log any exceptions
                     FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
                 }
                 break;

             //Receive a username and password and check if it exists in the database
             case "validate-user":
                 //Check to see if the username is valid
                 User dbCheck = UserRepo.getUser(req.getHeader("Username"));

                 //If username is not valid, log in fails
                 if (dbCheck == null) {
                     return null;

                 //Checks to see if the Password for that username matches the one stored in the database.
                 //If not, log in fails
                 } else if (! dbCheck.getPassword().equals(req.getHeader("Password"))) {
                     return null;
                 }

                 //If both checks pass, the User object is retrieved from the
                 // database and sent back to the front end
                 try {
                     //The User object is returned as a JSON String
                     return mapper.writeValueAsString(dbCheck);

                 } catch (JsonProcessingException e) {
                     //Log any exceptions
                     FileLogger.getFileLogger().writeLog(e.getMessage(), 0);
                 }
         }
         //Return alternative result
         return null;
     }
}




