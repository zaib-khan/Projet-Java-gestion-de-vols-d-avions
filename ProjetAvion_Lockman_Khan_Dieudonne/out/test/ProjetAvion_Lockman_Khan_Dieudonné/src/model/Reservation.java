package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Reservation {

    private Integer reservation_id;
    private String class_flight;
    private Integer number_adult;
    private Integer number_child;
    private Boolean has_cancel_insurance;
    private Integer nb_adult_meal;
    private Integer nb_child_meal;
    private GregorianCalendar reservation_date;
    private Client client;
    private Flight flight;

    public Reservation(Integer reservation_id, String class_flight, Integer number_adult, Integer number_child, Integer has_cancel_insurance,
                       Integer nb_adult_meal, Integer nb_child_meal,GregorianCalendar reservation_date, Client client, Flight flight) {
        setReservation_id(reservation_id);
        setClass_flight(class_flight);
        setNumber_adult(number_adult);
        setNumber_child(number_child);
        setHas_cancel_insurance(has_cancel_insurance);
        setNb_adult_meal(nb_adult_meal);
        setNb_child_meal(nb_child_meal);
        setReservation_date(reservation_date);
        setClient(client);
        setFlight(flight);
    }

    public Reservation(Integer reservation_id, String class_flight, Integer number_adult, Integer number_child, Integer has_cancel_insurance,
                       Integer nb_adult_meal, Integer nb_child_meal, Client client, Flight flight){
        this.reservation_date = new GregorianCalendar();
        setReservation_id(reservation_id);
        setClass_flight(class_flight);
        setNumber_adult(number_adult);
        setNumber_child(number_child);
        setHas_cancel_insurance(has_cancel_insurance);
        setNb_adult_meal(nb_adult_meal);
        setNb_child_meal(nb_child_meal);
        setClient(client);
        setFlight(flight);
    }

    public Reservation(String class_flight, Integer number_adult, Integer number_child, Integer has_cancel_insurance,
                       Integer nb_child_meal,GregorianCalendar reservation_date, Client client, Flight flight) {
        this(null,class_flight,number_adult,number_child,has_cancel_insurance,0,nb_child_meal,reservation_date,client,flight);
    }

    public Reservation(String class_flight, Integer number_adult, Integer number_child, Integer has_cancel_insurance,
                       GregorianCalendar reservation_date,Integer nb_adult_meal, Client client, Flight flight) {
        this(null,class_flight,number_adult,number_child,has_cancel_insurance,nb_adult_meal,0,reservation_date,client,flight);
    }

    public Reservation(String class_flight, Integer number_adult, Integer number_child, Integer has_cancel_insurance,
                       GregorianCalendar reservation_date, Client client, Flight flight) {
        this(null,class_flight,number_adult,number_child,has_cancel_insurance,0,0,reservation_date,client,flight);
    }

    //Getter

    public Integer getReservation_id() {
        return reservation_id;
    }

    public String getClass_flight() {
        return class_flight;
    }

    public Integer getNumber_adult() {
        return number_adult;
    }

    public Integer getNumber_child() {
        return number_child;
    }

    public Integer getNumber_person() {
        return number_adult + number_child;
    }

    public Boolean getHas_cancel_insurance() {
        return has_cancel_insurance;
    }

    public Integer getNb_adult_meal() {
        return nb_adult_meal;
    }

    public Integer getNb_child_meal() {
        return nb_child_meal;
    }

    public GregorianCalendar getReservation_date() {
        return reservation_date;
    }

    public Date getSimpleDate(){
        return getReservation_date().getTime();
    }

    public Client getClient() {
        return client;
    }

    public Flight getFlight() {
        return flight;
    }


    //Setter
    public void setReservation_id(Integer reservation_id){
        this.reservation_id = reservation_id;
    }

    public void setClass_flight(String class_flight){
        this.class_flight = class_flight;
    }

    public void setNumber_adult(Integer number_adult){
        this.number_adult = number_adult;
    }

    public void setNumber_child(Integer number_child){
        this.number_child = number_child;
    }

    public void setHas_cancel_insurance(Integer has_cancel_insurance){
        this.has_cancel_insurance = (has_cancel_insurance == 1);
    }

    public void setNb_adult_meal(Integer nb_adult_meal){
        this.nb_adult_meal = nb_adult_meal;
    }

    public void setNb_child_meal(Integer nb_child_meal){
        this.nb_child_meal = nb_child_meal;
    }

    public void setReservation_date(GregorianCalendar reservation_date){
        this.reservation_date = reservation_date;
    }

    public void setClient (Client client){
        this.client = client;
    }

    public void setFlight(Flight flight){
        this.flight = flight;
    }

    public String toString(){
        String res = "reservation : \n";
        res += "Client : "+getClient().getLast_name()+" "+getClient().getFirst_name()+" \n";
        res += "Flight : "+getFlight().getPlane().getPlane_type()+" ( from  "+getFlight().getDeparture_airport().getCity_name()+" to "+getFlight().getArrival_airport().getCity_name()+" ) in "+getClass_flight()+" \n";
        res += "Places : "+getNumber_adult()+ " adult and "+getNumber_child()+" child \n";
        res += "Meals : "+getNb_adult_meal()+ " adult and "+getNb_child_meal()+" child \n";
        res += (getHas_cancel_insurance()? "With" : "Without")+" cancel insurance \n";
        res += getReservation_date().get(Calendar.DAY_OF_MONTH)+"/"+(getReservation_date().get(Calendar.MONTH)+1)+"/"+getReservation_date().get(Calendar.YEAR);
        return res;
    }

}
