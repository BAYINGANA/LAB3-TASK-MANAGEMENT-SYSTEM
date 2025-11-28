package models;

import java.util.Scanner;

public class TaskCatalog {
    public Scanner scanner = new Scanner(System.in);
    public int choice ;

    private int taskId;
    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
    private int assignedUserId;
    private int projectID;

    public TaskCatalog(int taskId, String taskName, String taskDescription, TaskStatus notStarted, int projectID) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = TaskStatus.NOT_STARTED;
        this.assignedUserId = 0;
        this.projectID = projectID;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
    public int getProjectID(){
        return projectID;
    }

    @Override
    public String toString() {
        return "Task{id=" + taskId + ", name='" + taskName + "', status=" + taskStatus +
                ", assignedUserId=" + assignedUserId + ", projectId=" + projectID + "}";
    }

    public void createTask(){
//        System.out.println("Enter task ID: ");
//        taskId = scanner.nextInt();
//        System.out.println("Enter task name:");
//        taskName = scanner.nextLine();
//        System.out.println("Enter the task description:");
//        taskDescription = scanner.nextLine();
//        System.out.println("Eter assigned user ID:");
//        assignedUserId = scanner.nextInt();
    }

    public void displayProjectTasks(){

    }

    public void updateTaskDetails(){
        int newID;
        String newTaskName;
        String newTaskDescription;
        String newTaskStatus;
        String newTaskPriority;
        int newAssignedUserId;

        System.out.println("Enter new task ID:");
        newID = scanner.nextInt();
        taskId = newID;

        System.out.println("Enter new task name:");
        newTaskName = scanner.nextLine();
        taskName = newTaskName;

        System.out.println("Enter the task description:");
        newTaskDescription = scanner.nextLine();
        taskDescription = newTaskDescription;

        System.out.println("Eter assigned user ID:");
        newAssignedUserId = scanner.nextInt();
        assignedUserId = newAssignedUserId;
    }

    public void updateTaskStatus(){

    }

    public void deleteTask(){

    }

}

