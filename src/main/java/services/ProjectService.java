package services;

import models.HardwareProject;
import models.ProjectCatalog;
import models.SoftwareProject;
import utils.ConsoleMenu;
import utils.ValidationUtils;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.InvalidInputException;

import java.util.*;

public class ProjectService {
    private static final Map<String,ProjectCatalog> projects = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int projectCounter = 0;

    //Generate ID for projrct

    private static String generateProjectId() {
        projectCounter++;
        String projectId = "PR" + String.format("%03d", projectCounter);
        System.out.println("Auto-generated Project ID: " + projectId);
        return projectId;
    }

    public void addProject(ProjectCatalog project) {
        projects.put(project.getProjectID(),project);
        System.out.println("Project added successfully.");
    }

    public List<ProjectCatalog> getAllProjects() {

        return new ArrayList<>(projects.values());
    }

    /*==================SEARCH PROJECT===============*/

    public ProjectCatalog findProjectById(String id) {
        return projects.get(id);
    }

    /*==================CREATE PROJECT=================*/

    public void createProject() {
        System.out.println("Enter project type (1.software/ 2.hardware):");
        int type = scanner.nextInt();
        scanner.nextLine();
        String id = generateProjectId();

        //validate name
        String name;
        while (true) {
            System.out.println("Enter project name:");
            name = scanner.nextLine().trim();
            try {
                ValidationUtils.isValidName(name);
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Enter project description:");
        String description = scanner.nextLine();

        //validate deadline
        String deadline ;
        while (true){
            System.out.println("Enter project deadline(dd/mm/yyyy):");
            deadline = scanner.nextLine();
            try {
                ValidationUtils.isValidDate(deadline);
                break;
            }catch (InvalidInputException e){
                System.out.println("try again with the correct format");
            }
        }

        ProjectCatalog project;
        if (type == 1) {
            project = new SoftwareProject(id, name, description, deadline);
        } else if (type == 2) {
            project = new HardwareProject(id, name, description, deadline);
        } else {
            System.out.println("Invalid project type.");
            return;
        }
        addProject(project);
    }

    /*====================DISPLAY PROJECTS======================*/

    public void displayProjects() throws EmptyProjectException {
        if (projects.isEmpty()) {
            throw new EmptyProjectException("No projects found");
        }
        projects.values().forEach(System.out::println);
    }

    /*=====================UPDATE PROJECT======================*/

    public void updateProjectMenu () {
        try{
        displayProjects();
        }catch(EmptyProjectException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Enter project ID to update:");
        String id = scanner.nextLine();
        ProjectCatalog project = findProjectById(id);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.showProjectUpdateOptions(project);

        System.out.println("Project updated successfully.");
    }

    /*=====================DELETE PROJECT==================*/

    public void deleteProject() {
        System.out.println("Enter project ID to delete:");
        String id = scanner.nextLine();
        if (projects.remove(id) != null) {
            System.out.println("Project deleted.");
        } else {
            System.out.println("Project not found.");
        }
    }
    public void updateProjectName(ProjectCatalog project){
        System.out.println("Enter new project name:");
        String name = scanner.nextLine();
        try {
            ValidationUtils.isValidName(name);
            project.setProjectName(name);
            System.out.println("Project name updated.");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateProjectDescription(ProjectCatalog project) {
        System.out.println("Enter new description:");
        project.setProjectDescription(scanner.nextLine());
        System.out.println("Description updated.");
    }
    public void updateProjectDeadline(ProjectCatalog project) {
        String deadline ;
        while (true){
            System.out.println("Enter new project deadline(dd/mm/yyyy):");
            deadline = scanner.nextLine();
            try {
                ValidationUtils.isValidDate(deadline);
                project.setProjectDeadline(deadline);
                break;
            }catch (InvalidInputException e){
                System.out.println("try again with the correct format");
            }
        }
        System.out.println("Description updated.");
    }
}
