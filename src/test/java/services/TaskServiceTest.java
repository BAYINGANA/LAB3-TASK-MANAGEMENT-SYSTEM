package services;

import models.*;
import utils.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
	private TaskService taskService;
	private ProjectService projectService;

	@BeforeEach
	void setUp() {
		taskService = new TaskService();
		projectService = new ProjectService();
		ProjectCatalog testProject = new SoftwareProject("P001", "Test Project", "Test", "25/12/2025");
		projectService.addProject(testProject);
	}

	@Test
	void testGetAllTasksReturnsValidList() {
		List<TaskCatalog> tasks = taskService.getAllTasks();
		assertNotNull(tasks);
		assertTrue(tasks instanceof List);
	}

	@Test
	void testFindTaskById_NotFound() {
		assertThrows(TaskNotFoundException.class, () -> {
			taskService.findTaskById("NONEXISTENT_TASK_ID_12345");
		});
	}

	@Test
	void testFindTaskById_CanFindAfterAdding() throws TaskNotFoundException {
		String uniqueId = "TASK_" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "Test Task", "Description", "P001");
		taskService.getAllTasks().add(task);
		
		TaskCatalog foundTask = taskService.findTaskById(uniqueId);
		assertNotNull(foundTask);
		assertEquals(uniqueId, foundTask.getTaskId());
	}

	@Test
	void testDisplayTasks_NoException() {
		assertDoesNotThrow(() -> taskService.displayTasks());
	}

	@Test
	void testAddAndRetrieveTasks() throws TaskNotFoundException {
		int initialSize = taskService.getAllTasks().size();
		
		String id1 = "TASK_" + System.nanoTime();
		String id2 = "TASK_" + (System.nanoTime() + 1);
		
		TaskCatalog task1 = new TaskCatalog(id1, "Feature 1", "Description 1", "P001");
		TaskCatalog task2 = new TaskCatalog(id2, "Feature 2", "Description 2", "P001");
		
		taskService.getAllTasks().add(task1);
		taskService.getAllTasks().add(task2);
		
		TaskCatalog found1 = taskService.findTaskById(id1);
		TaskCatalog found2 = taskService.findTaskById(id2);
		
		assertNotNull(found1);
		assertNotNull(found2);
		assertEquals("Feature 1", found1.getTaskName());
		assertEquals("Feature 2", found2.getTaskName());
		assertEquals(initialSize + 2, taskService.getAllTasks().size());
	}

	@Test
	void testTaskStatusUpdates() throws TaskNotFoundException {
		String uniqueId = "TASK_" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "Test", "Test", "P001");
		taskService.getAllTasks().add(task);
		
		TaskCatalog found = taskService.findTaskById(uniqueId);
		assertEquals(TaskStatus.NOT_STARTED, found.getTaskStatus());
		
		found.setTaskStatus(TaskStatus.IN_PROGRESS);
		assertEquals(TaskStatus.IN_PROGRESS, found.getTaskStatus());
		
		found.setTaskStatus(TaskStatus.COMPLETED);
		assertEquals(TaskStatus.COMPLETED, found.getTaskStatus());
	}

	@Test
	void testTaskAssignment() throws TaskNotFoundException {
		String uniqueId = "TASK_" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "Test", "Test", "P001");
		task.setAssignedUserId("U001");
		taskService.getAllTasks().add(task);
		
		TaskCatalog found = taskService.findTaskById(uniqueId);
		assertEquals("U001", found.getAssignedUserId());
	}

	@Test
	void testTaskInitialState() {
		String uniqueId = "TASK_" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "New Task", "Description", "P001");
		
		assertEquals(TaskStatus.NOT_STARTED, task.getTaskStatus());
		assertEquals("0", task.getAssignedUserId());
		assertEquals(uniqueId, task.getTaskId());
	}
}
