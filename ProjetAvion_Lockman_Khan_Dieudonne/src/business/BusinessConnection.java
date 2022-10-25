package business;

import dataAccess.SingletonConnexion;
import exception.ClosingException;

public class BusinessConnection {

    public BusinessConnection(){}

    public void closeConnection() throws ClosingException{
        SingletonConnexion.closeConnection();
    }
}
