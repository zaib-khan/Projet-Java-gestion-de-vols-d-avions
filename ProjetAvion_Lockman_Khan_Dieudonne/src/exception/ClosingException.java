package exception;

public class ClosingException extends Exception {

    public String getMessage(){
        return "Failed to close the connection to the data.";
    }
}
