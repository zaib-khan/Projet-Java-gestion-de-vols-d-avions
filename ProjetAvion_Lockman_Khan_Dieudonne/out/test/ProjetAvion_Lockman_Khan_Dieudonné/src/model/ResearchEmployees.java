package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class ResearchEmployees {

    private String first_name;
    private String last_name;
    private String role;
    private String city_name;
    private GregorianCalendar departure_time;

    public ResearchEmployees(String first_name, String last_name, String role, String city_name, GregorianCalendar departure_time) {
        setCity_name(city_name);
        setDeparture_time(departure_time);
        setFirst_name(first_name);
        setLast_name(last_name);
        setRole(role);
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Date getDeparture_time() {
        return departure_time.getTime();
    }

    public void setDeparture_time(GregorianCalendar departure_time) {
        this.departure_time = departure_time;
    }
}
