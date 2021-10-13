package Models;

public class Ticket {
    private int ticketID;
    private int userID;
    private int flightNumber;
    private String check_IN;

    public Ticket(int ticketID, int userID, int flightNumber, String check_IN) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.flightNumber = flightNumber;
        this.check_IN = check_IN;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCheck_IN() {
        return check_IN;
    }

    public void setCheck_IN(String check_IN) {
        this.check_IN = check_IN;
    }
}
