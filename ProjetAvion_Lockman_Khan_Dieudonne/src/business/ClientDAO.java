package business;

import exception.ConnexionException;
import exception.FirstNameException;
import exception.NameException;
import exception.OperationDataException;
import model.Client;

import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientDAO {

    ArrayList<Client> getAll_client() throws ConnexionException, OperationDataException, NameException, FirstNameException;

    Client getClientBy_id(Integer client_id) throws ConnexionException,OperationDataException,NameException, FirstNameException;
}
