package business;

import dataAccess.MealDBAccess;
import exception.*;
import model.Meal;

import java.util.ArrayList;
import java.util.Date;

public class BusinessMeal {

    private MealDAO dao;

    public BusinessMeal(){
        this.dao = new MealDBAccess();
    }

    public void insertMeal(Meal meal) throws ConnexionException, OperationDataException {
        dao.addMeal(meal);
    }
    public ArrayList<Meal> getAllMeal() throws ConnexionException, OperationDataException, NameException, PriceException {
        return dao.getAllMeal();
    }
    public void deleteMeal(Meal meal) throws ConnexionException, OperationDataException {
        dao.deleteMeal(meal);
    }
    public void updateMeal(Meal meal) throws ConnexionException, OperationDataException {
        dao.updateMeal(meal);
    }

    public ArrayList<Meal> getMealsByCountry(Integer country_id) throws ConnexionException,OperationDataException,
            NameException, PriceException{
        return dao.getMealsByCountry(country_id,new Date());
    }

}
