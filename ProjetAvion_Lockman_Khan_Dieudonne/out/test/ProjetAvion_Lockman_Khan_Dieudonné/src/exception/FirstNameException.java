package exception;

public class FirstNameException extends Exception{

    private String NameError;

    public FirstNameException(String nameError){
        this.NameError = nameError;
    }

    public String getMessage(){
        return NameError+" n'est pas un prénom";
    }
    public String getTitleName(){
        return "Erreur prénom";
    }


}
