package services;

import models.HardwareProject;
import models.ProjectCatalog;
import models.SoftwareProject;
import utils.ValidationUtils;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectService {
    private final List<ProjectCatalog> projects = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static int projectCounter = 0;

    private static int generateProjectId() {
        projectCounter++;
        System.out.println("Auto-generated Project ID: PR" + String.format("%03d", projectCounter));
        return projectCounter;
    }

    public void addProject(ProjectCatalog project) {
        projects.add(project);
        System.out.println("Project added successfully.");
    }

    public List<ProjectCatalog> getAllProjects() {
        return projects;
    }

    public ProjectCatalog findProjectById(int id) {
        return projects.stream().filter(p -> p.getProjectID() == id).findFirst().orElse(null);
    }

    public void createProjectMenu() {
        System.out.println("Enter project type (software/hardware):");
        String type = scanner.nextLine().toLowerCase();
        int id = generateProjectId();
        System.out.println("Enter project name:");
        String name = scanner.nextLine();
        try {
            ValidationUtils.isValidName(name);
        }catch (InvalidInputException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Enter project description:");
        String description = scanner.nextLine();
        System.out.println("Enter project deadline:");
        String deadline = scanner.nextLine();

        ProjectCatalog project;
        if ("software".equals(type)) {
            project = new SoftwareProject(id, name, description, deadline);
        } else if ("hardware".equals(type)) {
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

    public void updateProjectMenu() {
        System.out.println("Enter project ID to update:");
        int id = Integer.parseInt(scanner.nextLine());
        ProjectCatalog project = findProjectById(id);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        System.out.println("Enter new project name:");
        project.setProjectName(scanner.nextLine());
        System.out.println("Enter new project description:");
        project.setProjectDescription(scanner.nextLine());
        System.out.println("Enter new project deadline:");
        project.setProjectDeadline(scanner.nextLine());

        System.out.println("Project updated successfully.");
    }

    public void deleteProject() {
        System.out.println("Enter project ID to delete:");
        int id = Integer.parseInt(scanner.nextLine());
        ProjectCatalog project = findProjectById(id);
        if (project != null && projects.remove(project)) {
            System.out.println("Project deleted.");
        } else {
            System.out.println("Project not found.");
        }
    }
}

