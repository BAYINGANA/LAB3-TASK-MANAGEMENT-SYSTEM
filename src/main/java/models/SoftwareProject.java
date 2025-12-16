package models;

public class SoftwareProject extends ProjectCatalog {

    public SoftwareProject(String projectID, String projectName, String projectDescription, String projectDeadline) {
        super(projectID, projectName, projectDescription, "Software", projectDeadline);
    }
}
