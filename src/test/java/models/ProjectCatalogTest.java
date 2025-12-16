package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectCatalogTest {
	private ProjectCatalog softwareProject;
	private ProjectCatalog hardwareProject;

	@BeforeEach
	void setUp() {
		softwareProject = new SoftwareProject("P001", "Java App", "Build a Java application", "25/12/2025");
		hardwareProject = new HardwareProject("P002", "Circuit Board", "Design a circuit", "31/12/2025");
	}

	@Test
	void testSoftwareProjectCreation() {
		assertNotNull(softwareProject);
		assertEquals("P001", softwareProject.getProjectID());
		assertEquals("Java App", softwareProject.getProjectName());
	}

	@Test
	void testHardwareProjectCreation() {
		assertNotNull(hardwareProject);
		assertEquals("P002", hardwareProject.getProjectID());
		assertEquals("Circuit Board", hardwareProject.getProjectName());
	}

	@Test
	void testSetProjectName() {
		softwareProject.setProjectName("Updated Java App");
		assertEquals("Updated Java App", softwareProject.getProjectName());
	}

	@Test
	void testSetProjectDescription() {
		softwareProject.setProjectDescription("Updated description");
		assertDoesNotThrow(() -> softwareProject.setProjectDescription("Another update"));
	}

	@Test
	void testSetProjectDeadline() {
		softwareProject.setProjectDeadline("01/01/2026");
		assertDoesNotThrow(() -> softwareProject.setProjectDeadline("02/01/2026"));
	}

	@Test
	void testAddTaskToProject() {
		TaskCatalog task = new TaskCatalog("T001", "Feature 1", "Implement feature", "P001");
		softwareProject.addTask(task);
		List<TaskCatalog> tasks = softwareProject.getTasks();
		assertEquals(1, tasks.size());
		assertTrue(tasks.contains(task));
	}

	@Test
	void testAddMultipleTasksToProject() {
		TaskCatalog task1 = new TaskCatalog("T001", "Feature 1", "Feature 1", "P001");
		TaskCatalog task2 = new TaskCatalog("T002", "Feature 2", "Feature 2", "P001");
		TaskCatalog task3 = new TaskCatalog("T003", "Feature 3", "Feature 3", "P001");
        
		softwareProject.addTask(task1);
		softwareProject.addTask(task2);
		softwareProject.addTask(task3);
        
		List<TaskCatalog> tasks = softwareProject.getTasks();
		assertEquals(3, tasks.size());
	}

	@Test
	void testGetTasksEmptyList() {
		List<TaskCatalog> tasks = softwareProject.getTasks();
		assertNotNull(tasks);
		assertEquals(0, tasks.size());
	}

	@Test
	void testProjectToString() {
		String toString = softwareProject.toString();
		assertNotNull(toString);
		assertTrue(toString.contains("P001"));
		assertTrue(toString.contains("Java App"));
	}

	@Test
	void testProjectIDImmutable() {
		assertEquals("P001", softwareProject.getProjectID());
	}
}
