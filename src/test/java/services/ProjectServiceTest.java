package services;

import models.*;
import utils.exceptions.EmptyProjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest {

    private ProjectService projectService;
    private Map<String, ProjectCatalog> projectsMap;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception{
        projectService = new ProjectService();

        Field projectsField = ProjectService.class.getDeclaredField("projects");
        projectsField.setAccessible(true);
        projectsMap = (Map<String, ProjectCatalog>) projectsField.get(null);
        projectsMap.clear();

        Field counterField = ProjectService.class.getDeclaredField("projectCounter");
        counterField.setAccessible(true);
        counterField.setInt(null, 0);
    }

    @Test
    void testAddAndFindProject() {
        ProjectCatalog p = new SoftwareProject("PR100", "Proj", "Desc", "01/01/2026");
        int before = projectService.getAllProjects().size();
        projectService.addProject(p);
        assertEquals(before + 1, projectService.getAllProjects().size());
        ProjectCatalog found = projectService.findProjectById("PR100");
        assertNotNull(found);
        assertEquals("PR100", found.getProjectID());
    }

    @Test
    void testDisplayProjectsThrowsWhenEmpty() {
        projectsMap.clear();
        assertThrows(EmptyProjectException.class, () -> projectService.displayProjects());
    }

    @Test
    void testGetAllProjectsReturnsList() {
        List<ProjectCatalog> list = projectService.getAllProjects();
        assertNotNull(list);
        ProjectCatalog p = new HardwareProject("PR101", "HW", "Desc", "02/02/2026");
        projectsMap.put(p.getProjectID(), p);
        List<ProjectCatalog> updatedList = projectService.getAllProjects();
        assertTrue(updatedList.contains(p));
    }
}
