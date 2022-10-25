package model;

public class Country {

    private Integer country_id;
    private String country_name;

    //Constructeur
    public Country(Integer country_id,String country_name){
        setCountry_id(country_id);
        setCountry_name(country_name);
    }

    //Getter

    public Integer getCountry_id() {
        return country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    //Setter

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    @Override
    public String toString(){
        return country_name;
    }
}
