package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator {

    private static final String USER_ID_PATTERN = "^UR\\d{3}$";
    private static final String PROJECT_ID_PATTERN = "^PR\\d{3}$";
    private static final String TASK_ID_PATTERN = "^TS\\d{3}$";


    public static boolean isValidUserId(String userId) {
        return matchesPattern(USER_ID_PATTERN, userId);
    }


    public static boolean isValidProjectId(String projectId) {
        return matchesPattern(PROJECT_ID_PATTERN, projectId);
    }

    public static boolean isValidTaskId(String taskId) {
        return matchesPattern(TASK_ID_PATTERN, taskId);
    }

    private static boolean matchesPattern(String pattern, String input) {
        if (input == null) return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }

}
