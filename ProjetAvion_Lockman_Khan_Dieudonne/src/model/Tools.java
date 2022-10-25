package model;

import java.util.Calendar;
import java.util.Date;

public class Tools {
    public static final long TEN_MINS_IN_MILLISECONDS = 600000;

    public static boolean isNameValid(String name) {
        if (name == null) return false;
        return name.matches("[a-zA-Z \\-éèïöüëäùà]*");
    }

    public static boolean isValidPrice(String price) {
        if (price == null) return false;
        return price.matches("[0-9]{1,13}(\\.[0-9]*)?");
    }

    public static boolean isDate(String date) {
        if (date == null) return false;
        return date.matches("[0-3][0-9]\\/[01][0-9]\\/[12][0-9][0-9][0-9]");
    }

    public static boolean isAlphaNumeric(String string) {
        if (string == null) return false;
        return string.matches("^[\\-\\w éàèëâÂÉÈÀ]+");
        //return string.matches("[^A-Za-z0-9]+");
    }

    public static boolean isHour(String hour) {
        if (hour == null) return false;
        return hour.matches("[0-2][0-9]:[0-5][0-9]");
    }

    public static Date stringToDate(String date) {
        String[] dates = date.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt((dates[2])));
        cal.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
        cal.set(Calendar.DAY_OF_WEEK, Integer.parseInt(dates[0]));
        return cal.getTime();
    }

    public static Date stringsToDateAndTime(String date, String hour) {
        String[] dates = date.split("/");
        String[] hours = hour.split(":");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dates[2]));
        cal.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
        cal.set(Calendar.DAY_OF_WEEK, Integer.parseInt(dates[0]));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
        return cal.getTime();
    }
}
