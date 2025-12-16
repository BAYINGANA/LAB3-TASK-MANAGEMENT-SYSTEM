package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserCatalogTest {

    static class TestUser extends UserCatalog {
        public TestUser(String id, String name, String password, UserStatus status, String email) {
            super(id, name, password, status, email);
        }
    }

    private UserCatalog adminUser;
    private UserCatalog regularUser;
    private UserCatalog testUser;

    @BeforeEach
    void setUp() {
        adminUser = new adminUser("U001", "Admin User", "adminPass123", "admin@test.com");
        regularUser = new regularUser("U002", "Regular User", "userPass123", "user@test.com");
        testUser = new TestUser("U003", "Test User", "testPass123", UserStatus.ACTIVE, "test@test.com");
    }

    @Test
    void testAdminUserCreation() {
        assertNotNull(adminUser);
        assertEquals("U001", adminUser.getId());
        assertEquals("Admin User", adminUser.getName());
        assertEquals("adminPass123", adminUser.getPassword());
        assertEquals(UserStatus.ACTIVE, adminUser.getStatus());
    }

    @Test
    void testRegularUserCreation() {
        assertNotNull(regularUser);
        assertEquals("U002", regularUser.getId());
        assertEquals("Regular User", regularUser.getName());
        assertEquals(UserStatus.ACTIVE, regularUser.getStatus());
    }

    @Test
    void testUserInitialStatus() {
        assertEquals(UserStatus.ACTIVE, adminUser.getStatus());
        assertEquals(UserStatus.ACTIVE, regularUser.getStatus());
    }

    @Test
    void testSetUserName() {
        adminUser.setName("Updated Admin");
        assertEquals("Updated Admin", adminUser.getName());
    }

    @Test
    void testSetUserPassword() {
        adminUser.setPassword("newPassword456");
        assertEquals("newPassword456", adminUser.getPassword());
    }

    @Test
    void testSetUserEmail() {
        adminUser.setEmail("newemail@test.com");
        assertDoesNotThrow(() -> adminUser.setEmail("another@test.com"));
    }

    @Test
    void testSetUserStatus() {
        adminUser.setStatus(UserStatus.INACTIVE);
        assertEquals(UserStatus.INACTIVE, adminUser.getStatus());
        adminUser.setStatus(UserStatus.ACTIVE);
        assertEquals(UserStatus.ACTIVE, adminUser.getStatus());
    }

    @Test
    void testUserToString() {
        String toString = adminUser.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("U001"));
        assertTrue(toString.contains("Admin User"));
        assertTrue(toString.contains("ACTIVE"));
    }

    @Test
    void testUserIdImmutable() {
        assertEquals("U001", adminUser.getId());
    }

    @Test
    void testConcreteUserImplementations() {
        assertTrue(adminUser instanceof adminUser);
        assertTrue(regularUser instanceof regularUser);
        assertTrue(testUser instanceof TestUser);
    }

    @Test
    void testMultipleUsersWithDifferentStatuses() {
        adminUser.setStatus(UserStatus.INACTIVE);
        regularUser.setStatus(UserStatus.ACTIVE);
        testUser.setStatus(UserStatus.INACTIVE);
        
        assertEquals(UserStatus.INACTIVE, adminUser.getStatus());
        assertEquals(UserStatus.ACTIVE, regularUser.getStatus());
        assertEquals(UserStatus.INACTIVE, testUser.getStatus());
    }
}
