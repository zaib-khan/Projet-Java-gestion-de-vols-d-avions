package exception;

public class NoOneAdded extends Exception {

    public NoOneAdded(){

    }

    public String getMessage(){
        return "You didn't add anyone!";
    }
}
