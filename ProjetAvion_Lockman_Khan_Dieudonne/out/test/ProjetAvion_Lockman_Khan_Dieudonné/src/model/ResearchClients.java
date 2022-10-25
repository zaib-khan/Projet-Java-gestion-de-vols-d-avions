package model;

import java.util.Date;

public class ResearchClients {

    private String first_name;
    private String last_name;
    private Date departure_time;

    public ResearchClients(String first_name, String last_name, Date departure_time) {
        setDeparture_time(departure_time);
        setFirst_name(first_name);
        setLast_name(last_name);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Date departure_time) {
        this.departure_time = departure_time;
    }
}