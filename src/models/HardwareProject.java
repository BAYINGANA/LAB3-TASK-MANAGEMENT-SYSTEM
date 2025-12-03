package models;

public class HardwareProject extends ProjectCatalog {
    public HardwareProject(int projectID, String projectName, String projectDescription, String projectDeadline) {
        super(projectID, projectName, projectDescription, "Hardware", projectDeadline);
    }
}

