package business;

import dataAccess.PlaneDBAccess;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Plane;

import java.util.ArrayList;

public class BusinessPlane {
    private PlaneDAO planeDAO;

    public BusinessPlane(){
        this.planeDAO = new PlaneDBAccess();
    }

    public ArrayList<Plane> getAllPlanes()throws ConnexionException, OperationDataException {
        return planeDAO.getAllPlanes();
    }
}
