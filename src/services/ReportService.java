package services;

import models.*;
import java.util.List;

public class ReportService {
    public void projectCompletionSummary(List<ProjectCatalog> projects) {
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }
        System.out.println("Project Completion Summary:");
        for (ProjectCatalog project : projects) {
            System.out.println("Project " + project.getProjectName() + ": " + project.getCompletionPercentage() + "% complete.");
        }
    }

    public void taskCompletionSummary(List<TaskCatalog> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        long completed = tasks.stream().filter(t -> t.getTaskStatus() == TaskStatus.COMPLETED).count();
        System.out.println("Task Completion Summary:");
        System.out.println("Total tasks: " + tasks.size());
        System.out.println("Completed: " + completed);
    }

    public void userWorkloadSummary(List<UserCatalog> users, List<TaskCatalog> tasks) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("User Workload Summary:");
        for (UserCatalog user : users) {
            double count = tasks.stream().filter(task -> task.getAssignedUserId() == user.getId()).count();
            System.out.println("User " + user.getName() + " has " + count + " assigned tasks.");
        }
    }
}
