

import utils.ConsoleMenu;
import utils.FileUtils;
import services.ProjectService;
import services.UserService;
import services.TaskService;
import models.UserCatalog;
import models.ProjectCatalog;
import models.TaskCatalog;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, List<?>> loaded = FileUtils.loadAll();
        UserService userService = new UserService((List<UserCatalog>) loaded.get("users"));
        ProjectService projectService = new ProjectService((List<ProjectCatalog>) loaded.get("projects"));
        TaskService taskService = new TaskService((List<TaskCatalog>) loaded.get("tasks"));
        ConsoleMenu menu = new ConsoleMenu(projectService, userService, taskService);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileUtils.saveAll(userService.getAllUsers(), projectService.getAllProjects(), taskService.getAllTasks());
        }));
        menu.MainMenu();
    }
}
