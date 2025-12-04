package models;

public class TaskCatalog {

    private final String taskId;
    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
    private int assignedUserId;
    private int projectID;

    public TaskCatalog(String taskId, String taskName, String taskDescription, int projectID) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = TaskStatus.NOT_STARTED;
        this.assignedUserId = 0;
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

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        return "Task{id=" + taskId + ", name='" + taskName + "', status=" + taskStatus + ", Description= " + taskDescription +
                ", assignedUserId=" + assignedUserId + ", projectId=" + projectID + "}";
    }
}

