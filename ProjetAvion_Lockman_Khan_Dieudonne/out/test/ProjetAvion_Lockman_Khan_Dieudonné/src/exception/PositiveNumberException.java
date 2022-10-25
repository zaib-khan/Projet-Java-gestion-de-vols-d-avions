package exception;

public class PositiveNumberException extends Exception {
    private int nombreErreur;

    public PositiveNumberException(int nombreErreur){
        this.nombreErreur = nombreErreur;
    }

    public String getMessage(){
        return "<< "+nombreErreur+" >> n'est pas un nombre positif!";
    }

}
