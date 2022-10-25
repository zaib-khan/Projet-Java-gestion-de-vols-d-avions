package dataAccess;

import business.MealDAO;
import exception.*;
import model.Country;
import model.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MealDBAccess implements MealDAO {


    public void addMeal(Meal meal) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();

            String sql = "insert into meal(meal_name,is_hot,price_adult,price_child,spicy_level,availibility_date,country) value(?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,meal.getMeal_name());
            statement.setBoolean(2,meal.is_hot());
            statement.setDouble(3,meal.getPrice_adult());
            if (meal.getPrice_child() != null)statement.setDouble(4,meal.getPrice_child());
            else statement.setNull(4,Types.INTEGER);
            statement.setInt(5,meal.getSpicy_level());

            java.sql.Date sqlDate = new java.sql.Date(meal.getAvailibility_date().getTimeInMillis());

            statement.setDate(6,sqlDate);
            statement.setInt(7,meal.getCountry().getCountry_id());

            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public ArrayList<Meal> getAllMeal() throws ConnexionException, OperationDataException, NameException, PriceException {
        ArrayList<Meal> meal_list = new ArrayList<>();
        Double priceAdult = null;
        Double priceChild = null;
        String name = null;

        try {
            Connection connection = SingletonConnexion.getInstance();
            String sql = "select * from meal";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();

            while (data.next()){

                java.sql.Date sqlDate = data.getDate("availibility_date");
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(sqlDate);

                Country country = new CountryDBAccess().getCountryBy_id(data.getInt("country"));
                name = data.getString("meal_name");
                priceAdult = data.getDouble("price_adult");
                priceChild = data.getDouble("price_child");

                meal_list.add(new Meal(data.getInt("meal_id"),name,data.getBoolean("is_hot"),priceAdult,priceChild,data.getInt("spicy_level"),calendar,country));

            }
        }
        catch (SQLException e){
            throw new OperationDataException();
        }

        return meal_list;
    }

    public void deleteMeal(Meal meal) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();
            String sql = "delete from meal where meal_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,meal.getMeal_id());

            statement.executeUpdate();

        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public void updateMeal(Meal meal) throws ConnexionException, OperationDataException {
        try {
            Connection connection = SingletonConnexion.getInstance();

            String sql = "update meal" +
                    "   set meal.meal_name = ?," +
                    "   meal.is_hot = ?," +
                    "   meal.price_adult = ?," +
                    "   meal.price_child = ?," +
                    "   meal.spicy_level = ?," +
                    "   meal.country = ?," +
                    "   meal.availibility_date = ?" +
                    "where meal.meal_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,meal.getMeal_name());
            statement.setBoolean(2,meal.is_hot());
            statement.setDouble(3,meal.getPrice_adult());
            statement.setDouble(4,meal.getPrice_child());
            statement.setInt(5,meal.getSpicy_level());
            statement.setInt(6,meal.getCountry().getCountry_id());

            java.sql.Date sqlDate = new java.sql.Date(meal.getAvailibility_date().getTimeInMillis());

            statement.setDate(7,sqlDate);
            statement.setInt(8,meal.getMeal_id());

            statement.executeUpdate();



        }
        catch (SQLException e){
            throw new OperationDataException();
        }
    }

    public ArrayList<Meal> getMealsByCountry(Integer country_id,Date flight_date)throws ConnexionException,
            OperationDataException,NameException, PriceException {
        ArrayList<Meal> meals = new ArrayList<Meal>();
        Meal meal;
        Integer meal_id, spicy_level;
        String meal_name = null;
        Boolean is_hot;
        Double price_adult = null;
        Double price_child = null;
        GregorianCalendar availibility_date;
        Country country;
        java.sql.Date sqlDate;

        if(country_id == null) return null;
        try {
            sqlDate = new java.sql.Date(flight_date.getTime());
            Connection connection = SingletonConnexion.getInstance();
            PreparedStatement statement = connection.prepareStatement("select * from Meal where country = ? " +
                    "and availibility_date <= ?");
            statement.setInt(1,country_id);
            statement.setDate(2,sqlDate);
            ResultSet data = statement.executeQuery();
            while (data.next()){

                meal_id = data.getInt("meal_id");
                spicy_level = data.getInt("spicy_level");
                meal_name = data.getString("meal_name");
                is_hot = data.getBoolean("is_hot");
                price_adult = data.getDouble("price_adult");
                price_child = data.getDouble("price_child");
                sqlDate = data.getDate("availibility_date");
                availibility_date = new GregorianCalendar();
                availibility_date.setTime(sqlDate);
                country = new CountryDBAccess().getCountryBy_id(country_id);
                meal = new Meal(meal_id, meal_name, is_hot, price_adult, price_child, spicy_level, availibility_date, country);
                meals.add(meal);

            }
            return meals;
        }
        catch (SQLException e){
            throw new OperationDataException();
        }
        catch (NameException exception){
            throw new NameException(meal_name);
        }
        catch (PriceException exception){
            throw new PriceException(price_adult);
        }

    }

    public Meal getMealBy_id(Integer meal_id)throws ConnexionException, OperationDataException,NameException,
            PriceException{
        int spicy_level;
        String meal_name = null;
        Boolean is_hot;
        Double price_adult = null;
        Double price_child = null;
        java.sql.Date sqlDate;
        GregorianCalendar availibility_date;
        Country country;

        if(meal_id == null) return null;
        else{
            try{
                Connection connection = SingletonConnexion.getInstance();
                PreparedStatement statement = connection.prepareStatement("select * from meal where meal_id = ?");
                statement.setInt(1,meal_id);
                ResultSet data = statement.executeQuery();
                if(data.next()){
                    spicy_level = data.getInt("spicy_level");
                    meal_name = data.getString("meal_name");
                    is_hot = data.getBoolean("is_hot");
                    price_adult = data.getDouble("price_adult");
                    price_child = data.getDouble("price_child");
                    sqlDate = data.getDate("availibility_date");
                    availibility_date = new GregorianCalendar();
                    availibility_date.setTime(sqlDate);
                    country = new CountryDBAccess().getCountryBy_id(data.getInt("country"));
                    return new Meal(meal_id,meal_name,is_hot,price_adult,price_child,spicy_level,availibility_date,country);

                }
                else return null;
            }
            catch (SQLException e){
                throw new OperationDataException();
            }
            catch (NameException exception){
                throw new NameException(meal_name);
            }
            catch (PriceException exception){
                throw new PriceException(price_adult);
            }
        }
    }
}
