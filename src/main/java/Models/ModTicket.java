package Models;


public class ModTicket {
    private int ticketId;
    private int flightNum;
    private boolean checkIn;

    public ModTicket() {
    }

    public ModTicket(int ticketId, int flightNum, boolean checkIn) {
        this.ticketId = ticketId;
        this.flightNum = flightNum;
        this.checkIn = checkIn;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(int flightNum) {
        this.flightNum = flightNum;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }
}
