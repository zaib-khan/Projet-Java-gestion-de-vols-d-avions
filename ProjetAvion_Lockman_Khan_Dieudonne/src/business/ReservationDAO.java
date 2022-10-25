package business;

import exception.*;
import model.Reservation;

import java.util.ArrayList;

public interface ReservationDAO {

    void insertReservation(Reservation reservation) throws ConnexionException, OperationDataException;
    ArrayList<Reservation> getAll_reservations() throws ConnexionException, OperationDataException, NameException,
            FirstNameException, PriceException;
    void deleteReservationForSpecificFlight(Integer flight_id) throws ConnexionException, OperationDataException;
    void deleteReservation(Reservation reservation) throws ConnexionException, OperationDataException;
    Reservation getReservationBy_id(Integer reservation_id) throws ConnexionException, OperationDataException, NameException,
            FirstNameException, PriceException;
    void updateReservation(Reservation reservation) throws ConnexionException, OperationDataException;
}
