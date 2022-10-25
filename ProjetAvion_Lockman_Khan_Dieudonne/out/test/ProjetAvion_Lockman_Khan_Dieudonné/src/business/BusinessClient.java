package business;

import dataAccess.ClientDBAccess;
import exception.ConnexionException;
import exception.FirstNameException;
import exception.NameException;
import exception.OperationDataException;
import model.Client;

import java.util.ArrayList;

public class BusinessClient {

    private ClientDAO dao;

    public BusinessClient(){
        this.dao = new ClientDBAccess();
    }

    public ArrayList<Client> getAll_client() throws ConnexionException, OperationDataException, NameException, FirstNameException {
        return dao.getAll_client();
    }
}
