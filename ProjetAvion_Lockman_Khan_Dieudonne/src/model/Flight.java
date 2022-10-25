package model;


import java.util.Date;
import java.util.ArrayList;

public class Flight {

    private Integer flight_id;
    private Date departure_time;
    private Integer estimated_duration;
    private Integer real_duration;
    private Double price_adult_eco;
    private Double price_adult_business;
    private Double price_adult_first_class;
    private Double price_child;
    private Double cancel_insurance_price;
    private Boolean has_wifi;
    private String commentary;
    private Plane plane;
    private Airport departure_airport;
    private Airport arrival_airport;
    private Meal meal;

    public Flight(Integer flight_id,Date departure_time,Integer estimated_duration,Integer real_time, Double price_adult_eco,Double price_adult_business,
                  Double price_adult_first_class,Double price_child,Double cancel_insurance_price, Boolean has_wifi,
                  String commentary,Plane plane, Airport arrival_airport,Airport departure_airport,Meal meal){
        setFlight_id(flight_id);
        setDeparture_time(departure_time);
        setEstimated_duration(estimated_duration);
        setReal_duration(real_time);
        setPrice_adult_eco(price_adult_eco);
        setPrice_adult_business(price_adult_business);
        setPrice_adult_first_class(price_adult_first_class);
        setPrice_child(price_child);
        setCancel_insurance_price(cancel_insurance_price);
        setHas_wifi(has_wifi);
        setCommentary(commentary);
        setPlane(plane);
        setArrival_airport(arrival_airport);
        setDeparture_airport(departure_airport);
        setMeal(meal);
    }

    public Flight(Integer flight_id,Date departure_time,Integer estimated_duration, Double price_adult_eco,Double price_adult_business,
                  Double price_adult_first_class,Double price_child,Double cancel_insurance_price, Boolean has_wifi,
                  String commentary,Plane plane, Airport arrival_airport,Airport departure_airport,Meal meal){
        this(flight_id,departure_time,estimated_duration,null,price_adult_eco,price_adult_business,price_adult_first_class,
                price_child,cancel_insurance_price,has_wifi,commentary,plane,arrival_airport,departure_airport,meal);
    }

    public Flight(Date departure_time,Integer estimated_duration,Double price_adult_eco,Double price_adult_business,
                  Double price_adult_first_class,Double price_child,Double cancel_insurance_price, Boolean has_wifi,
                  String commentary,Plane plane, Airport arrival_airport,Airport departure_airport,Meal meal){
        this(null,departure_time,estimated_duration,price_adult_eco,price_adult_business,price_adult_first_class,
                price_child,cancel_insurance_price,has_wifi,commentary,plane,arrival_airport,departure_airport,meal);
    }

    //Getter

    public Integer getFlight_id() {
        return flight_id;
    }

    public Date getDeparture_time() {
        return departure_time;
    }

    public Integer getEstimated_duration() {
        return estimated_duration;
    }

    public Integer getReal_duration() {
        return real_duration;
    }

    public Double getPrice_adult_eco() {
        return price_adult_eco;
    }

    public Double getPrice_adult_business() {
        return price_adult_business;
    }

    public Double getPrice_adult_first_class() {
        return price_adult_first_class;
    }

    public Double getPrice_child() {
        return price_child;
    }

    public Double getCancel_insurance_price() {
        return cancel_insurance_price;
    }

    public Boolean isHas_wifi() {
        return has_wifi;
    }

    public String getCommentary() {
        return commentary;
    }

    public Plane getPlane() {
        return plane;
    }

    public Airport getDeparture_airport() {
        return departure_airport;
    }

    public Airport getArrival_airport() {
        return arrival_airport;
    }

    public Meal getMeal() {
        return meal;
    }

    //Setter

    public void setDeparture_time(Date departure_time){
        this.departure_time = departure_time;
    }

    public void setFlight_id(Integer flight_id){
        this.flight_id = flight_id;
    }

    public void setEstimated_duration(Integer estimated_duration){
        this.estimated_duration = estimated_duration;
    }

    public void setReal_duration(Integer real_duration){
        this.real_duration = real_duration;
    }

    public void setPrice_adult_eco(Double price_adult_eco){
        this.price_adult_eco = price_adult_eco;
    }

    public void setPrice_adult_business(Double price_adult_business){
        this.price_adult_business = price_adult_business;
    }

    public void setPrice_adult_first_class(Double price_adult_first_class){
        this.price_adult_first_class = price_adult_first_class;
    }

    public void setPrice_child(Double price_child){
        this.price_child = price_child;
    }

    public void setCancel_insurance_price(Double cancel_insurance_price){
        this.cancel_insurance_price = cancel_insurance_price;
    }

    public void setHas_wifi(Boolean has_wifi){
        this.has_wifi =has_wifi;
    }

    public void setCommentary(String commentary){
        this.commentary = commentary;
    }

    public void setPlane(Plane plane){
        this.plane = plane;
    }

    public void setDeparture_airport(Airport departure_airport){
        this.departure_airport = departure_airport;
    }

    public void setArrival_airport(Airport arrival_airport){
        this.arrival_airport =  arrival_airport;
    }

    public void setMeal(Meal meal){
        this.meal = meal;
    }

    public ArrayList<String> getListClass(){
        ArrayList<String> listClass = new ArrayList<>();
        if(getPrice_adult_eco() != 0) listClass.add("Economy Class");
        if(getPrice_adult_business() != 0) listClass.add("Business Class");
        if(getPrice_adult_first_class() != 0) listClass.add("First class");
        return listClass;
    }

}
