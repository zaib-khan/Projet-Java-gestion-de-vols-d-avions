package dataAccess;

import business.PlaneDAO;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Plane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaneDBAccess implements PlaneDAO {

    public ArrayList<Plane> getAllPlanes() throws ConnexionException, OperationDataException {
        ArrayList<Plane> planes = new ArrayList<Plane>();
        Integer plane_id,number_seats;
        String plane_type;
        Boolean has_business_class, has_first_class;
        Plane plane;

        try{
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from plane");
            ResultSet data = statement.executeQuery();
            while (data.next()){
                plane_id = data.getInt("plane_id");
                number_seats = data.getInt("number_seats");
                plane_type = data.getString("plane_type");
                has_business_class = data.getBoolean("has_business_class");
                has_first_class = data.getBoolean("has_first_class");
                plane = new Plane(plane_id,number_seats,plane_type,has_business_class,has_first_class);
                planes.add(plane);
            }
            return planes;
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public Plane getPlaneBy_id(Integer plane_id)throws ConnexionException,OperationDataException{
        int number_seats;
        String plane_type;
        Boolean has_business_class;
        Boolean has_first_class;

        if(plane_id == null) return null;
        else{
            try{
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("select * from plane where plane_id = ?");
                statement.setInt(1,plane_id);
                ResultSet data = statement.executeQuery();
                if(data.next()){
                    number_seats = data.getInt("number_seats");
                    plane_type = data.getString("plane_type");
                    has_business_class = data.getBoolean("has_business_class");
                    has_first_class = data.getBoolean("has_first_class");
                    return new Plane(plane_id, number_seats, plane_type, has_business_class, has_first_class);
                }
                else return null;
            }
            catch (SQLException e){
                throw new OperationDataException();
            }
        }
    }

}

