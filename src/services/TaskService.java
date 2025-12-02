package services;

import models.ProjectCatalog;
import models.TaskCatalog;
import models.TaskStatus;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.TaskNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskService {
    private final List<TaskCatalog> tasks = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static int taskCounter = 0;

    private static int generateTaskId() {
        taskCounter++;
        System.out.println("Auto-generated Task ID: TS" + String.format("%03d", taskCounter));
        return taskCounter;
    }

    public void addTask(TaskCatalog task) {
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    public List<TaskCatalog> getAllTasks() {
        return tasks;
    }

    public TaskCatalog findTaskById(int id) throws TaskNotFoundException {
        for (TaskCatalog task : tasks) {
            if (task.getTaskId() == id) {
                return task;
            }
        }
        throw new TaskNotFoundException("TASK NOT FOUND!!");
    }
    public void createTaskMenu(ProjectService projectService) {
        int taskId = generateTaskId();
        System.out.println("Enter task name:");
        String name = scanner.nextLine();
        System.out.println("Enter task description:");
        String desc = scanner.nextLine();
        System.out.println("Available projects:");
        try {
            projectService.displayProjects();
        }catch (EmptyProjectException e){
            System.out.println(e.getMessage());
        }
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

    public void displayTasks() throws EmptyProjectException{
        if (tasks.isEmpty()) {
            throw new EmptyProjectException("No tasks available.");
        }
        for (TaskCatalog task : tasks) {
            System.out.println(task);
        }
    }

    public void updateTaskMenu() throws TaskNotFoundException{
        System.out.println("Enter task ID to update:");
        int id = Integer.parseInt(scanner.nextLine());
        TaskCatalog task = findTaskById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task not found.");
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
    // assigned user is not requested here because TaskCatalog has a default; keep existing assignment

    ProjectService projectService = new ProjectService();
    ProjectCatalog project = projectService.findProjectById(task.getProjectID());
        if (project != null) {
            project.updateTask(task);

        }

        System.out.println("Task updated.");
    }

    public void deleteTask() throws TaskNotFoundException {
        System.out.println("Enter task ID to delete:");
        int id = Integer.parseInt(scanner.nextLine());
        TaskCatalog task = findTaskById(id);
        if (task != null && tasks.remove(task)) {
            System.out.println("Task deleted.");
        } else {
            throw new TaskNotFoundException("Task not found.");
        }
    }
}
