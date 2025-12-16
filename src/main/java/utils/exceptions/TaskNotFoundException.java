package utils.exceptions;

public class TaskNotFoundException extends EmptyProjectException{
    public TaskNotFoundException(String message){
        super(message);
    }

}
