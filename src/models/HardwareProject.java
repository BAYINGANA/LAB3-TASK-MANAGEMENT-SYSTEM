package models;

import java.util.Scanner;

public class HardwareProject extends ProjectCatalog {
    Scanner scanner = new Scanner(System.in);
    public HardwareProject(int projectID, String projectName, String projectDescription, String projectDeadline) {
        super(projectID, projectName, projectDescription, "Hardware", projectDeadline);

    }

    @Override
    public void createProject() {
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

