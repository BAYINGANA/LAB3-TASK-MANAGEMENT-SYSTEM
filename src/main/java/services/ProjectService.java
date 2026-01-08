package services;

import models.HardwareProject;
import models.ProjectCatalog;
import models.SoftwareProject;
import utils.RegexValidator;
import utils.ValidationUtils;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectService {
    private static final List<ProjectCatalog> projects = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int projectCounter = 0;

    public ProjectService(List<ProjectCatalog> loadedProjects) {
        projects.clear();
        if (loadedProjects != null) projects.addAll(loadedProjects);
    }
    public ProjectService() { this(new ArrayList<>()); }

    private static String generateProjectId() {
        projectCounter++;
        String projectId = "PR" + String.format("%03d", projectCounter);
        RegexValidator.isValidProjectId(projectId);
        System.out.println("Auto-generated Project ID: " + projectId);
        return projectId;
    }

    public void addProject(ProjectCatalog project) {
        projects.add(project);
        System.out.println("Project added successfully.");
    }

    public List<ProjectCatalog> getAllProjects() {
        return projects;
    }

    public ProjectCatalog findProjectById(String id) {
        return projects.stream().filter(p -> p.getProjectID().equals(id)).findFirst().orElse(null);
    }

    public void createProject() {
        System.out.println("Enter project type (1.software/ 2.hardware):");
        int type = scanner.nextInt();
        scanner.nextLine();
        String id = generateProjectId();
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

    public void displayProjects() throws EmptyProjectException {
        if (projects.isEmpty()) {
            throw new EmptyProjectException("No projects found");
        }
        for (ProjectCatalog project : projects) {
            System.out.println(project);
        }
    }

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
        ConsoleMenu consoleMenu = new ConsoleMenu(this, userService, taskService);

        System.out.println("Project updated successfully.");
    }

    public void deleteProject() {
        System.out.println("Enter project ID to delete:");
        String id = scanner.nextLine();
        ProjectCatalog project = findProjectById(id);
        if (project != null && projects.remove(project)) {
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
