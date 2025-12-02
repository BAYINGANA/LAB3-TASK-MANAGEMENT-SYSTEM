package models;

import java.util.ArrayList;
import java.util.List;


public abstract class ProjectCatalog {
    protected int projectID;
    protected String projectName;
    protected String projectDescription;
    protected String projectCategory;
    protected String projectDeadline;
    protected List<TaskCatalog> tasks = new ArrayList<>();

    public abstract void createProject();
    public abstract void displayAllProjects();
    public abstract void displayProject();
    public abstract void updateProject();
    public abstract void filterProject();
    public abstract void deleteProject();

    public double getCompletionPercentage(){
        if (tasks.isEmpty()){
            return 0;
        }
        double completed = tasks.stream()
                .filter(t -> t.getTaskStatus()== TaskStatus.COMPLETED)
                .count();
        return (completed * 100.0) / tasks.size();
    }

    protected ProjectCatalog(int projectID, String projectName, String projectDescription, String projectCategory, String projectDeadline){
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectCategory = projectCategory;
        this.projectDeadline = projectDeadline;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<TaskCatalog> getTasks() {
        return tasks;
    }

//    public void setProjectID(int projectID) {
//        this.projectID = projectID;
//    }

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

    
    public void updateTask(TaskCatalog task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId() == task.getTaskId()) {
                tasks.set(i, task);
                return;
            }
        }
       
        tasks.add(task);
    }

    @Override
    public String toString(){
        return "User{id=" + projectID + ", name='" +projectName + ", Description = " + projectDescription + ", Deadline = "+ projectDeadline + ", Category = "+ projectCategory + "}";
     }
}

