package business;

import dataAccess.ReservationDBAccess;
import exception.*;
import model.Reservation;

import java.util.ArrayList;


public class BusinessReservation {

    private ReservationDAO dao;

    public BusinessReservation(){
        this.dao = new ReservationDBAccess();
    }



    public void insertReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        dao.insertReservation(reservation);
    }

    public ArrayList<Reservation> getAll_reservations() throws ConnexionException,OperationDataException, NameException,
            FirstNameException, PriceException{
        return dao.getAll_reservations();
    }

    public Reservation getReservationBy_id(Integer reservation_id) throws ConnexionException,OperationDataException, NameException,
            FirstNameException, PriceException{
        return dao.getReservationBy_id(reservation_id);
    }
    public void deleteReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        dao.deleteReservation(reservation);
    }
    public void updateReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        dao.updateReservation(reservation);
    }
}
