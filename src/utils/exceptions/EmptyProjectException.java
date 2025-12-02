package utils.exceptions;

public class EmptyProjectException extends Exception {
    public EmptyProjectException (){
        super("This project is empty");
    }

    public EmptyProjectException(String message){
        super(message);
    }
}
