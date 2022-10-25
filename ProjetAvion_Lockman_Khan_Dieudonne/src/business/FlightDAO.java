package business;

import exception.*;
import model.Employee;
import model.Flight;

import java.util.ArrayList;

public interface FlightDAO {

    void insertFlight(Flight flight, Employee[] employees)throws ConnexionException, OperationDataException;

    void insertFlightEmployee(Flight flight, Employee employee)throws ConnexionException, OperationDataException;

    void deleteFlight(Integer flight_id)throws ConnexionException, OperationDataException;

    void deleteFlightEmployee(Integer flight_id)throws ConnexionException, OperationDataException;

    Flight getFlightBy_id(Integer flight_id) throws ConnexionException, OperationDataException, NameException, PriceException;

    Employee[] getFlightStaff(Integer flight_id)throws ConnexionException, OperationDataException;

    ArrayList<Flight> getAll_flight() throws ConnexionException, OperationDataException, NameException, PriceException;

    int getFreePlacesByFlight_id(Integer flight_id) throws ConnexionException, OperationDataException;

    void modifyFlight(Flight flight, Employee[] employees)throws ConnexionException, OperationDataException;

    ArrayList<String> getFlightLeavingSoon() throws ConnexionException, OperationDataException;

}
