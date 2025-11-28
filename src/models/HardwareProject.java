package models;

import java.util.Scanner;

public class HardwareProject extends ProjectCatalog {
    Scanner scanner = new Scanner(System.in);
    public HardwareProject(int projectID, String projectName, String projectDescription, String projectDeadline, String deadline) {
        super(projectID, projectName, projectDescription, "Hardware", projectDeadline);

    }

    @Override
    public void createProject() {
        System.out.println("Enter project ID:");
        this.projectID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter project name:");
        this.projectName = scanner.nextLine();
        System.out.println("Enter project description:");
        this.projectDescription = scanner.nextLine();
        System.out.println("Enter project deadline:");
        this.projectDeadline = scanner.nextLine();
    }

    @Override
    public void displayAllProjects() {

    }

    @Override
    public void displayProject() {

    }

    @Override
    public void updateProject() {

    }

    @Override
    public void filterProject() {

    }

    @Override
    public void deleteProject() {

    }

    @Override
    public double getCompletionPercentage() {
    return super.getCompletionPercentage();
    }
}

