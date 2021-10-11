package Models;

public class Flight {
    private String depart;
    private String arrive;
    private boolean available;
    private int flight_num;



    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getFlight_num() {
        return flight_num;
    }

    public void setFlight_num(int flight_num) {
        this.flight_num = flight_num;
    }

    public Flight(String depart, String arrive, boolean available, int flight_num) {
        this.depart = depart;
        this.arrive = arrive;
        this.available = available;
        this.flight_num = flight_num;
    }

    public Flight(String depart, String arrive, boolean available) {
        this.depart = depart;
        this.arrive = arrive;
        this.available = available;
    }
}
