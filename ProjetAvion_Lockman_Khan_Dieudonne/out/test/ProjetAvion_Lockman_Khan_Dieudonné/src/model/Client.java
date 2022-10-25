package model;

import exception.FirstNameException;
import exception.NameException;

import java.util.GregorianCalendar;

import static model.Tools.isNameValid;

public class Client {

    private Integer client_id;
    private String first_name;
    private String last_name;
    private GregorianCalendar birthdate;

    //Constructeur
    public Client(Integer client_id, String first_name, String last_name, GregorianCalendar birthdate) throws NameException,FirstNameException {
        setClient_id(client_id);
        setBirthdate(birthdate);
        setFirst_name(first_name);
        setLast_name(last_name);
    }

    //Getter
    public Integer getClient_id(){
        return client_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public GregorianCalendar getBirthdate() {
        return birthdate;
    }

    //Setter

    public void setBirthdate(GregorianCalendar birthdate) {
        this.birthdate = birthdate;
    }

    public void setFirst_name(String first_name) throws NameException {
       if (!isNameValid(first_name)){
           throw new NameException(first_name);
       }
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) throws FirstNameException{
        if (!isNameValid(last_name)){
            throw new FirstNameException(last_name);
        }
        this.last_name = last_name;
    }

    public void setClient_id(Integer client_id){
        this.client_id = client_id;
    }
}
