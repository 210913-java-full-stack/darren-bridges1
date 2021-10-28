package repos;

import Models.User;
import services.GlobalStore;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 * The UserRepo class contains the method that use hibernate to retrieve data about
 * a user from the database.
 *
 * @author Chris Oh and Darren Bridges
 * @version 1.0
 * @since 2021-10-27
 */


public class UserRepo {

    /*
    Method to retrieve a User object from the database based on the userID field
     */
    public static User getUserByNum(int userId) {
        return GlobalStore.getSession().get(User.class, userId);
    }

    /*
    Method to retrieve a User object from the database based on username field
     */
    public static User getUser(String username) {
        //Create CriteriaBuilder
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();

        //Create query for User object
        CriteriaQuery<User> query = build.createQuery(User.class);

        //Create root for query
        Root<User> root = query.from(User.class);

        //Retrieve user object based on username field
        query.select(root).where(build.equal( root.get("username"), username) );

        //Return user object
        return GlobalStore.getSession().createQuery(query).getSingleResult();
    }
}




