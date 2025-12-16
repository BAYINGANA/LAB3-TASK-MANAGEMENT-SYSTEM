package models;

import java.util.ArrayList;
import java.util.List;


public abstract class ProjectCatalog {
    protected String projectID;
    protected String projectName;
    protected String projectDescription;
    protected String projectCategory;
    protected String projectDeadline;
    protected List<TaskCatalog> tasks = new ArrayList<>();

    protected ProjectCatalog(String projectID, String projectName, String projectDescription, String projectCategory, String projectDeadline){
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectCategory = projectCategory;
        this.projectDeadline = projectDeadline;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }


    public void setProjectDeadline(String projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public void addTask(TaskCatalog task) {
        tasks.add(task);
    }

    public List<TaskCatalog> getTasks() {
        return tasks;
    }

    @Override
    public String toString(){
        return "User{id=" + projectID + ", name= " +projectName + ", Description = " + projectDescription + ", Deadline = "+ projectDeadline + ", Category = "+ projectCategory + "}";
     }
}
