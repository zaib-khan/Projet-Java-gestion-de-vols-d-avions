package controller;

import business.*;
import exception.*;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Control {

    public Control() {
    }


    public ArrayList<Country> getAll_country() throws ConnexionException, OperationDataException {
        return new BusinessCountry().getAll_country();
    }

    public void insertMeal(Meal meal) throws ConnexionException, OperationDataException {
        new BusinessMeal().insertMeal(meal);
    }

    public ArrayList<Meal> getAllMeal() throws ConnexionException, OperationDataException, NameException, PriceException {
        return new BusinessMeal().getAllMeal();
    }

    public void deleteMeal(Meal meal) throws ConnexionException, OperationDataException {
        new BusinessMeal().deleteMeal(meal);
    }

    public void updateMeal(Meal meal) throws ConnexionException, OperationDataException {
        new BusinessMeal().updateMeal(meal);
    }

    public ArrayList<Airport> getAirportByCountry(Integer country_id) throws ConnexionException, OperationDataException {
        return new BusinessAirport().getAirportByCountry(country_id);
    }

    public ArrayList<Employee> getEmployeeByRole(String role) throws ConnexionException, OperationDataException {
        return new BusinessEmployee().getEmployeeByRole(role);
    }

    public ArrayList<Meal> getMealsByCountries(Integer country_id) throws ConnexionException, OperationDataException,
            NameException, PriceException {
        return new BusinessMeal().getMealsByCountry(country_id);
    }

    public ArrayList<Client> getAll_client() throws ConnexionException, OperationDataException, NameException, FirstNameException {
        return new BusinessClient().getAll_client();
    }

    public ArrayList<Flight> getAll_flight() throws ConnexionException, OperationDataException, NameException, PriceException {
        return new BusinessFlight().getAll_flight();
    }

    public void insertReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        new BusinessReservation().insertReservation(reservation);
    }

    public ArrayList<Reservation> getAll_reservations() throws ConnexionException, OperationDataException, NameException,
            FirstNameException, PriceException {
        return new BusinessReservation().getAll_reservations();
    }

    public Reservation getReservationBy_id(Integer reservation_id) throws ConnexionException, OperationDataException, NameException,
            FirstNameException, PriceException {
        return new BusinessReservation().getReservationBy_id(reservation_id);
    }

    public void deleteReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        new BusinessReservation().deleteReservation(reservation);
    }

    public void updateReservation(Reservation reservation) throws ConnexionException, OperationDataException {
        new BusinessReservation().updateReservation(reservation);
    }

    public ArrayList<ResearchEmployees> researchEmployees(Country country, GregorianCalendar start, GregorianCalendar end)
            throws ConnexionException, OperationDataException {
        return new BusinessResearch().researchEmployees(country, start, end);
    }

    public ArrayList<ResearchFlights> researchFlights(Country country, Boolean isDestination) throws ConnexionException, OperationDataException {
        return new BusinessResearch().researchFlights(country, isDestination);
    }

    public ArrayList<ResearchClients> researchClients(Integer meal_id, Date start_date, Date end_date) throws ConnexionException, OperationDataException {
        return new BusinessResearch().researchClients(meal_id, start_date, end_date);
    }

    public ArrayList<Plane> getAllPlanes() throws ConnexionException, OperationDataException {
        return new BusinessPlane().getAllPlanes();
    }

    public void insertFlight(Flight flight, Employee[] employees) throws ConnexionException, OperationDataException {
        new BusinessFlight().insertFlight(flight, employees);
    }

    public void deleteFlight(Integer flight) throws ConnexionException, OperationDataException {
        new BusinessFlight().deleteFLight(flight);
    }

    public Employee getEmployee(Integer employee_id) throws ConnexionException, OperationDataException {
        return new BusinessEmployee().getEmployee(employee_id);
    }

    public Employee[] getEmployeeStaff(Integer flight_id) throws ConnexionException, OperationDataException {
        return new BusinessFlight().getFlightStaff(flight_id);
    }

    public void modifyFlight(Flight flight, Employee[] employees) throws ConnexionException, OperationDataException {
        new BusinessFlight().modifyFlight(flight, employees);
    }

    public static int getFreePlacesByFlight_id(Integer flight_id) throws ConnexionException, OperationDataException {
        return new BusinessFlight().getFreePlacesByFlight_id(flight_id);
    }

    public ArrayList<String> getFlightLeavingSoon() throws ConnexionException, OperationDataException {
        return new BusinessFlight().getFlightLeavingSoon();
    }

    public void closeConnection() throws ClosingException {
        new BusinessConnection().closeConnection();
    }

    public int getTotalDelay() throws OperationDataException, ConnexionException, PriceException, NameException {
        return BusinessFlight.totalDelay(new BusinessFlight().getAll_flight());
    }
    public Double getTotalDelayHour(int delay){
        return BusinessFlight.getDelyInHour(delay);
    }

    public double getAverageFlightPerMonthForACountry(Integer country_id) throws ConnexionException, OperationDataException, PriceException, NameException {
        return BusinessFlight.getAverageFlightPerMonthForACountry(country_id, new BusinessFlight().getAll_flight());
    }

    public double getEightyPercentFull() throws ConnexionException, OperationDataException, PriceException, NameException, FirstNameException {
        return BusinessFlight.getEightyPercentFull(new BusinessFlight().getAll_flight(), new BusinessReservation().getAll_reservations());
    }

}
