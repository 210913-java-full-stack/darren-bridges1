package Models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="User_Table")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String roleID;

    @OneToMany(mappedBy = "user")
    private List<Ticket> ticketList = new LinkedList<>();

    public User() {
    }

    public User(int userID, String username, String password, String roleID) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.roleID = roleID;
    }

    public User(int id, String username, String hash, int roleId) {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public void buyTicket(Ticket ticket) {
        ticketList.add(ticket);
        ticket.setUser(this);
    }


    public int getId() {
        return 0;
    }

}
