package model;

import java.util.Date;

public class Employee {

    private Integer employee_id;
    private String role;
    private String first_name;
    private String last_name;
    private Date birthdate;

    //Constructeur
    public Employee(Integer employee_id, String role,String first_name, String last_name, Date birthdate) {
        setEmployee_id(employee_id);
        setBirthdate(birthdate);
        setFirst_name(first_name);
        setLast_name(last_name);
        setRole(role);
    }

    //Getter
    public Integer getEmployee_id(){
        return employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getRole(){return role;}

    //Setter

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmployee_id(Integer employee_id){
        this.employee_id = employee_id;
    }

    public void setRole(String role){this.role = role;}

    @Override
    public String toString(){
        return first_name + " " + last_name;
    }
}
