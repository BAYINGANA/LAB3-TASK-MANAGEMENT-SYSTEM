package utils;

import models.ProjectCatalog;
import models.TaskCatalog;
import models.UserCatalog;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import services.UserService;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.InvalidInputException;
import utils.exceptions.TaskNotFoundException;

import java.util.Scanner;


public class ConsoleMenu {
    private static final Scanner scanner = new Scanner(System.in);
    int choice;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ReportService reportService = new ReportService();

    private void saveData() {
        FileUtils.saveAll(userService.getAllUsers(),
                projectService.getAllProjects(),
                taskService.getAllTasks());
    }

    public ConsoleMenu(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    public void MainMenu(){
        while (true) {
            System.out.println("******************");
            System.out.println("* DASHBOARD MENU *");
            System.out.println("******************");
            System.out.println("1. User Management");
            System.out.println("2. Project Management");
            System.out.println("3. Task Management");
            System.out.println("4. Reports");
            System.out.println("5. Simulate Concurrent Task Updates");
            System.out.println("6. Exit System");
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
                        System.out.println("Task Management");
                        TaskMenu();
                        break;
                    case 4:
                        System.out.println("Report management");
                        ReportsMenu();
                        break;
                    case 5:
                        System.out.println("Concurrency Simulation");
                        ConcurrencyMenu();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }
    public void ConcurrencyMenu() {
        System.out.println("*****************************");
        System.out.println("* CONCURRENCY DEMONSTRATION *");
        System.out.println("*****************************");
        System.out.println("Enter number of threads:");
        int threads = scanner.nextInt();
        System.out.println("Enter updates per thread:");
        int updates = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nStarting concurrent task updates...");
        System.out.println("Threads: " + threads + " | Updates per thread: " + updates);
        System.out.println("-".repeat(50));

        TaskUpdateSimulator.simulateConcurrentUpdates(taskService, threads, updates);

        System.out.println("-".repeat(50));
        System.out.println("Simulation complete. Saving data...");
        saveData();
        System.out.println("Press Enter to return to main menu...");
        scanner.nextLine();
    }

    public void UserMenu(){
        String userId;

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
            System.out.println("7. Search User By Name");
            System.out.println("8. Return to Main Menu");
            System.out.println("9. Exit system");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" User creation ");
                        try {
                            userService.createUser();
                            saveData();
                        }catch (InvalidInputException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Users display");
                        userService.displayAllUsers();
                        break;
                    case 3:
                        System.out.println("User display");
                        System.out.println("Enter user id to display:");
                        userId = scanner.nextLine();
                        try {
                            userService.findUserById(userId);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Status");
                        userService.userActivation();
                        break;
                    case 5:
                        System.out.println("update");
                        try {
                            userService.updateUser(projectService, taskService);
                            saveData();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.println("Delete");
                        userService.deleteUser();
                        saveData();
                        break;
                    case 7:
                        System.out.println("Search");
                        userService.searchUser();
                        break;
                    case 8:
                        System.out.println("Returning to main menu");
                        return;
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

    public void showUserUpdateOptions(UserCatalog user) {
        while (true) {
            System.out.println("\n==== UPDATE MENU ====");
            System.out.println("1. Update User Name");
            System.out.println("2. Update User Email");
            System.out.println("3. Update Password");
            System.out.println("4. Exit Update Menu");
            System.out.println("5. Exit system");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            if (choice != 0) {

                switch (choice) {
                    case 1:
                        userService.updateUserName(user);
                        break;

                    case 2:
                        userService.updateUserEmail(user);
                        break;

                    case 3:
                        userService.updateUserPassword(user);
                        break;
                    case 4:
                        System.out.println("Exiting update menu...");
                        return;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
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
            System.out.println("4. Delete Project");
            System.out.println("5. Search Project");
            System.out.println("6. Return To Main Menu");
            System.out.println("7. Exit system");
            System.out.println("\n Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" project creation ");
                        projectService.createProject();
                        saveData();
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
                        projectService.updateProjectMenu(userService,taskService);
                        saveData();
                        break;
                    case 4:
                        System.out.println("Delete");
                        projectService.deleteProject();
                        break;
                    case 5:
                        System.out.println("Search");
                        System.out.println("Enter project id to search:");
                        String id = scanner.nextLine();
                        projectService.findProjectById(id);
                        break;
                    case 6:
                        System.out.println("Return");
                        return;
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
    public void showProjectUpdateOptions(ProjectCatalog project) {
        while (true) {
            System.out.println("\n==== UPDATE MENU ====");
            System.out.println("1. Update Project Name");
            System.out.println("2. Update Project Description");
            System.out.println("3. Update Project Deadline");
            System.out.println("4. Exit Update Menu");
            System.out.println("5. Exit system");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            if (choice != 0) {

                switch (choice) {
                    case 1:
                        projectService.updateProjectName(project);
                        break;

                    case 2:
                        projectService.updateProjectDescription(project);
                        break;

                    case 3:
                        projectService.updateProjectDeadline(project);
                        break;

                    case 4:
                        System.out.println("Exiting update menu...");
                        return;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
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
            scanner.nextLine();

            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(" Task creation ");
                        taskService.createTaskMenu(projectService, userService);
                        saveData();
                        break;
                    case 2:
                        System.out.println("Task list display");
                        taskService.displayTasks();
                        break;
                    case 3:
                        System.out.println("Task update ");
                        try {
                            taskService.updateTaskMenu(projectService, userService);
                            saveData();
                        } catch (TaskNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            taskService.deleteTask();
                            saveData();
                        } catch (TaskNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Return ");
                        return;
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
                        try {
                            taskService.updateAssignedUser(task, userService);
                        }catch (InvalidInputException e){
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 5:
                        taskService.updateProject(task, projectService);
                        break;

                    case 6:
                        System.out.println("Exiting update menu...");
                        return;
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
                        return;
                    case 5:
                        System.out.println("Exiting...");
                        saveData();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("please enter a valid choice");
                }

            }
        }
    }
}
