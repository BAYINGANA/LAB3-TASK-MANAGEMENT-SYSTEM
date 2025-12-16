package interfaces;

import models.*;
import utils.exceptions.EmptyProjectException;

import java.util.List;

public interface CompletablesReport {
    void projectCompletionSummary(List<ProjectCatalog> projects) throws EmptyProjectException;
    void taskCompletionSummary(List<TaskCatalog> tasks) throws EmptyProjectException;
    void userWorkloadSummary(List<UserCatalog> users, List<TaskCatalog> tasks);
}
