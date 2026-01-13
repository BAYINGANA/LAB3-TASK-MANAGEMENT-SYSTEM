package services;

import utils.exceptions.EmptyProjectException;
import models.*;

import java.util.List;
import interfaces.CompletablesReport;

public class ReportService implements CompletablesReport{

    @Override
    public void projectCompletionSummary(List<ProjectCatalog> projects) throws EmptyProjectException {
        if (projects.isEmpty()) {
            throw new EmptyProjectException("No projects found.");
        }
        System.out.println("======Project Completion Summary======");
        for (ProjectCatalog project : projects) {
            System.out.println("Project " + project.getProjectName() + ": " + getCompletionPercentage(project) + "% complete.");
        }
    }
    public double getCompletionPercentage(ProjectCatalog projectCatalog){
        List<TaskCatalog> tasks = projectCatalog.getTasks();
        if (tasks.isEmpty()){
            return 0.0;
        }
        double completed = tasks.stream()
                .filter(t -> t.getTaskStatus()== TaskStatus.COMPLETED)
                .count();
        return(completed * 100.0) / tasks.size();
    }

    @Override
    public void taskCompletionSummary(List<TaskCatalog> tasks) throws EmptyProjectException{
        if (tasks.isEmpty()) {
            throw new EmptyProjectException("No tasks found.");
        }
        double completed = tasks.stream().filter(t -> t.getTaskStatus() == TaskStatus.COMPLETED).count();
        double inProgress = tasks.stream() .filter(t ->t.getTaskStatus()==TaskStatus.IN_PROGRESS).count();
        double notStarted = tasks.stream() .filter(t ->t.getTaskStatus()==TaskStatus.NOT_STARTED).count();
        double total = tasks.size();
        double percent = (completed / total) * 100;
        System.out.println("Task Completion Summary:");
        System.out.println("Total tasks: " + tasks.size());
        System.out.println("Completed: " + completed);
        System.out.println("In-Progress: " + inProgress);
        System.out.println("Not yet started: " + notStarted);
        System.out.printf("Percentage of completed tasks: %.2f%%%n" , percent);
    }

    @Override
    public void userWorkloadSummary(List<UserCatalog> users, List<TaskCatalog> tasks) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("User Workload Summary:");
        for (UserCatalog user : users) {
            long count = tasks.stream().filter(task -> task.getAssignedUserId().equals(user.getId())).count();
            System.out.println("User " + user.getName() + " has " + count + " assigned tasks.");
        }

    }
}
