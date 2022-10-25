package model;

import exception.NameException;
import exception.PriceException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Meal {

    private Integer meal_id;
    private String meal_name;
    private Boolean is_hot;
    private Double price_adult;
    private Double price_child;
    private Integer spicy_level;
    private GregorianCalendar availibility_date;
    private Country country;

    private static Integer NOTSPICY = 0;
    private static Integer LOW_SPICY = 1;
    private static Integer MEDIUM_SPICY = 2;
    private static Integer HIGH_SPICY = 3;

    public Meal(){}



    public Meal(Integer meal_id, String meal_name, Boolean is_hot, Double price_adult, Integer spicy_level, GregorianCalendar availibility_date,Country country) throws NameException, PriceException {
        setMeal_id(meal_id);
        setMeal_name(meal_name);
        setIs_hot(is_hot);
        setPrice_adult(price_adult);
        setSpicy_level(spicy_level);
        setAvailibility_date(availibility_date);
        setCountry(country);
    }
    public Meal(Integer meal_id, String meal_name, Boolean is_hot, Double price_adult, Double price_child, Integer spicy_level, GregorianCalendar availibility_date,Country country) throws NameException, PriceException {
        this(meal_id,meal_name,is_hot,price_adult,spicy_level,availibility_date,country);
        setPrice_child(price_child);
    }



    //Getter

    public Integer getMeal_id() {
        return meal_id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public Boolean is_hot() {
        return is_hot;
    }

    public Double getPrice_adult() {
        return price_adult;
    }

    public Double getPrice_child() {
        return price_child;
    }

    public Integer getSpicy_level() {
        return spicy_level;
    }

    public GregorianCalendar getAvailibility_date() {
        return availibility_date;
    }

    public Country getCountry(){
        return country;
    }
    public static Integer getNOTSPICY(){
        return NOTSPICY;
    }
    public static Integer getLowSpicy(){
        return LOW_SPICY;
    }
    public static Integer getMediumSpicy(){
        return MEDIUM_SPICY;
    }
    public static Integer getHighSpicy(){
        return HIGH_SPICY;
    }

    //Setter

    public void setMeal_id(Integer meal_id){
        this.meal_id = meal_id;
    }

    public void setMeal_name(String meal_name) throws NameException {
        if(!Tools.isAlphaNumeric(meal_name)) throw new NameException(meal_name);
        this.meal_name = meal_name;
    }

    public void setIs_hot(boolean is_hot){
        this.is_hot = is_hot;
    }

    public void setPrice_adult(Double price_adult) throws PriceException {
        if(!Tools.isValidPrice(price_adult.toString())) throw new PriceException(price_adult.toString());
        this.price_adult = price_adult;
    }

    public void setPrice_child(Double price_child) throws PriceException {
        if(!Tools.isValidPrice(price_child.toString())) throw new PriceException(price_child.toString());
        this.price_child = price_child;
    }


    public void setSpicy_level(Integer spicy_level){
        this.spicy_level = spicy_level;
    }

    public void setAvailibility_date(GregorianCalendar availibility_date){
        this.availibility_date = availibility_date;
    }

    public Date getSimpleDate(){
        return getAvailibility_date().getTime();
    }

    public void setCountry(Country country){
        this.country = country;
    }

    public String getMealNameBetwenQuotationMarks(){
        return "\""+getMeal_name()+"\"";
    }

    public String messageBeforeRemoval(){
        String res = "Meal name : "+ getMeal_name()+"\n";
        res+= "Is the meal hot :";
        res+= (is_hot)?" yes":" no";
        res +="\n";
        res += "Adulte price : "+ getPrice_adult()+"\n";
        res += "Child price : "+ getPrice_child()+"\n";
        res += "Spice lavel : "+ getSpicy_level()+"\n";
        res += "Country name : "+ getCountry().getCountry_name()+"\n";
        res += "Availibility date : "+ getAvailibility_date().get(Calendar.DATE)+"/"+(getAvailibility_date().get(Calendar.MONTH)+1)+"/"+getAvailibility_date().get(Calendar.YEAR)+"\n";
        return res;
    }

    public String toString(){
       return "The meal "+getMeal_name();
    }




}


