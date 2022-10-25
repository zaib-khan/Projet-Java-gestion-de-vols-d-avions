package business;

import dataAccess.FlightDBAccess;
import exception.*;
import model.Employee;
import model.Flight;
import model.Reservation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BusinessFlight {

    private FlightDAO dao;

    public BusinessFlight() {
        this.dao = new FlightDBAccess();
    }

    public ArrayList<Flight> getAll_flight() throws ConnexionException, OperationDataException, NameException,
            PriceException {
        return dao.getAll_flight();
    }

    public int getFreePlacesByFlight_id(Integer flight_id) throws ConnexionException, OperationDataException {
        return dao.getFreePlacesByFlight_id(flight_id);
    }

    public void insertFlight(Flight flight, Employee[] employees) throws ConnexionException, OperationDataException {
        dao.insertFlight(flight, employees);
    }

    public void deleteFLight(Integer flight) throws ConnexionException, OperationDataException {
        dao.deleteFlight(flight);
    }

    public Employee[] getFlightStaff(Integer flight_id) throws ConnexionException, OperationDataException {
        return dao.getFlightStaff(flight_id);
    }

    public void modifyFlight(Flight flight, Employee[] employees) throws ConnexionException, OperationDataException {
        dao.modifyFlight(flight, employees);
    }

    public ArrayList<String> getFlightLeavingSoon() throws ConnexionException, OperationDataException {
        return dao.getFlightLeavingSoon();
    }

    static public double getEightyPercentFull(ArrayList<Flight> listFlight, ArrayList<Reservation> listReservation) {
        int nbFlightsEightyPercentFull = 0;

        for (Flight flight : listFlight) {
            int nbPersonOnFlight = 0;
            for (Reservation reservation : listReservation) {
                if (reservation.getFlight().getFlight_id() == flight.getFlight_id()) {
                    nbPersonOnFlight += numberAdultAndChildOnPlane(reservation.getNumber_adult(), reservation.getNumber_child());
                }
            }
            if (((double)nbPersonOnFlight / (double)flight.getPlane().getNumber_seats()) > 0.8) {
                nbFlightsEightyPercentFull++;
            }
        }

        return 100.0 * nbFlightsEightyPercentFull / listFlight.size();
    }

    static public int numberAdultAndChildOnPlane(int nbAdult, int nbChild) {
        return nbAdult + nbChild;
    }

    static public int totalDelay(ArrayList<Flight> list) {
        int res = 0;

        for (Flight flight : list) {
            res += delay(flight.getEstimated_duration(), flight.getReal_duration());
        }
        return res;
    }

    static public Double getDelyInHour(int delay){
        return (double)delay/60.0;
    }
    static public int delay(Integer estimated_duration, Integer real_duration) {
        if (real_duration != null) {
            if (real_duration > estimated_duration)
                return real_duration - estimated_duration;
        }
        return 0;
    }

    static public double getAverageFlightPerMonthForACountry(Integer country_id, ArrayList<Flight> flights) {
        Calendar cal;
        Date now, lastYear;
        double totalFligh = 0;

        cal = Calendar.getInstance();
        cal.setTime(new Date());
        now = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        lastYear = cal.getTime();

        for (Flight flight : flights) {
            if (flight.getDeparture_airport().getCountry().getCountry_id() == country_id) {
                if (isDateBetweenTwoOther(flight.getDeparture_time(), lastYear, now)) {
                    totalFligh++;
                }
            }
        }
        return totalFligh / 12.0;
    }

    static public boolean isDateBetweenTwoOther(Date dateTested, Date limitLeft, Date limitRight) {
        return (dateTested.after(limitLeft) && dateTested.before(limitRight));
    }

}


