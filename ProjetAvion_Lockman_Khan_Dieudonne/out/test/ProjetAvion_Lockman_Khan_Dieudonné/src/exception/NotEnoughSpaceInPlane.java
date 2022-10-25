package exception;

public class NotEnoughSpaceInPlane extends Exception {
    private Integer free_places;

    public NotEnoughSpaceInPlane(Integer free_places){
        this.free_places= free_places;
    }

    public String getMessage(){
        return "No more space in this flight, there is only "+free_places+" seat"+ (free_places>1?"s":"")+" left.";
    }
}
