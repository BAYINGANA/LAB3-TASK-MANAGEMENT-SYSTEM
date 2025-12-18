package utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import models.*;

public class FileUtils {
    private static final Path FILE = Paths.get("src", "data", "projects_data.json");

    public static void saveAll(List<UserCatalog> users, List<ProjectCatalog> projects, List<TaskCatalog> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        // Users
        sb.append("  \"users\": [\n");
        for (int i = 0; i < users.size(); i++) {
            UserCatalog u = users.get(i);
            sb.append(String.format("    {\"id\": \"%s\", \"name\": \"%s\", \"password\": \"%s\", \"status\": \"%s\", \"type\": \"%s\", \"email\": \"%s\"}%s\n",
                u.getId(), u.getName(), u.getPassword(), u.getStatus().name(), u.getClass().getSimpleName(), u.getEmail(),
                (i < users.size() - 1) ? "," : ""));
        }
        sb.append("  ],\n");
        // Projects
        sb.append("  \"projects\": [\n");
        for (int i = 0; i < projects.size(); i++) {
            ProjectCatalog p = projects.get(i);
            sb.append(String.format("    {\"id\": \"%s\", \"name\": \"%s\", \"description\": \"%s\", \"category\": \"%s\", \"deadline\": \"%s\", \"type\": \"%s\"}%s\n",
                p.getProjectID(), p.getProjectName(), p.getProjectDescription(), p.getProjectCategory(), p.getProjectDeadline(), p.getClass().getSimpleName(),
                (i < projects.size() - 1) ? "," : ""));
        }
        sb.append("  ],\n");
        // Tasks
        sb.append("  \"tasks\": [\n");
        for (int i = 0; i < tasks.size(); i++) {
            TaskCatalog t = tasks.get(i);
            sb.append(String.format("    {\"id\": \"%s\", \"name\": \"%s\", \"description\": \"%s\", \"status\": \"%s\", \"assignedUser\": \"%s\", \"projectId\": \"%s\"}%s\n",
                t.getTaskId(), t.getTaskName(), t.getTaskDescription(), t.getTaskStatus().name(), t.getAssignedUserId(), t.getProjectID(),
                (i < tasks.size() - 1) ? "," : ""));
        }
        sb.append("  ]\n");
        sb.append("}\n");
        try {
            Files.writeString(FILE, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("[INFO] Data saved to " + FILE.toString());
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save data: " + e.getMessage());
        }
    }

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
                if (line.equals("[USERS]")) { section = "USERS"; continue; }
                if (line.equals("[PROJECTS]")) { section = "PROJECTS"; continue; }
                if (line.equals("[TASKS]")) { section = "TASKS"; continue; }
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
                                TaskCatalog task = new TaskCatalog(parts[1], parts[2], parts[3], parts[6]);
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
            System.out.println("[INFO] Data loaded from " + FILE.toString());
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
