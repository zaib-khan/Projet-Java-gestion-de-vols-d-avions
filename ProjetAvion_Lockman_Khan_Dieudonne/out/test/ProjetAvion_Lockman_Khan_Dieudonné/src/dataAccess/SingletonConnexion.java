package dataAccess;

import java.sql.*;

import exception.ClosingException;
import exception.ConnexionException;

public class SingletonConnexion {
    private static Connection connectionUniqueInstance;

    public static Connection getInstance() throws ConnexionException{

        if (connectionUniqueInstance == null){
            try {

                //LOCKMAN
                //connectionUniqueInstance = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdjava2020gestionavion","root","worna-123");

                //DIEUDONNE
                //connectionUniqueInstance = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdjava2020gestionavion?useSSL=false","root","cerealplacebo5");

                //KHAN
                connectionUniqueInstance = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdjava2020gestionavion","root","zaib123");
            }
            catch (SQLException e){
                throw new ConnexionException();
            }
        }
        return connectionUniqueInstance;
    }

    public static void closeConnection() throws ClosingException{
        try{
            if(connectionUniqueInstance != null) connectionUniqueInstance.close();
        }
        catch (SQLException e){
            throw new ClosingException();
        }
    }
}