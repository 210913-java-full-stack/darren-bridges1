package Models;


import javax.persistence.*;

@Entity
@Table(name="Ticket_Table")
public class Ticket {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketID;

    @Column
    private boolean check_IN;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Ticket() {
        this.check_IN = false;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }


    public boolean isCheck_IN() {
        return check_IN;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String toString() {
        return this.ticketID + "";
    }
}
