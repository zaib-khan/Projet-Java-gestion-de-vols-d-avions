package business;

import dataAccess.ResearchDBAccess;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;
import model.ResearchClients;
import model.ResearchEmployees;
import model.ResearchFlights;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class BusinessResearch {

    private ResearchDAO dao;

    public BusinessResearch(){
        this.dao = new ResearchDBAccess();
    }

    public ArrayList<ResearchEmployees> researchEmployees(Country country, GregorianCalendar start, GregorianCalendar end)
            throws ConnexionException, OperationDataException {
        return dao.researchEmployees(country,start,end);
    }
    public ArrayList<ResearchFlights> researchFlights(Country country, Boolean isDestination) throws ConnexionException, OperationDataException {
        return dao.researchFlights(country, isDestination);
    }

    public ArrayList<ResearchClients> researchClients(Integer meal_id, Date start_date, Date end_date)
            throws ConnexionException,OperationDataException{
        return dao.researchClients(meal_id,start_date,end_date);
    }

}
