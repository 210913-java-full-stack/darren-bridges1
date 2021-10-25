package services;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
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
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public UserService(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    public void registerUser(User aUser, String propFilePath) {

        Properties p = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(propFilePath);
            p.load(fis);
            p.setProperty(aUser.getUsername(), aUser.getPassword());
            p.store(new FileOutputStream(propFilePath), null);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean validateUser(User aUser, String propFilePath){
        boolean success = true;
        Properties p = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(propFilePath);

            p.load(fis);

            // Checks whether the username exists or not
            if(!p.containsKey(aUser.getUsername())) {
                // Link-redirection
                success = false;
            }  else { // Checks whether the password matches or not
                String pword = p.getProperty(aUser.getUsername());
                if(!pword.equals(aUser.getPassword())) {
                    success = false; // Link-redirection
                } else {
                    success = true; // Link-redirection
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            success = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            success = false;
        } finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        return success;
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




