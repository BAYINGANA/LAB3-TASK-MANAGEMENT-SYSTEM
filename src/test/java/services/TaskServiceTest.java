package services;

import models.*;
import utils.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
	private TaskService taskService;
    private Map<String, TaskCatalog> tasksMap;

	@BeforeEach
    @SuppressWarnings("unchecked")
	void setUp() throws Exception{
		taskService = new TaskService();
        ProjectService projectService = new ProjectService();
		ProjectCatalog testProject = new SoftwareProject("PR001", "Test Project", "Test", "25/12/2025");
		projectService.addProject(testProject);

        Field tasksField = TaskService.class.getDeclaredField("tasks");
        tasksField.setAccessible(true);
        tasksMap = (Map<String, TaskCatalog>) tasksField.get(null);
        tasksMap.clear();

        Field counterField = TaskService.class.getDeclaredField("taskCounter");
        counterField.setAccessible(true);
        counterField.setInt(null, 0);
	}

	@Test
	void testGetAllTasksReturnsValidList() {
		List<TaskCatalog> tasks = taskService.getAllTasks();
		assertNotNull(tasks);
        assertInstanceOf(List.class, tasks);
	}

	@Test
	void testFindTaskById_NotFound() {
		assertThrows(TaskNotFoundException.class, () -> taskService.findTaskById("NONEXISTENT_TASK_ID_12345"));
	}

	@Test
	void testFindTaskById_CanFindAfterAdding() throws TaskNotFoundException {
		String uniqueId = "TS001" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "Test Task", "Description", "PR001");
		tasksMap.put(uniqueId, task);
		
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
		
		String id1 = "TS001" + System.nanoTime();
		String id2 = "TS002" + (System.nanoTime() + 1);
		
		TaskCatalog task1 = new TaskCatalog(id1, "Feature 1", "Description 1", "PR001");
		TaskCatalog task2 = new TaskCatalog(id2, "Feature 2", "Description 2", "PR001");
		
		tasksMap.put(task1.getTaskId(),task1);
		tasksMap.put(task2.getTaskId(),task2);
		
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
		String uniqueId = "TS003" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "Test", "Test", "P001");
		tasksMap.put(task.getTaskId(),task);
		
		TaskCatalog found = taskService.findTaskById(uniqueId);
		assertEquals(TaskStatus.NOT_STARTED, found.getTaskStatus());
		
		found.setTaskStatus(TaskStatus.IN_PROGRESS);
		assertEquals(TaskStatus.IN_PROGRESS, found.getTaskStatus());
		
		found.setTaskStatus(TaskStatus.COMPLETED);
		assertEquals(TaskStatus.COMPLETED, found.getTaskStatus());
	}

	@Test
	void testTaskAssignment() throws TaskNotFoundException {
		String uniqueId = "TS004" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "Test", "Test", "P001");
		task.setAssignedUserId("UR001");
		tasksMap.put(task.getTaskId(),task);
		
		TaskCatalog found = taskService.findTaskById(uniqueId);
		assertEquals("UR001", found.getAssignedUserId());
	}

	@Test
	void testTaskInitialState() {
		String uniqueId = "TS005" + System.nanoTime();
		TaskCatalog task = new TaskCatalog(uniqueId, "New Task", "Description", "PR001");
		
		assertEquals(TaskStatus.NOT_STARTED, task.getTaskStatus());
		assertEquals("0", task.getAssignedUserId());
		assertEquals(uniqueId, task.getTaskId());
	}
}
