package business;

import dataAccess.CountryDBAccess;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;

import java.util.ArrayList;

public class BusinessCountry {

    private CountryDAO dao;

    public BusinessCountry(){
        this.dao = new CountryDBAccess();
    }

    public ArrayList<Country> getAll_country() throws ConnexionException, OperationDataException {
        return dao.getAll_country();
    }




}
