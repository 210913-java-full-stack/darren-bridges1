package services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

 class User {

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
    public User(String userName, String password) {
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
            p.setProperty(aUser.getUserName(), aUser.getPassword());
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
            if(!p.containsKey(aUser.getUserName())) {
                // Link-redirection
                success = false;
            }  else { // Checks whether the password matches or not
                String pword = p.getProperty(aUser.getUserName());
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
}





