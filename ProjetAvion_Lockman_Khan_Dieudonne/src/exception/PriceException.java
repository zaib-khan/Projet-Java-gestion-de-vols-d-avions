package exception;

public class PriceException extends Exception {
    private String prixErreur;

    public PriceException(String prixErreur){
        this.prixErreur= prixErreur;
    }
    public PriceException(Double prixErreur){
        this.prixErreur = prixErreur.toString();
    }

    public String getMessage(){
        return "<< "+prixErreur+" >> is not a valid price";
    }

}
