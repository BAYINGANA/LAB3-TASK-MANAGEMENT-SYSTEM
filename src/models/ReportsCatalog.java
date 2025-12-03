package models;

import services.ReportService;

import java.util.Scanner;

public class ReportsCatalog {
    public Scanner scanner = new Scanner(System.in);
    public int choice ;

    private ProjectCatalog project;
    private ReportService service;

    public ReportsCatalog(ProjectCatalog project){
        this.project = project;
    }

    public String summary() {
        return "Project '" + project.getProjectName() + "' completion" + service.getCompletionPercentage();
    }
}

