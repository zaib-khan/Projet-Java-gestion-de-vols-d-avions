package business;

import exception.ConnexionException;
import exception.OperationDataException;
import model.Employee;

import java.util.ArrayList;

public interface EmployeeDAO {

    Employee getEmployee(Integer employee_id) throws ConnexionException, OperationDataException;

    ArrayList<Employee> getAllEmployeesByRoles(String role)throws ConnexionException,OperationDataException;
}
