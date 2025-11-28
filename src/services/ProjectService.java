package services;

import models.HardwareProject;
import models.ProjectCatalog;
import models.SoftwareProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectService {
    private final List<ProjectCatalog> projects = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

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
        System.out.println("Enter project ID:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter project name:");
        String name = scanner.nextLine();
        System.out.println("Enter project description:");
        String description = scanner.nextLine();
        System.out.println("Enter project status:");
        String status = scanner.nextLine();
        System.out.println("Enter project deadline:");
        String deadline = scanner.nextLine();

        ProjectCatalog project;
        if ("software".equals(type)) {
            project = new SoftwareProject(id, name, description, status, deadline);
        } else if ("hardware".equals(type)) {
            project = new HardwareProject(id, name, description, status, deadline);
        } else {
            System.out.println("Invalid project type.");
            return;
        }
        addProject(project);
    }

    public void displayProjects() {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
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

