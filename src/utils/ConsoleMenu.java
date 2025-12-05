package utils;

import models.TaskCatalog;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import services.UserService;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.TaskNotFoundException;

import java.util.Scanner;

public class ConsoleMenu {
    private  static final Scanner scanner =new Scanner(System.in);
    int choice ;
    private final UserService userService = new UserService();
    private final ProjectService projectService = new ProjectService();
    private final TaskService taskService = new TaskService();
    private  final ReportService reportService = new ReportService();

    public void MainMenu(){
        while (true) {
            System.out.println("******************");
            System.out.println("* DASHBOARD MENU *");
            System.out.println("******************");
            System.out.println("1. User Management");
            System.out.println("2. Project Management");
            System.out.println("3. Task Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit System");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" User management");
                        UserMenu();
                        break;
                    case 2:
                        System.out.println("Project management");
                        ProjectMenu();
                        break;
                    case 3:
                        System.out.println("TAsk Management");
                        TaskMenu();
                        break;
                    case 4:
                        System.out.println("Report management");
                        ReportsMenu();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }

    public void UserMenu(){
        int userId;

        while(true) {
            System.out.println("*******************");
            System.out.println("* USER MANAGEMENT *");
            System.out.println("*******************");
            System.out.println("1. Create new user");
            System.out.println("2. View All Users");
            System.out.println("3. View User Details");
            System.out.println("4. Activate and Deactivate User");
            System.out.println("5. Update User Information");
            System.out.println("6. Delete User");
            System.out.println("7. Search User");
            System.out.println("8. Return to Main Menu");
            System.out.println("9. Exit system");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" User creation ");
                        userService.createUser();
                        break;
                    case 2:
                        System.out.println("Users display");
                        userService.getAllUsers();
                        break;
                    case 3:
                        System.out.println("User display");
                        System.out.println("Enter user id to display:");
                        userId = scanner.nextInt();
                        scanner.nextLine();
                        userService.findUserById(userId);
                        break;
                    case 4:
                        System.out.println("Status");
                        userService.userActivation();
                        break;
                    case 5:
                        System.out.println("update");
                        userService.updateUser();
                        break;
                    case 6:
                        System.out.println("Delete");
                        userService.deleteUser();
                        break;
                    case 7:
                        System.out.println("Search");
                        userService.searchUser();
                        break;
                    case 8:
                        System.out.println("Returning to main menu");
                        MainMenu();
                    case 9:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("please enter a valid choice");
                }
            }
        }
    }

    public void ProjectMenu(){
        while (true) {
            System.out.println("**********************");
            System.out.println("* PROJECT MANAGEMENT *");
            System.out.println("**********************");
            System.out.println("1. Create new project");
            System.out.println("2. Display projects");
            System.out.println("3. Update project Details");
            System.out.println("4. Filter Projects");
            System.out.println("5. Delete Project");
            System.out.println("6. Return To Main Menu");
            System.out.println("7. Exit system");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" project creation ");
                        projectService.createProject();
                        break;
                    case 2:
                        System.out.println("project list display");
                        try {
                            projectService.displayProjects();
                        } catch (EmptyProjectException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    case 3:
                        System.out.println("update");
                        projectService.updateProjectMenu();
                        break;
                    case 4:
                        System.out.println("Delete");
                        projectService.deleteProject();
                        break;
                    case 5:
                        System.out.println("Search");
                        System.out.println("Enter project id to search:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        projectService.findProjectById(id);
                        break;
                    case 6:
                        System.out.println("Return");
                        MainMenu();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }

    public void TaskMenu(){

        while (true) {
            System.out.println("*******************");
            System.out.println("* TASK MANAGEMENT *");
            System.out.println("*******************");
            System.out.println("1. Create new task");
            System.out.println("2. Display project tasks");
            System.out.println("3. Update task");
            System.out.println("4. Delete task");
            System.out.println("5. Return To Main Menu");
            System.out.println("6.Exit system");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" Task creation ");
                        taskService.createTaskMenu(projectService, userService);
                        break;
                    case 2:
                        System.out.println("Task list display");
                        taskService.displayTasks();
                        break;
                    case 3:
                        System.out.println("Task update ");
                        try {
                            taskService.updateTaskMenu();
                        } catch (TaskNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            taskService.deleteTask();
                        } catch (TaskNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Return ");
                        MainMenu();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }
    public void showUpdateOptions(TaskCatalog task) {
        while (true) {
            System.out.println("\n==== UPDATE MENU ====");
            System.out.println("1. Update Task Name");
            System.out.println("2. Update Task Description");
            System.out.println("3. Update Task Status");
            System.out.println("4. Update Assigned User");
            System.out.println("5. Update Project");
            System.out.println("6. Exit Update Menu");
            System.out.println("7. Exit system");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            if (choice != 0) {

                switch (choice) {
                    case 1:
                        taskService.updateTaskName(task);
                        break;

                    case 2:
                        taskService.updateTaskDescription(task);
                        break;

                    case 3:
                        taskService.updateTaskStatus(task);
                        break;

                    case 4:
                        taskService.updateAssignedUser(task);
                        break;

                    case 5:
                        taskService.updateProject(task);
                        break;

                    case 6:
                        System.out.println("Exiting update menu...");
                        MainMenu();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }




    public void ReportsMenu() {

        while (true) {
            System.out.println("**********************");
            System.out.println("* Reports MANAGEMENT *");
            System.out.println("**********************");
            System.out.println("1. Project Completion Summary");
            System.out.println("2. Task Completion Summary");
            System.out.println("3. User Workload Summary");
            System.out.println("4. Return To Main Menu");
            System.out.println("5. Exit system");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        try {
                            reportService.projectCompletionSummary(projectService.getAllProjects());
                        }catch (EmptyProjectException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            reportService.taskCompletionSummary(taskService.getAllTasks());
                        }catch (EmptyProjectException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        reportService.userWorkloadSummary(userService.getAllUsers(), taskService.getAllTasks());
                        break;
                    case 4:
                        System.out.println("Returning");
                        MainMenu();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }
}

