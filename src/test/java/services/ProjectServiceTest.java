package services;

import models.*;
import utils.exceptions.EmptyProjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest {
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
        projectService.getAllProjects().clear();
    }

    @Test
    void testAddAndFindProject() {
        ProjectCatalog p = new SoftwareProject("P100", "Proj", "Desc", "01/01/2026");
        int before = projectService.getAllProjects().size();
        projectService.addProject(p);
        assertEquals(before + 1, projectService.getAllProjects().size());
        ProjectCatalog found = projectService.findProjectById("P100");
        assertNotNull(found);
        assertEquals("P100", found.getProjectID());
    }

    @Test
    void testDisplayProjectsThrowsWhenEmpty() {
        projectService.getAllProjects().clear();
        assertThrows(EmptyProjectException.class, () -> projectService.displayProjects());
    }

    @Test
    void testGetAllProjectsReturnsList() {
        List<ProjectCatalog> list = projectService.getAllProjects();
        assertNotNull(list);
        ProjectCatalog p = new HardwareProject("P101", "HW", "Desc", "02/02/2026");
        list.add(p);
        assertTrue(projectService.getAllProjects().contains(p));
    }
}
