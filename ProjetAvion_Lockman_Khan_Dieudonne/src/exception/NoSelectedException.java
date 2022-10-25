package exception;

public class NoSelectedException extends Exception {
    private String label_name;

    public NoSelectedException(String label_name){
        this.label_name= label_name;
    }

    public String getMessage(){
        return "You did not select a "+label_name;
    }
}
