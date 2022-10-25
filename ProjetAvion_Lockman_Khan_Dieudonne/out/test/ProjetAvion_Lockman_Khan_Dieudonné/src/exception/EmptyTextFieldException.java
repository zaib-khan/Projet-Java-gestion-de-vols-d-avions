package exception;

public class EmptyTextFieldException extends Exception {

    private String label_name;

    public EmptyTextFieldException(String label_name){
        this.label_name= label_name;
    }
    public String getMessage(){
        return "The field <<"+label_name+">> is empty !";
    }




}
