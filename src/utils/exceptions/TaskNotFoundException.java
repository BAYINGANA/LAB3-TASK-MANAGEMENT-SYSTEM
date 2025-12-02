package utils.exceptions;

public class TaskNotFoundException extends EmptyProjectException{
    public TaskNotFoundException(){
        super("Task List is Empty");
    }
    public TaskNotFoundException(String message){
        super(message);
    }

}
