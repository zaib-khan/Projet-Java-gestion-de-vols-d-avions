package model;

public class Plane {

    private Integer plane_id;
    private Integer number_seats;
    private String plane_type;
    private Boolean has_business_class;
    private Boolean has_first_class;

    public Plane(Integer plane_id,Integer number_seats,String plane_type,Boolean has_business_class, Boolean has_first_class){
        setHas_business_class(has_business_class);
        setHas_first_class(has_first_class);
        setNumber_seats(number_seats);
        setPlane_id(plane_id);
        setPlane_type(plane_type);
    }

    //Getter
    public Integer getPlane_id() {
        return plane_id;
    }

    public Integer getNumber_seats() {
        return number_seats;
    }

    public String getPlane_type() {
        return plane_type;
    }

    public Boolean has_business_class() {
        return has_business_class;
    }

    public Boolean has_first_class() {
        return has_first_class;
    }

    //Setter

    public void setPlane_id(Integer plane_id) {
        this.plane_id = plane_id;
    }

    public void setNumber_seats(Integer number_seats) {
        this.number_seats = number_seats;
    }


    public void setPlane_type(String plane_type) {
        this.plane_type = plane_type;
    }

    public void setHas_business_class(Boolean has_business_class) {
        this.has_business_class = has_business_class;
    }

    public void setHas_first_class(Boolean has_first_class) {
        this.has_first_class = has_first_class;
    }
}
