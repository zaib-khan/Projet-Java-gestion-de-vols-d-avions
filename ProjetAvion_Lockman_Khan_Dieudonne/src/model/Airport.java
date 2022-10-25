package model;

public class Airport {

    private Integer airport_id;
    private String city_name;
    private Country country;

    //Constructeur
    public Airport(Integer airport_id,String city_name, Country country){
        setAirport_id(airport_id);
        setCity_name(city_name);
        setCountry(country);
    }

    //Getter

    public Integer getAirport_id() {
        return airport_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public Country getCountry() {
        return country;
    }

    //Setter

    public void setAirport_id(Integer airport_id) {
        this.airport_id = airport_id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
