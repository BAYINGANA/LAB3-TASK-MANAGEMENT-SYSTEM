package services;

import models.*;
import utils.exceptions.EmptyProjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService();
    }

    @Test
    void testGetCompletionPercentage_NoTasksReturnsZero() {
        ProjectCatalog p = new SoftwareProject("PRX", "Empty", "No tasks", "01/01/2026");
        double percent = reportService.getCompletionPercentage(p);
        assertEquals(0.0, percent);
    }

    @Test
    void testGetCompletionPercentage_ComputedCorrectly() {
        ProjectCatalog p = new SoftwareProject("PR1", "WithTasks", "Some tasks", "01/01/2026");
        TaskCatalog t1 = new TaskCatalog("T1", "A", "d", "PR1");
        TaskCatalog t2 = new TaskCatalog("T2", "B", "d", "PR1");
        TaskCatalog t3 = new TaskCatalog("T3", "C", "d", "PR1");
        t1.setTaskStatus(TaskStatus.COMPLETED);
        t2.setTaskStatus(TaskStatus.COMPLETED);

        p.addTask(t1);
        p.addTask(t2);
        p.addTask(t3);

        double percent = reportService.getCompletionPercentage(p);
        assertEquals((2.0/3.0) * 100.0, percent, 0.0001);
    }

    @Test
    void testProjectCompletionSummaryThrowsWhenEmpty() {
        List<ProjectCatalog> empty = new ArrayList<>();
        assertThrows(EmptyProjectException.class, () -> reportService.projectCompletionSummary(empty));
    }

    @Test
    void testTaskCompletionSummaryThrowsWhenEmpty() {
        List<TaskCatalog> empty = new ArrayList<>();
        assertThrows(EmptyProjectException.class, () -> reportService.taskCompletionSummary(empty));
    }

    @Test
    void testUserWorkloadSummary_NoUsersReturnsNothing() {
        List<UserCatalog> users = new ArrayList<>();
        List<TaskCatalog> tasks = new ArrayList<>();

        assertDoesNotThrow(() -> reportService.userWorkloadSummary(users, tasks));
    }
}
