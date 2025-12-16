package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskCatalogTest {
    private TaskCatalog task;

    @BeforeEach
    void setUp() {
        task = new TaskCatalog("T001", "Test Task", "Test Description", "P001");
    }

    @Test
    void testTaskCreation() {
        assertNotNull(task);
        assertEquals("T001", task.getTaskId());
        assertEquals(TaskStatus.NOT_STARTED, task.getTaskStatus());
        assertEquals("0", task.getAssignedUserId());
    }

    @Test
    void testTaskInitialStatus() {
        assertEquals(TaskStatus.NOT_STARTED, task.getTaskStatus());
    }

    @Test
    void testTaskInitialAssignedUserId() {
        assertEquals("0", task.getAssignedUserId());
    }

    @Test
    void testSetTaskName() {
        task.setTaskName("Updated Task");
        assertEquals("Updated Task", task.getTaskName());
    }

    @Test
    void testSetTaskDescription() {
        task.setTaskDescription("Updated Description");
        assertEquals("Updated Description", task.getTaskDescription());
    }

    @Test
    void testSetTaskStatus() {
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getTaskStatus());
        task.setTaskStatus(TaskStatus.COMPLETED);
        assertEquals(TaskStatus.COMPLETED, task.getTaskStatus());
    }

    @Test
    void testSetAssignedUserId() {
        task.setAssignedUserId("U123");
        assertEquals("U123", task.getAssignedUserId());
    }

    @Test
    void testSetProjectID() {
        task.setProjectID("P002");
        assertDoesNotThrow(() -> task.setProjectID("P003"));
    }

    @Test
    void testTaskToString() {
        String toString = task.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("T001"));
        assertTrue(toString.contains("Test Task"));
        assertTrue(toString.contains("NOT_STARTED"));
    }

    @Test
    void testTaskImmutableId() {
        String taskId = task.getTaskId();
        assertEquals("T001", taskId);
    }
}
