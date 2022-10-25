package dataAccess;

import business.ClientDAO;
import exception.ConnexionException;
import exception.FirstNameException;
import exception.NameException;
import exception.OperationDataException;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ClientDBAccess implements ClientDAO {


    public ArrayList<Client> getAll_client() throws ConnexionException, OperationDataException,NameException, FirstNameException {
        GregorianCalendar birthdate;
        ArrayList<Client> listClient = new ArrayList<>();
        Client client;
        String last_name = null;
        String first_name = null;
        try {

            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from client order by first_name");

            ResultSet data = statement.executeQuery();

            while (data.next()){
                int client_id = data.getInt("client_id");
                first_name = data.getString("first_name");
                last_name = data.getString("last_name");
                java.sql.Date sqlDate = data.getDate("birthdate");
                birthdate = new GregorianCalendar();
                birthdate.setTime(sqlDate);
                client = new Client(client_id,first_name,last_name,birthdate);
                listClient.add(client);
            }
            return listClient;
        }
        catch (SQLException sqlException) {
            throw new OperationDataException();
        }
        catch (NameException exception){
            throw  new NameException(last_name);
        }
        catch (FirstNameException exception){
            throw new FirstNameException(first_name);
        }

    }

    public Client getClientBy_id(Integer client_id) throws ConnexionException,OperationDataException,NameException, FirstNameException {
        String first_name = null;
        String last_name = null;
        java.sql.Date sqlDate;
        GregorianCalendar birthdate;

        if(client_id == null) return null;
        else{
            try{
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("select * from client where client_id = ?");
                statement.setInt(1,client_id);
                ResultSet data = statement.executeQuery();
                if(data.next()){
                    first_name = data.getString("first_name");
                    last_name = data.getString("last_name");
                    sqlDate = data.getDate("birthdate");
                    birthdate = new GregorianCalendar();
                    birthdate.setTime(sqlDate);
                    return new Client(client_id, first_name, last_name, birthdate);

                }
                return null;
            }
            catch (SQLException sqlException){
                throw new OperationDataException();
            }
            catch (NameException exception){
                throw new NameException(last_name);
            }
            catch (FirstNameException exception){
                throw new FirstNameException(first_name);
            }
        }
    }
}
