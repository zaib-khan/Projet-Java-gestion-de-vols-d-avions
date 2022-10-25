package dataAccess;

import business.ReservationDAO;
import exception.*;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ReservationDBAccess implements ReservationDAO {

    public void insertReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        try{
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("insert into Reservation(class,number_adult," +
                    "number_child,has_cancel_insurance,reservation_date,client,nb_adult_meal,nb_child_meal,flight)" +
                    "values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,reservation.getClass_flight());
            statement.setInt(2,reservation.getNumber_adult());
            statement.setInt(3,reservation.getNumber_child());
            statement.setBoolean(4,reservation.getHas_cancel_insurance());
            java.sql.Date sqlDate = new java.sql.Date(reservation.getReservation_date().getTimeInMillis());
            statement.setDate(5,sqlDate);
            statement.setInt(6,reservation.getClient().getClient_id());
            if(reservation.getNb_adult_meal() == null) statement.setNull(7, Types.INTEGER);
            else statement.setInt(7,reservation.getNb_adult_meal());
            if(reservation.getNb_child_meal() == null) statement.setNull(8,Types.INTEGER);
            else statement.setInt(8,reservation.getNb_child_meal());
            statement.setInt(9,reservation.getFlight().getFlight_id());
            statement.executeUpdate();
            ResultSet data = statement.getGeneratedKeys();
            if(data.next()) reservation.setReservation_id(data.getInt(1));
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public ArrayList<Reservation> getAll_reservations() throws ConnexionException, OperationDataException, NameException,
            FirstNameException, PriceException {
        ArrayList<Reservation> listReservation = new ArrayList<>();

        int has_cancel_insurance;
        Client client;
        Flight flight;
        Reservation reservation;

        try {

            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from reservation");

            ResultSet data = statement.executeQuery();

            while (data.next()){
                has_cancel_insurance = data.getBoolean("has_cancel_insurance")? 1 : 0 ;
                java.sql.Date sqlDate = data.getDate("reservation_date");
                GregorianCalendar reservation_date = new GregorianCalendar();
                reservation_date.setTimeInMillis(sqlDate.getTime());
                client = new ClientDBAccess().getClientBy_id(data.getInt("client"));
                flight = new FlightDBAccess().getFlightBy_id(data.getInt("flight"));
                reservation = new Reservation(data.getInt("reservation_id"),data.getString("class"), data.getInt("number_adult"), data.getInt("number_child"), has_cancel_insurance,
                        data.getInt("nb_adult_meal"), data.getInt("nb_child_meal"), reservation_date, client, flight);
                listReservation.add(reservation);
            }
            return listReservation;
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public void deleteReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();
            String sql = "delete from reservation where reservation_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,reservation.getReservation_id());

            statement.executeUpdate();

        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public void deleteReservationForSpecificFlight(Integer flight_id) throws ConnexionException, OperationDataException{
        if(flight_id != null) {
            try {
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("Delete from Reservation where flight = ?");
                statement.setInt(1, flight_id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new OperationDataException();
            }
        }
    }

    public Reservation getReservationBy_id(Integer reservation_id) throws ConnexionException, OperationDataException, NameException,
            FirstNameException, PriceException {

        int has_cancel_insurance;
        GregorianCalendar reservation_date = new GregorianCalendar();
        Client client;
        Flight flight;


        try {

            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from reservation where reservation_id = ?");
            statement.setInt(1,reservation_id);
            ResultSet data = statement.executeQuery();

            if (data.next()){

                has_cancel_insurance = data.getBoolean("has_cancel_insurance")? 1 : 0 ;
                java.sql.Date sqlDate = data.getDate("reservation_date");
                reservation_date.setTimeInMillis(sqlDate.getTime());
                client = new ClientDBAccess().getClientBy_id(data.getInt("client"));
                flight = new FlightDBAccess().getFlightBy_id(data.getInt("flight"));
                return new Reservation(data.getInt("reservation_id"),data.getString("class"), data.getInt("number_adult"), data.getInt("number_child"), has_cancel_insurance, data.getInt("nb_adult_meal"), data.getInt("nb_child_meal"), reservation_date, client, flight);
            }
            return null;
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public void updateReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();

            String sql = "update reservation" +
                    " set reservation.class = ?," +
                    " reservation.number_adult = ?," +
                    " reservation.number_child = ?," +
                    " reservation.has_cancel_insurance = ?," +
                    " reservation.nb_adult_meal = ?," +
                    " reservation.nb_child_meal = ?," +
                    " reservation.reservation_date = ?," +
                    " reservation.client = ?," +
                    " reservation.flight = ?" +
                    " where reservation.reservation_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,reservation.getClass_flight());
            statement.setInt(2,reservation.getNumber_adult());
            statement.setInt(3,reservation.getNumber_child());
            statement.setBoolean(4,reservation.getHas_cancel_insurance());
            statement.setInt(5,reservation.getNb_adult_meal());
            statement.setInt(6,reservation.getNb_child_meal());
            java.sql.Date sqlDate = new java.sql.Date(reservation.getReservation_date().getTimeInMillis());
            statement.setDate(7,sqlDate);
            statement.setInt(8,reservation.getClient().getClient_id());
            statement.setInt(9,reservation.getFlight().getFlight_id());
            statement.setInt(10,reservation.getReservation_id());

            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }
}
