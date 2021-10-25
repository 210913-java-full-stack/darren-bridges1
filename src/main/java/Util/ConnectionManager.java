package Util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static ConnectionManager cf = null;
    private static Connection conn;

    private ConnectionManager() {


    }

//    public static synchronized ConnectionManager getInstance() {
//
//        if (cf == null) cf = new ConnectionManager();
//        return cf;
//
//    }

    /*
     *
     * - The connection manages out a connection to the database
     * - and allows us to execute SQL statements and return results that have
     * - information about DB tables, stored procedures etc.
     */

    public static Connection getConnection() {

        if(conn == null) {
            //Connection conn = null;
            Properties prop = new Properties();
            String path = "src/main/resources/database.properties";
            //String path = "src/main/resources/database.properties";
            try {

                prop.load(new FileReader(path));

                //The following line of code uses reflection and the
                // .properties file in order to instantiate the driver
                // class listed in the file
                String connString = "jdbc:mariadb://" +
                        prop.getProperty("hostname") + ":" +
                        prop.getProperty("port") + "/" +
                        prop.getProperty("databaseName") + "?user=" +
                        prop.getProperty("user") + "&password=" +
                        prop.getProperty("password");

                conn = DriverManager.getConnection(connString);
                /*
                 * The DriverManager provides a basic service to manage a set of JDBC drivers.
                 * The DriverManager class will attempt to load
                 * the driver class referenced previously
                 */

//            conn = DriverManager.getConnection(
//                    prop.getProperty("url"),
//                    prop.getProperty("usr"),
//                    prop.getProperty("psw"));

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            catch (SQLException e) {

                e.printStackTrace();

            }
        }


        return conn;

    }
}

