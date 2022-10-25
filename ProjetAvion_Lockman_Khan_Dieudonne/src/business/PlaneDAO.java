package business;

import exception.ConnexionException;
import exception.OperationDataException;
import model.Plane;

import java.util.ArrayList;

public interface PlaneDAO {

    ArrayList<Plane> getAllPlanes()throws ConnexionException, OperationDataException;
    Plane getPlaneBy_id(Integer plane_id)throws ConnexionException,OperationDataException;
}
