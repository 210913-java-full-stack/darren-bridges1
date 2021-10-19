package Models;

import javax.persistence.*;


@Entity
@Table(name="Flight_Table")
public class Flight {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightNumber;

    @Column
    private String depart;

    @Column
    private String arrive;

    @Column
    private boolean available;

    public Flight() {
    }

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

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Flight(String depart, String arrive, boolean available, int flight_num) {
        this.depart = depart;
        this.arrive = arrive;
        this.available = available;
        this.flightNumber = flight_num;
    }

    public Flight(String depart, String arrive, boolean available) {
        this.depart = depart;
        this.arrive = arrive;
        this.available = available;
    }
}
