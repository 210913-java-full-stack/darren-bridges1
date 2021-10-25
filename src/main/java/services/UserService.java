package services;


import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import repos.UserRepo;

import javax.servlet.http.HttpServletRequest;


public class UserService {
    private static ObjectMapper mapper = new ObjectMapper();

    private static User getUser(String username) {
        return UserRepo.getUser(username);
    }

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


        }
        return "NoSuchFlight";
    }

}


