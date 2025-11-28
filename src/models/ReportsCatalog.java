package models;

import java.util.Scanner;

public class ReportsCatalog {
    public Scanner scanner = new Scanner(System.in);
    public int choice ;

    private ProjectCatalog project;

    public ReportsCatalog(ProjectCatalog project){
        this.project = project;
    }

    public String summary() {
        return "Poject '" + project.getProjectName() + "' completion" + project.getCompletionPercentage();
    }
}

