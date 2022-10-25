package dataAccess;

import business.ResearchDAO;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;
import model.ResearchClients;
import model.ResearchEmployees;
import model.ResearchFlights;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class ResearchDBAccess implements ResearchDAO {


    @Override
    public ArrayList<ResearchEmployees> researchEmployees(Country country, GregorianCalendar startDate, GregorianCalendar endDate)
            throws ConnexionException, OperationDataException {

        ArrayList<ResearchEmployees> listEmployee = new ArrayList<>();

        try {
            Connection connection = SingletonConnexion.getInstance();
            String sql = "select emp.first_name, emp.last_name,emp.role,air.city_name, fli.departure_time\n" +
                    "from employee emp, flight_employee flm, flight fli, airport air, country cou\n" +
                    "where flm.employee_id = emp.employee_id\n" +
                    "and fli.flight_id = flm.flight_id\n" +
                    "and fli.departure_airport = air.airport_id\n" +
                    "and air.country = cou.country_id\n" +
                    "and cou.country_name = ?\n" +
                    "and fli.departure_time < ?\n" +
                    "and fli.departure_time >?";

            PreparedStatement statement = connection.prepareStatement(sql);

            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTimeInMillis());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTimeInMillis());

            statement.setString(1,country.getCountry_name());
            statement.setDate(2,sqlEndDate);
            statement.setDate(3,sqlStartDate);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                java.sql.Date sqlDate = resultSet.getDate("departure_time");
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(sqlDate);

                listEmployee.add(new ResearchEmployees(resultSet.getString("first_name"),resultSet.getString("last_name"),resultSet.getString("role"),resultSet.getString("city_name"),calendar));
            }


        }
        catch (SQLException e) {
            throw new OperationDataException();
        }


        return listEmployee;
    }

    public ArrayList<ResearchFlights> researchFlights(Country country, boolean isDestination) throws ConnexionException, OperationDataException {
        ArrayList<ResearchFlights> flights = new ArrayList<ResearchFlights>();
        ResearchFlights flight;

        try{
            Connection connection = SingletonConnexion.getInstance();
            String string = "select dep.city_name as \"Departure city\", arr.city_name as \"Arrival city\",p.number_seats," +
                    "sum(r.number_adult)+ sum(r.number_child)as nb_passenger,f.departure_time,f.flight_id\n" +
                    "from flight f\n" +
                    "left outer join reservation r on (r.flight = f.flight_id)";
            if(isDestination){
                string += "join airport arr on (f.arrival_airport = arr.airport_id)\n" +
                        "join country c on (arr.country = c.country_id)\n" +
                        "join airport dep on (f.departure_airport = dep.airport_id)";
            }
            else {
                string += "join airport dep on (f.departure_airport = dep.airport_id)\n" +
                        "join country c on (dep.country = c.country_id)\n" +
                        "join airport arr on (f.arrival_airport = arr.airport_id)";
            }
            string += "join plane p on (f.plane = p.plane_id)\n" +
                    "where c.country_id = ?\n" +
                    "and p.number_seats > (select sum(r.number_adult) + sum(r.number_child) from reservation r where r.flight = f.flight_id)\n" +
                    "or c.country_id = ?\n" +
                    "and (select sum(r.number_adult) + sum(r.number_child)) is null\n"+
                    "group by (f.flight_id);";
            PreparedStatement statement = connection.prepareStatement(string);
            statement.setInt(1,country.getCountry_id());
            statement.setInt(2,country.getCountry_id());
            ResultSet data = statement.executeQuery();
            while (data.next()){
                Timestamp ts = data.getTimestamp("departure_time");
                Date departure_time = new Date(ts.getTime());
                data.getInt("nb_passenger");
                flight = new ResearchFlights(data.getString("Departure city"), data.getString("Arrival city"),data.getInt("number_seats"),data.getInt("nb_passenger"),departure_time,data.getInt("flight_id"));
                flights.add(flight);
            }
            return flights;
        }
        catch (SQLException e) {
            throw new OperationDataException();
        }
    }

    public ArrayList<ResearchClients> researchClients(Integer meal_id,Date start_date, Date end_date) throws ConnexionException,OperationDataException{
        ArrayList<ResearchClients> researchClients = new ArrayList<>();
        Timestamp ts;
        Date departure_time;
        String first_name, last_name;
        SimpleDateFormat sdf;

        try{
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select c.first_name, c.last_name, f.departure_time " +
                    "from Meal m " +
                    "join Reservation r on (r.meal = m.meal_id) " +
                    "join Client c on (r.client = c.client_id) " +
                    "join Flight f on (r.flight = f.flight_id) " +
                    "where m.meal_id = ? " +
                    "and f.departure_time between ? and ? ");
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setInt(1,meal_id);
            statement.setString(2,sdf.format(start_date));
            statement.setString(3,sdf.format(end_date));
            ResultSet data = statement.executeQuery();
            while (data.next()){
                ts = data.getTimestamp("departure_time");
                departure_time = new java.util.Date(ts.getTime());
                first_name = data.getString("first_name");
                last_name = data.getString("last_name");
                researchClients.add(new ResearchClients(first_name,last_name,departure_time));
            }
            return researchClients;
        }
        catch (SQLException exception){
            throw new OperationDataException();
        }
    }
}
