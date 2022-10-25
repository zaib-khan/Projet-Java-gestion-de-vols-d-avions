package exception;

public class ResearchDateException extends Exception{

    public String getMessage(){
        return "Start and End dates are invalide.\nThe Start date must be older then the End date.";
    }



}
