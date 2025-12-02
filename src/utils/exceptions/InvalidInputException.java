package utils.exceptions;

public class InvalidInputException extends Exception{
    public InvalidInputException(){
        super("!!INVALID INPUT!!");
    }

    public InvalidInputException(String message){
        super(message);
    }
}
