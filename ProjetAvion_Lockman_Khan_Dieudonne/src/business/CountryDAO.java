package business;

import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CountryDAO {

    Country getCountryBy_id(Integer country_id)throws ConnexionException,OperationDataException;

    ArrayList<Country> getAll_country() throws ConnexionException, OperationDataException;
}
