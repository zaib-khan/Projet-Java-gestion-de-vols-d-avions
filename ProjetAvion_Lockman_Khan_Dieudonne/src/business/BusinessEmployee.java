package business;

import dataAccess.EmployeeDBAccess;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Employee;

import java.util.ArrayList;

public class BusinessEmployee {

    private EmployeeDAO employeeDAO;

    public BusinessEmployee(){
        this.employeeDAO = new EmployeeDBAccess();
    }

    public ArrayList<Employee> getEmployeeByRole(String role)throws ConnexionException, OperationDataException {
        return employeeDAO.getAllEmployeesByRoles(role);
    }

    public Employee getEmployee(Integer employee_id)throws ConnexionException, OperationDataException {
        return employeeDAO.getEmployee(employee_id);
    }
}
