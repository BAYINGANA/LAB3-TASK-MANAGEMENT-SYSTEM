package utils;

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
        boolean running = true;
        while (running) {
            System.out.println("******************");
            System.out.println("* DASHBOARD MENU *");
            System.out.println("******************");
            System.out.println("1. User Management");
            System.out.println("2. Project Management");
            System.out.println("3. Task Management");
            System.out.println("4. Reports");
            System.out.println("5.Exit System");
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
                        running = false;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }

    public void UserMenu(){
        int userId;

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
        System.out.println("\n Enter choice: " );

        choice = scanner.nextInt();

        if(choice != 0 ){
            switch (choice){
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
                    System.out.println("Return pending...");
                    break;
                default:
                    System.out.println("please enter a valid choice");
            }

        }
    }

    public void ProjectMenu(){
        System.out.println("**********************");
        System.out.println("* PROJECT MANAGEMENT *");
        System.out.println("**********************");
        System.out.println("1. Create new project");
        System.out.println("2. Display projects");
        System.out.println("3. Display project Details");
        System.out.println("4. Update project Details");
        System.out.println("5. Filter Projects");
        System.out.println("6. Delete Project");
        System.out.println("7. Return To Main Menu");
        System.out.println("\n Enter choice: ");

        choice = scanner.nextInt();

        if(choice != 0 ){
            switch (choice){
                case 1:
                    System.out.println(" project creation ");
                    projectService.createProject();
                    break;
                case 2:
                    System.out.println("project list display");
                    try {
                        projectService.displayProjects();
                    } catch (Exception t){
                        System.out.println(t.getMessage());
                    }

                    break;
                case 3:
                    System.out.println("project details");
                    projectService.getAllProjects();
                    break;
                case 4:
                    System.out.println("update");
                    projectService.updateProjectMenu();
                    break;
                case 5:
                    System.out.println("Delete");
                    projectService.deleteProject();
                    break;
                case 6:
                    System.out.println("Search");
                    System.out.println("Enter project id to search:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    projectService.findProjectById(id);
                    break;
                case 7:
                    System.out.println("Return");
                    new ConsoleMenu();
                    break;
                default:
                    System.out.println("please enter a valid choice");
            }

        }
    }

    public void TaskMenu(){
        System.out.println("*******************");
        System.out.println("* TASK MANAGEMENT *");
        System.out.println("*******************");
        System.out.println("1. Create new task");
        System.out.println("2. Display project tasks");
        System.out.println("3. Update task");
        System.out.println("4. Delete task");
        System.out.println("5. Return To Main Menu");
        System.out.println("\n Enter choice: " );

        choice = scanner.nextInt();

        if(choice != 0 ){
            switch (choice){
                case 1:
                    System.out.println(" Task creation ");
                    taskService.createTaskMenu(projectService);
                    break;
                case 2:
                    System.out.println("Task list display");
                    try {
                        taskService.displayTasks();
                    }catch (EmptyProjectException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Task update ");
                    try {
                        taskService.updateTaskMenu();
                    }catch (TaskNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Delete pending");
                    try {
                        taskService.deleteTask();
                    }catch (TaskNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Return ");
                    new ConsoleMenu();
                    break;
                default:
                    System.out.println("please enter a valid choice");
            }

        }
    }



    public void ReportsMenu(){
        System.out.println("**********************");
        System.out.println("* Reports MANAGEMENT *");
        System.out.println("**********************");
        System.out.println("1. Project Completion Summary");
        System.out.println("2. Task Completion Summary");
        System.out.println("3. User Workload Summary");
        System.out.println("4. Return To Main Menu");
        System.out.println("\n Enter choice: " );

        choice = scanner.nextInt();
        scanner.nextLine();

        if(choice != 0 ){
            switch (choice){
                case 1:
                    reportService.projectCompletionSummary(projectService.getAllProjects());
                    break;
                case 2:
                    reportService.taskCompletionSummary(taskService.getAllTasks());
                    break;
                case 3:
                    reportService.userWorkloadSummary(userService.getAllUsers(), taskService.getAllTasks());
                    break;
                case 4:
                    System.out.println("Returning");
                    new ConsoleMenu();
                    break;
                default:
                    System.out.println("please enter a valid choice");
            }

        }
    }
}

