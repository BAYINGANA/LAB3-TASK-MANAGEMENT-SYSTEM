package models;

public class HardwareProject extends ProjectCatalog {
    public HardwareProject(String projectID, String projectName, String projectDescription, String projectDeadline) {
        super(projectID, projectName, projectDescription, "Hardware", projectDeadline);
    }
}
