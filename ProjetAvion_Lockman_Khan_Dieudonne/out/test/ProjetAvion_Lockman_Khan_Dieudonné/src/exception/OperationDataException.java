package exception;

public class OperationDataException extends Exception {
    public String getMessage(){
        return "Error while operating on the data";
    }
}
