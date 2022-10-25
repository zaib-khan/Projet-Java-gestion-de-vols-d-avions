package dataAccess;

import business.*;
import exception.*;
import model.*;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FlightDBAccess implements FlightDAO {

    public void insertFlight(Flight flight, Employee[] employees) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("insert into Flight(departure_time, " +
                    "estimated_duration, price_adult_eco,price_adult_business,price_adult_first_class," +
                    "price_child,cancel_insurance_price,plane,departure_airport,arrival_airport,meal,has_wifi," +
                    "commentary) values (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date = sdf.format(flight.getDeparture_time());
            statement.setString(1, date);

            statement.setInt(2, flight.getEstimated_duration());
            statement.setDouble(3, flight.getPrice_adult_eco());
            if (flight.getPrice_adult_business() == null) statement.setNull(4, Types.DOUBLE);
            else statement.setDouble(4, flight.getPrice_adult_business());
            if (flight.getPrice_adult_first_class() == null) statement.setNull(5, Types.DOUBLE);
            else statement.setDouble(5, flight.getPrice_adult_first_class());
            statement.setDouble(6, flight.getPrice_child());
            statement.setDouble(7, flight.getCancel_insurance_price());
            statement.setInt(8, flight.getPlane().getPlane_id());
            statement.setInt(9, flight.getDeparture_airport().getAirport_id());
            statement.setInt(10, flight.getArrival_airport().getAirport_id());
            if (flight.getMeal() == null) statement.setNull(11, Types.INTEGER);
            else statement.setInt(11, flight.getMeal().getMeal_id());
            statement.setBoolean(12, flight.isHas_wifi());
            if (flight.getCommentary() == null) statement.setNull(13, Types.VARCHAR);
            else statement.setString(13, flight.getCommentary());
            statement.executeUpdate();
            ResultSet data = statement.getGeneratedKeys();
            if (data.next()) flight.setFlight_id(data.getInt(1));
            for (Employee employee : employees) {
                insertFlightEmployee(flight, employee);
            }
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }

    public void insertFlightEmployee(Flight flight, Employee employee) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("insert into FLight_Employee(flight_id,employee_id) " +
                    "values(?,?)");
            statement.setInt(1, flight.getFlight_id());
            statement.setInt(2, employee.getEmployee_id());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }

    public void deleteFlightEmployee(Integer flight_id) throws ConnexionException, OperationDataException {
        if (flight_id != null) {
            try {
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("delete from flight_employee where flight_id = ?");
                statement.setInt(1, flight_id);
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new OperationDataException();
            }
        }
    }

    public void deleteFlight(Integer flight_id) throws ConnexionException, OperationDataException {

        if (flight_id != null) {
            try {
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("delete from flight where flight_id = ?");
                statement.setInt(1, flight_id);
                deleteFlightEmployee(flight_id);
                new ReservationDBAccess().deleteReservationForSpecificFlight(flight_id);
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new OperationDataException();
            }
        }
    }

    public ArrayList<Flight> getAll_flight() throws ConnexionException, OperationDataException, NameException,
            PriceException {
        ArrayList<Flight> listFlight = new ArrayList<>();
        int flight_id;
        java.util.Date departure_time;
        int estimated_duration;
        int real_duration;
        double price_adult_eco;
        double price_adult_business;
        double price_adult_first_class;
        double price_child;
        double cancel_insurance_price;
        Boolean has_wifi;
        String commentary;
        Plane plane;
        Airport departure_airport;
        Airport arrival_airport;
        Meal meal = null;
        Flight flight;


        try {

            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from flight");

            ResultSet data = statement.executeQuery();

            while (data.next()) {
                flight_id = data.getInt("flight_id");
                Timestamp timestamp = data.getTimestamp("departure_time");
                departure_time = new java.util.Date(timestamp.getTime());
                estimated_duration = data.getInt("estimated_duration");
                real_duration = data.getInt("real_duration");
                price_adult_eco = data.getDouble("price_adult_eco");
                price_adult_business = data.getDouble("price_adult_business");
                price_adult_first_class = data.getDouble("price_adult_first_class");
                price_child = data.getDouble("price_child");
                cancel_insurance_price = data.getDouble("cancel_insurance_price");
                plane = new PlaneDBAccess().getPlaneBy_id(data.getInt("plane"));
                departure_airport = new AirportDBAccess().getAirportBy_id(data.getInt("departure_airport"));
                arrival_airport = new AirportDBAccess().getAirportBy_id(data.getInt("arrival_airport"));
                meal = new MealDBAccess().getMealBy_id(data.getInt("meal"));
                has_wifi = data.getBoolean("has_wifi");
                commentary = data.getString("commentary");
                flight = new Flight(flight_id, departure_time, estimated_duration, real_duration, price_adult_eco, price_adult_business, price_adult_first_class, price_child, cancel_insurance_price, has_wifi, commentary, plane, arrival_airport, departure_airport, meal);
                listFlight.add(flight);
            }
            return listFlight;
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }

    public Flight getFlightBy_id(Integer flight_id) throws ConnexionException, OperationDataException, NameException, PriceException {
        java.util.Date departure_time;
        int estimated_duration;
        int real_duration;
        double price_adult_eco;
        double price_adult_business;
        double price_adult_first_class;
        double price_child;
        double cancel_insurance_price;
        Boolean has_wifi;
        String commentary;
        Plane plane;
        Airport departure_airport;
        Airport arrival_airport;
        Meal meal;

        if (flight_id == null) return null;
        else {
            try {
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("select * from flight where flight_id = ?");
                statement.setInt(1, flight_id);
                ResultSet data = statement.executeQuery();
                if (data.next()) {
                    flight_id = data.getInt("flight_id");
                    Timestamp timestamp = data.getTimestamp("departure_time");
                    departure_time = new java.util.Date(timestamp.getTime());
                    estimated_duration = data.getInt("estimated_duration");
                    real_duration = data.getInt("real_duration");
                    price_adult_eco = data.getDouble("price_adult_eco");
                    price_adult_business = data.getDouble("price_adult_business");
                    price_adult_first_class = data.getDouble("price_adult_first_class");
                    price_child = data.getDouble("price_child");
                    cancel_insurance_price = data.getDouble("cancel_insurance_price");
                    plane = new PlaneDBAccess().getPlaneBy_id(data.getInt("plane"));
                    departure_airport = new AirportDBAccess().getAirportBy_id(data.getInt("departure_airport"));
                    arrival_airport = new AirportDBAccess().getAirportBy_id(data.getInt("arrival_airport"));
                    meal = new MealDBAccess().getMealBy_id(data.getInt("meal"));
                    has_wifi = data.getBoolean("has_wifi");
                    commentary = data.getString("commentary");
                    return new Flight(flight_id, departure_time, estimated_duration, real_duration, price_adult_eco, price_adult_business, price_adult_first_class, price_child, cancel_insurance_price, has_wifi, commentary, plane, arrival_airport, departure_airport, meal);
                } else return null;
            } catch (SQLException sqlException) {
                throw new OperationDataException();
            }
        }
    }

    public Employee[] getFlightStaff(Integer flight_id) throws ConnexionException, OperationDataException {
        try {
            Employee[] employees = new Employee[5];
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select employee_id from flight_employee where flight_id = ?");
            statement.setInt(1, flight_id);
            ResultSet data = statement.executeQuery();
            int i = 0;
            while (data.next()) {
                employees[i] = new EmployeeDBAccess().getEmployee(data.getInt("employee_id"));
                i++;
            }
            return employees;
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }

    public void modifyFlight(Flight flight, Employee[] employees) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("update flight " +
                    "set departure_time = ?," +
                    "estimated_duration = ?," +
                    "price_adult_eco = ?," +
                    "price_adult_business = ?," +
                    "price_adult_first_class = ?," +
                    "price_child = ?," +
                    "cancel_insurance_price = ?," +
                    "plane = ?," +
                    "departure_airport = ?," +
                    "arrival_airport = ?," +
                    "meal = ?," +
                    "has_wifi = ?," +
                    "commentary = ?," +
                    "real_duration = ? " +
                    "where flight_id = ?");

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date = sdf.format(flight.getDeparture_time());
            statement.setString(1, date);
            statement.setInt(2, flight.getEstimated_duration());
            statement.setDouble(3, flight.getPrice_adult_eco());
            statement.setDouble(4, flight.getPrice_adult_business());
            statement.setDouble(5, flight.getPrice_adult_first_class());
            statement.setDouble(6, flight.getPrice_child());
            statement.setDouble(7, flight.getCancel_insurance_price());
            statement.setInt(8, flight.getPlane().getPlane_id());
            statement.setInt(9, flight.getDeparture_airport().getAirport_id());
            statement.setInt(10, flight.getArrival_airport().getAirport_id());
            if (flight.getMeal() == null) statement.setNull(11, Types.INTEGER);
            else statement.setInt(11, flight.getMeal().getMeal_id());
            statement.setBoolean(12, flight.isHas_wifi());
            statement.setString(13, flight.getCommentary());
            if (flight.getReal_duration() == null) statement.setNull(14, Types.INTEGER);
            else statement.setInt(14, flight.getReal_duration());
            statement.setInt(15, flight.getFlight_id());
            statement.executeUpdate();
            deleteFlightEmployee(flight.getFlight_id());
            for (Employee employee : employees) {
                insertFlightEmployee(flight, employee);
            }
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }

    public int getFreePlacesByFlight_id(Integer flight_id) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();
            String sql = "select p.number_seats, sum(r.number_adult)+ sum(r.number_child)as nb_passenger\n" +
                    "from flight f\n" +
                    "left outer join reservation r on (r.flight = f.flight_id)\n" +
                    "join plane p on (f.plane = p.plane_id)\n" +
                    " where flight_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, flight_id);
            ResultSet data = statement.executeQuery();
            if (data.next()) {
                int nb_places = data.getInt("number_seats");
                int nb_passenger = data.getInt("nb_passenger");

                return nb_places - nb_passenger;
            }
            return 0;
        } catch (ConnexionException e) {
            throw new ConnexionException();
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }

    public ArrayList<String> getFlightLeavingSoon() throws ConnexionException, OperationDataException {
        Date now = new Date();
        Date later = new Date(now.getTime() + Tools.TEN_MINS_IN_MILLISECONDS);
        SimpleDateFormat sdfFullDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
        ArrayList<String> flights = new ArrayList<>();
        String flightInfo, arr_city_name, dep_city_name;
        Integer flight_id;
        Timestamp ts;
        Date departure_time;

        try {
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select f.departure_time, f.flight_id,arr.city_name as arrival," +
                    "dep.city_name as departure from flight f " +
                    "join Airport arr on (arr.airport_id = f.arrival_airport) " +
                    "join Airport dep on (dep.airport_id = f.departure_airport) " +
                    "where f.departure_time between ? and ?");
            statement.setString(1, sdfFullDate.format(now));
            statement.setString(2, sdfFullDate.format(later));
            ResultSet data = statement.executeQuery();
            while (data.next()) {
                ts = data.getTimestamp("departure_time");
                departure_time = new Date(ts.getTime());
                arr_city_name = data.getString("arrival");
                dep_city_name = data.getString("departure");
                flight_id = data.getInt("flight_id");
                flightInfo = "Flight #" + flight_id + " from " + dep_city_name + " to " + arr_city_name + " leave at " + sdfHour.format(departure_time);
                flights.add(flightInfo);
            }
            return flights;
        } catch (SQLException sqlException) {
            throw new OperationDataException();
        }
    }
}
