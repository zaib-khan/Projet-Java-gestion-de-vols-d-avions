package exception;

public class NameException extends Exception {

    private String NameError;

    public NameException(String nameError){
        this.NameError = nameError;
    }

    public String getMessage(){
        return "<<"+NameError+">> n'est pas un nom";
    }





}
