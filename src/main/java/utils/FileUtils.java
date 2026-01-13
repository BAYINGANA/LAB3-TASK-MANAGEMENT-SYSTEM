package utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import models.*;

public class FileUtils {

    private static final Path FILE = Paths.get("src", "data", "projects_data.json");

    // ===================== SAVE =====================
    public static void saveAll(List<UserCatalog> users,
                               List<ProjectCatalog> projects,
                               List<TaskCatalog> tasks) {

        List<String> lines = new ArrayList<>();

        /* ================= USERS ================= */
        lines.add("[USERS]");
        for (UserCatalog u : users) {
            lines.add(String.join("|",
                    "U",
                    u.getId(),
                    u.getName(),
                    u.getPassword(),
                    u.getStatus().name(),
                    u.getClass().getSimpleName(),
                    u.getEmail()
            ));
        }

        /* ================= PROJECTS ================= */
        lines.add("[PROJECTS]");
        for (ProjectCatalog p : projects) {
            lines.add(String.join("|",
                    "P",
                    p.getProjectID(),
                    p.getProjectName(),
                    p.getProjectDescription(),
                    p.getProjectCategory(),
                    p.getProjectDeadline(),
                    p.getClass().getSimpleName()
            ));
        }

        /* ================= TASKS ================= */
        lines.add("[TASKS]");
        for (TaskCatalog t : tasks) {
            lines.add(String.join("|",
                    "T",
                    t.getTaskId(),
                    t.getTaskName(),
                    t.getTaskDescription(),
                    t.getTaskStatus().name(),
                    t.getAssignedUserId(),
                    t.getProjectID()
            ));
        }

        try {
            Files.write(FILE, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("[INFO] Data saved to " + FILE.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save data: " + e.getMessage());
        }
    }

    // ===================== LOAD =====================
    public static Map<String, List<?>> loadAll() {

        List<UserCatalog> users = new ArrayList<>();
        List<ProjectCatalog> projects = new ArrayList<>();
        List<TaskCatalog> tasks = new ArrayList<>();

        if (!Files.exists(FILE)) {
            System.out.println("[INFO] No persistence file found. Starting fresh.");
            Map<String, List<?>> result = new HashMap<>();
            result.put("users", users);
            result.put("projects", projects);
            result.put("tasks", tasks);
            return result;
        }

        try (Stream<String> stream = Files.lines(FILE)) {

            String section = "";

            for (String line : (Iterable<String>) stream::iterator) {

                if (line.equals("[USERS]")) {
                    section = "USERS";
                    continue;
                }
                if (line.equals("[PROJECTS]")) {
                    section = "PROJECTS";
                    continue;
                }
                if (line.equals("[TASKS]")) {
                    section = "TASKS";
                    continue;
                }

                if (line.isBlank() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");

                try {
                    switch (section) {

                        case "USERS" -> {
                            if (parts.length >= 7) {
                                String type = parts[5];
                                UserCatalog user = type.equals("adminUser")
                                        ? new adminUser(parts[1], parts[2], parts[3], parts[6])
                                        : new regularUser(parts[1], parts[2], parts[3], parts[6]);
                                user.setStatus(UserStatus.valueOf(parts[4]));
                                users.add(user);
                            }
                        }

                        case "PROJECTS" -> {
                            if (parts.length >= 7) {
                                String type = parts[6];
                                ProjectCatalog project = type.equals("SoftwareProject")
                                        ? new SoftwareProject(parts[1], parts[2], parts[3], parts[5])
                                        : new HardwareProject(parts[1], parts[2], parts[3], parts[5]);
                                projects.add(project);
                            }
                        }

                        case "TASKS" -> {
                            if (parts.length >= 7) {
                                TaskCatalog task = new TaskCatalog(
                                        parts[1],
                                        parts[2],
                                        parts[3],
                                        parts[6]
                                );
                                task.setTaskStatus(TaskStatus.valueOf(parts[4]));
                                task.setAssignedUserId(parts[5]);
                                tasks.add(task);
                            }
                        }
                    }

                } catch (Exception e) {
                    System.err.println("[WARN] Malformed line skipped: " + line);
                }
            }

            System.out.println("[INFO] Data loaded from " + FILE.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load data: " + e.getMessage());
        }

        Map<String, List<?>> result = new HashMap<>();
        result.put("users", users);
        result.put("projects", projects);
        result.put("tasks", tasks);
        return result;
    }
}
