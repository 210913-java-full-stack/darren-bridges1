package services;

import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import repos.UserRepo;

import javax.servlet.http.HttpServletRequest;

 public class UserService {

    private static ObjectMapper mapper = new ObjectMapper();


     public static String viewUser(HttpServletRequest req) {
         String header = req.getHeader("header");
         switch(header) {
             case "view-ind-user":
                 try {
                     User user = UserRepo.getUser(req.getHeader("Username"));
                     return mapper.writeValueAsString(user);
                 } catch (JsonProcessingException e) {
                     e.printStackTrace(); //Add e logger
                 }
                 break;
             case "validate-user":
                 User dbCheck = UserRepo.getUser(req.getHeader("Username"));
                 if (dbCheck == null) {
                     return null;
                 } else if (! dbCheck.getPassword().equals(req.getHeader("Password"))) {
                     return null;
                 }
                 try {
                     return mapper.writeValueAsString(dbCheck);
                 } catch (JsonProcessingException e) {
                     e.printStackTrace();
                 }


         }
         return "NoSuchFlight";
     }
}




