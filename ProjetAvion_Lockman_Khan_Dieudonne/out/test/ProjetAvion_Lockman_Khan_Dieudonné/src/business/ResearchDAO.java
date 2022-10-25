package business;

import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;
import model.ResearchClients;
import model.ResearchEmployees;
import model.ResearchFlights;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public interface ResearchDAO {


    ArrayList<ResearchEmployees> researchEmployees(Country country, GregorianCalendar startDate, GregorianCalendar endDate)
            throws ConnexionException, OperationDataException;

    ArrayList<ResearchFlights> researchFlights(Country country, boolean isDestination) throws ConnexionException, OperationDataException;

    ArrayList<ResearchClients> researchClients(Integer meal_id, Date start_date, Date end_date)throws ConnexionException,OperationDataException;
}
