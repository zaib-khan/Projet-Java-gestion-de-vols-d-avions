package business;

import exception.*;
import model.Meal;

import java.util.ArrayList;
import java.util.Date;

public interface MealDAO {

    void addMeal(Meal meal) throws ConnexionException, OperationDataException;

    ArrayList<Meal> getAllMeal() throws ConnexionException, OperationDataException, NameException, PriceException;
    ArrayList<Meal> getMealsByCountry(Integer country_id, Date flight_date) throws ConnexionException,
            OperationDataException,NameException, PriceException;

    void deleteMeal(Meal meal) throws ConnexionException, OperationDataException;
    void updateMeal(Meal meal) throws ConnexionException, OperationDataException;
    Meal getMealBy_id(Integer meal_id) throws ConnexionException, OperationDataException,NameException, PriceException;
}
