package exception;

public class TooManyMeals extends Exception {
    private String source;

    public TooManyMeals(String source){
        this.source = source;
    }

    public String getMessage(){
        return "You have selected too many "+source+" meals";
    }
}
