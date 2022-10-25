package dataAccess;

import business.CountryDAO;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryDBAccess implements CountryDAO {

    public Country getCountryBy_id(Integer country_id)throws ConnexionException, OperationDataException {
        String country_name;

        if(country_id == null) return null;
        else{
            try{
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("select * from Country where country_id = ?");
                statement.setInt(1,country_id);
                ResultSet data = statement.executeQuery();
                if(data.next()){
                    country_name = data.getString("country_name");
                    return new Country(country_id,country_name);
                }
                else return null;
            }
            catch (SQLException sqlException){
                throw new OperationDataException();
            }
        }
    }

    public ArrayList<Country> getAll_country() throws ConnexionException, OperationDataException {

        ArrayList<Country> listCountry = new ArrayList<>();
        try {

            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from country order by country_id");

            ResultSet res = statement.executeQuery();

            while (res.next()){
                listCountry.add(new Country(res.getInt("country_id"),res.getString("country_name")));
            }
            return listCountry;
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }
}
