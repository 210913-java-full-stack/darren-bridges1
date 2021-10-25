package repos;


import Models.Flight;
import Models.User;
import services.GlobalStore;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserRepo {
    public static User getUserByNum(int userId) {
        return GlobalStore.getSession().get(User.class, userId);
    }

    public static User getUser(String username) {
        CriteriaBuilder build = GlobalStore.getSession().getCriteriaBuilder();
        CriteriaQuery<User> query = build.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(build.equal( root.get("username"), username) );
        return GlobalStore.getSession().createQuery(query).getResultList().get(0);
    }








}
