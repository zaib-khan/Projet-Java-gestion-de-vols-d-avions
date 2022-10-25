package model;

import java.util.Date;
import java.util.GregorianCalendar;

public class ResearchFlights {

    private String departure_city_name;
    private String arrival_city_name;
    private Integer nb_seats_on_plane;
    private Integer nb_seats_reserved;
    private Date departure_time;
    private Integer flight_id;

    public ResearchFlights(String departure_city_name, String arrival_city_name, Integer nb_seats_on_plane,
                           Integer nb_seats_reserved, Date departure_time, Integer flight_id){
        setArrival_city_name(arrival_city_name);
        setDeparture_city_name(departure_city_name);
        setDeparture_time(departure_time);
        setFlight_id(flight_id);
        setNb_seats_on_plane(nb_seats_on_plane);
        setNb_seats_reserved(nb_seats_reserved);
    }

    public String getDeparture_city_name() {
        return departure_city_name;
    }

    public void setDeparture_city_name(String departure_city_name) {
        this.departure_city_name = departure_city_name;
    }

    public String getArrival_city_name() {
        return arrival_city_name;
    }

    public void setArrival_city_name(String arrival_city_name) {
        this.arrival_city_name = arrival_city_name;
    }

    public Integer getNb_seats_on_plane() {
        return nb_seats_on_plane;
    }

    public void setNb_seats_on_plane(Integer nb_seats_on_plane) {
        this.nb_seats_on_plane = nb_seats_on_plane;
    }

    public Integer getNb_seats_reserved() {
        return nb_seats_reserved;
    }

    public void setNb_seats_reserved(Integer nb_seats_reserved) {

        if (nb_seats_reserved == null) nb_seats_on_plane = 0;
        this.nb_seats_reserved = nb_seats_reserved;
    }

    public Date getDeparture_time() {
        return departure_time;
    }


    public void setDeparture_time(Date departure_time) {
        this.departure_time = departure_time;
    }

    public Integer getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Integer flight_id) {
        this.flight_id = flight_id;
    }
}
