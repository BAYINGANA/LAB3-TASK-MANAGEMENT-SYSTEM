package models;

public class TaskCatalog {

    private final String taskId;
    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
    private String assignedUserId;
    private String projectID;

    public TaskCatalog(String taskId, String taskName, String taskDescription, String projectID) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = TaskStatus.NOT_STARTED;
        this.assignedUserId = "0";
        this.projectID = projectID;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }


    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        return "Task{id=" + taskId + ", name='" + taskName + "', status=" + taskStatus + ", Description= " + taskDescription +
                ", assignedUserId=" + assignedUserId + ", projectId=" + projectID + "}";
    }
}
