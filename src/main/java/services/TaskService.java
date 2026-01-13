package services;

import utils.ConsoleMenu;
import utils.RegexValidator;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.InvalidInputException;
import utils.exceptions.TaskNotFoundException;
import models.ProjectCatalog;
import models.TaskCatalog;
import models.TaskStatus;
import utils.ValidationUtils;

import java.util.*;

public class TaskService {
    private static final Map<String,TaskCatalog> tasks = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int taskCounter = 0;

    public TaskService(List<TaskCatalog> loadedTasks) {
        tasks.clear();
        if (loadedTasks != null) loadedTasks.forEach(t -> tasks.put(t.getTaskId(), t));
        initializeTaskrCounter(loadedTasks);
    }
    public TaskService() { this(new ArrayList<>()); }

    private static void initializeTaskrCounter(List<TaskCatalog> loadedTasks) {
        int max = 0;

        for (TaskCatalog task : loadedTasks) {
            String id = task.getTaskId(); // e.g. UR012
            int num = Integer.parseInt(id.substring(2));
            if (num > max) {
                max = num;
            }
        }
        taskCounter = max;
    }

    //Generate ID
    private static String generateTaskId() {
        taskCounter++;
        String taskId = "TS" + String.format("%03d", taskCounter);
        RegexValidator.isValidTaskId(taskId);
        System.out.println("Auto-generated Task ID: " + taskId);
        return taskId;
    }

    public List<TaskCatalog> getAllTasks() {

        return new ArrayList<>(tasks.values());
    }

    public TaskCatalog findTaskById(String id) throws TaskNotFoundException {
        TaskCatalog task = tasks.get(id);
        if (task == null) throw new TaskNotFoundException("TASK NOT FOUND!!");
        return task;
    }

    /*=========================CREATE TASK=======================*/

    public void createTaskMenu(ProjectService projectService, UserService userService) {
        String taskId = generateTaskId();

        //validate name
        String name;
        while (true) {
            System.out.println("Enter task name:");
            name = scanner.nextLine();
            try {
                ValidationUtils.isValidName(name);
                break;
            }catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }

        //validate description input
        String desc;
        do {
            System.out.println("Enter task description:");
            desc = scanner.nextLine();

        } while (!ValidationUtils.isNotEmpty(desc));

        System.out.println("Available projects:");
        try {
            projectService.displayProjects();
        }catch (EmptyProjectException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Enter project ID to assign this task:");
        String projectId = scanner.nextLine();
        ProjectCatalog project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found, aborting.");
            return;
        }

        System.out.println("Available users:");
        userService.displayAllUsers();

        System.out.println("Enter user ID to assign this task (or 0 for unassigned):");
        String assignedUserId = scanner.nextLine();

        TaskCatalog task = new TaskCatalog(taskId, name, desc, project.getProjectID());
        if (!assignedUserId.equals("0")) {
            boolean userExists = userService.getAllUsers()
                    .stream()
                    .anyMatch(u -> u.getId().equals(assignedUserId));

            if (!userExists) {
                System.out.println("User does not exist. Task not created.");
                return;
            }

            task.setAssignedUserId(assignedUserId);
        }
        tasks.put(taskId, task);
        project.addTask(task);
        System.out.println("Task created and assigned to project.");
    }

    /*=====================DISPLAY TASKS=================*/

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("NO TASKS AVAILABLE");
        }
        tasks.values().forEach(System.out::println);
    }

   /*=======================DELETE TASK====================*/

    public void deleteTask() throws TaskNotFoundException {
        System.out.println("Available Tasks:");
        System.out.println("================");
        displayTasks();
        if(tasks.isEmpty()){
            System.out.println("No tasks to delete");
        }else {
            System.out.println("Enter task id to delete:");
            String taskId = scanner.nextLine();
            if (tasks.remove(taskId) != null) {
                System.out.println("Task deleted.");
            } else {
                throw new TaskNotFoundException("Task not found.");
            }
        }
    }

    /*==========================UPDATE TASK==================*/

    public void updateTaskMenu(ProjectService projectService, UserService userService) throws TaskNotFoundException {
        System.out.println("Available Tasks:");
        System.out.println("================");
        displayTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to update");
        } else {
            System.out.println("Enter task ID to update:");
            String id = scanner.nextLine();
            TaskCatalog task;
            task = findTaskById(id);
            if (task == null) {
                throw new TaskNotFoundException("Task not found.");
            }
            ConsoleMenu console = new ConsoleMenu(projectService, userService, this);
            console.showUpdateOptions(task);
            System.out.println("Task updated.");
        }
    }

     public void updateTaskName(TaskCatalog task){
         System.out.println("Enter new task name:");
         String name = scanner.nextLine();
         try {
             ValidationUtils.isValidName(name);
             task.setTaskName(name);
             System.out.println("Task name updated.");
         } catch (InvalidInputException e) {
             System.out.println(e.getMessage());
         }
     }
    public void updateTaskDescription(TaskCatalog task) {
        System.out.println("Enter new description:");
        task.setTaskDescription(scanner.nextLine());
        System.out.println("Description updated.");
    }

    public void updateTaskStatus(TaskCatalog task) {
        System.out.println("Enter new status (NOT_STARTED, IN_PROGRESS, COMPLETED):");
        String status = scanner.nextLine();

        try {
            task.setTaskStatus(TaskStatus.valueOf(status));
            System.out.println("Status updated.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status.");
        }
    }

    public void updateAssignedUser (TaskCatalog task, UserService userService) throws InvalidInputException {
        System.out.println("Available users:");
        userService.displayAllUsers();
        System.out.println("Enter new assigned user ID (0 for unassigned):");

        String assignedUserId = scanner.nextLine();
        if (!assignedUserId.equals("0")) {
            boolean userExists = userService.getAllUsers()
                    .stream()
                    .anyMatch(u -> u.getId().equals(assignedUserId));

            if (!userExists) {
                System.out.println("User does not exist. Task not updated.");
                return;
            }

            task.setAssignedUserId(assignedUserId);
        }
        System.out.println("Assigned user updated.");
    }

    public void updateProject(TaskCatalog task, ProjectService projectService) {
        System.out.println("Available projects:");
        try {
            projectService.displayProjects();
        }catch (EmptyProjectException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Enter new project ID:");

        try {
            String projectId = scanner.nextLine();
            ProjectCatalog project = projectService.findProjectById(projectId);
            if (project == null) {
                System.out.println("Project not found.");
            }else {
                task.setProjectID(projectId);
                System.out.println("Project updated.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    public void linkTasksToProjects(ProjectService projectService) {
        List<ProjectCatalog> allProjects = projectService.getAllProjects();

        for (TaskCatalog task : tasks.values()) {
            String projectId = task.getProjectID();
            ProjectCatalog project = allProjects.stream()
                    .filter(p -> p.getProjectID().equals(projectId))
                    .findFirst()
                    .orElse(null);

            if (project != null) {
                project.addTask(task);
            }
        }
        System.out.println("[INFO] Linked " + tasks.size() + " tasks to their projects");
    }
}
