package services;

import models.ProjectCatalog;
import models.TaskCatalog;
import models.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskService {
    private final List<TaskCatalog> tasks = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addTask(TaskCatalog task) {
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    public List<TaskCatalog> getAllTasks() {
        return tasks;
    }

    public TaskCatalog findTaskById(int id) {
        for (TaskCatalog task : tasks) {
            if (task.getTaskId() == id) {
                return task;
            }
        }
        return null;
    }
    public void createTaskMenu(ProjectService projectService) {
        System.out.println("Enter task ID:");
        int taskId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter task name:");
        String name = scanner.nextLine();
        System.out.println("Enter task description:");
        String desc = scanner.nextLine();
        System.out.println("Available projects:");
        projectService.displayProjects();

        System.out.println("Enter project ID to assign this task:");
        int projectId = Integer.parseInt(scanner.nextLine());
        ProjectCatalog project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found, aborting.");
            return;
        }
       
        UserService userService = new UserService();
        System.out.println("Available users:");
        for (models.UserCatalog u : userService.getAllUsers()) {
            System.out.println(u);
        }
        System.out.println("Enter user ID to assign this task (or 0 for unassigned):");
        int assignedUserId = Integer.parseInt(scanner.nextLine());

        TaskCatalog task = new TaskCatalog(taskId, name, desc, TaskStatus.NOT_STARTED, project.getProjectID());
        if (assignedUserId > 0) {
            task.setAssignedUserId(assignedUserId);
        }
        tasks.add(task);
        project.addTask(task);
        System.out.println("Task created and assigned to project.");
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        for (TaskCatalog task : tasks) {
            System.out.println(task);
        }
    }

    public void updateTaskMenu() {
        System.out.println("Enter task ID to update:");
        int id = Integer.parseInt(scanner.nextLine());
        TaskCatalog task = findTaskById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        System.out.println("Enter new task name:");
        task.setTaskName(scanner.nextLine());
        System.out.println("Enter new task description:");
        task.setTaskDescription(scanner.nextLine());
    System.out.println("Enter new task status (NOT_STARTED, IN_PROGRESS, COMPLETED):");
        String status = scanner.nextLine();
        try {
            task.setTaskStatus(TaskStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status input.");
        }
   

    ProjectService projectService = new ProjectService();
    ProjectCatalog project = projectService.findProjectById(task.getProjectID());
        if (project != null) {
            project.updateTask(task);

        }

        System.out.println("Task updated.");
    }

    public void deleteTask() {
        System.out.println("Enter task ID to delete:");
        int id = Integer.parseInt(scanner.nextLine());
        TaskCatalog task = findTaskById(id);
        if (task != null && tasks.remove(task)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }
}

