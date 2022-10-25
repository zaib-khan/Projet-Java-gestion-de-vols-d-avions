package dataAccess;

import business.AirportDAO;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Airport;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirportDBAccess implements AirportDAO {

    public ArrayList<Airport> getAirportByCountry(Integer country_id) throws ConnexionException,OperationDataException{
        ArrayList<Airport> airports = new ArrayList<Airport>();
        Airport airport;
        Integer airport_id;
        String city_name;
        Country country;

        if(country_id == null) return null;
        try {
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from Airport where country = ?");
            statement.setInt(1,country_id);
            ResultSet data = statement.executeQuery();
            while (data.next()){
                airport_id = data.getInt("airport_id");
                city_name = data.getString("city_name");
                country = new CountryDBAccess().getCountryBy_id(data.getInt("country"));
                airport = new Airport(airport_id,city_name,country);
                airports.add(airport);
            }
            return airports;
        }
        catch (SQLException sqlException){
            throw new OperationDataException();
        }
    }

    public Airport getAirportBy_id(Integer airport_id) throws ConnexionException,OperationDataException{
        String city_name;
        Country country;

        if(airport_id == null) return null;
        else{
            try{
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("select * from airport where airport_id = ?");
                statement.setInt(1,airport_id);
                ResultSet data = statement.executeQuery();
                if(data.next()){
                    city_name = data.getString("city_name");
                    country = new CountryDBAccess().getCountryBy_id(data.getInt("country"));
                    return new Airport(airport_id,city_name,country);
                }
                else return null;
            }
            catch (SQLException sqlException){
                throw new OperationDataException();
            }
        }
    }
}
