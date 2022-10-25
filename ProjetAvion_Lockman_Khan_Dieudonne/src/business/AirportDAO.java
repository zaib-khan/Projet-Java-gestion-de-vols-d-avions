package business;

import exception.ConnexionException;
import exception.OperationDataException;
import model.Airport;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;

public interface AirportDAO {

    ArrayList<Airport> getAirportByCountry(Integer country_id)throws ConnexionException, OperationDataException;

    Airport getAirportBy_id(Integer airport_id)throws ConnexionException, OperationDataException;

}
