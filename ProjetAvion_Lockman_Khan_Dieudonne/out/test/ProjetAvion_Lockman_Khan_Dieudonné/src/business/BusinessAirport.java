package business;

import dataAccess.AirportDBAccess;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Airport;

import java.util.ArrayList;

public class BusinessAirport {

    private AirportDAO dao;

    public BusinessAirport(){
        this.dao = new AirportDBAccess();
    }

    public ArrayList<Airport> getAirportByCountry(Integer country_id) throws ConnexionException, OperationDataException {
        return dao.getAirportByCountry(country_id);
    }
}
