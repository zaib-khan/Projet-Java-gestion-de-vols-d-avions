package dataAccess;

import business.EmployeeDAO;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeDBAccess implements EmployeeDAO {

    public Employee getEmployee(Integer employee_id) throws ConnexionException, OperationDataException {
        if(employee_id == null) return null;
        try{
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from Employee where employee_id = ?");
            statement.setInt(1,employee_id);
            ResultSet data = statement.executeQuery();
            if(data.next()){
                java.sql.Date sqlDate = data.getDate("birthdate");
                Date birthdate = sqlDate;
                String role = data.getString("role");
                String last_name = data.getString("last_name");
                String first_name = data.getString("first_name");
                return new Employee(employee_id,role,first_name,last_name,birthdate);
            }
            return null;
        }
        catch (SQLException sqlException){
            throw new OperationDataException();
        }
    }

    public ArrayList<Employee> getAllEmployeesByRoles(String role)throws ConnexionException,OperationDataException{
        ArrayList<Employee> employees = new ArrayList<Employee>();
        String first_name, last_name;
        Date birthdate;
        Integer employee_id;
        java.sql.Date sqlDate;
        Employee employee;

        try{
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from Employee where role = ?");
            statement.setString(1,role);
            ResultSet data = statement.executeQuery();
            while (data.next()){
                first_name = data.getString("first_name");
                last_name = data.getString("last_name");
                sqlDate = data.getDate("birthdate");
                birthdate = sqlDate;
                employee_id = data.getInt("employee_id");
                employee = new Employee(employee_id,role,first_name,last_name,birthdate);
                employees.add(employee);
            }
            return employees;
        }
        catch (SQLException sqlException){
            throw new OperationDataException();
        }
    }
}
