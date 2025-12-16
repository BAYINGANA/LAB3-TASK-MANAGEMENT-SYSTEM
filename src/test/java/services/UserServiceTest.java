package services;

import models.*;
import utils.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        userService.getAllUsers().clear();
    }

    @Test
    void testGetAllUsersReturnsList() {
        List<UserCatalog> users = userService.getAllUsers();
        assertNotNull(users);
        UserCatalog u = new adminUser("U900", "Admin", "pass", "admin@x.com");
        users.add(u);
        assertTrue(userService.getAllUsers().contains(u));
    }

    @Test
    void testFindUserByIdThrowsWhenNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findUserById("NO_SUCH_USER"));
    }

    @Test
    void testFindUserByIdReturnsUser() throws UserNotFoundException {
        UserCatalog u = new regularUser("U901", "User", "pass", "user@x.com");
        userService.getAllUsers().add(u);
        UserCatalog found = userService.findUserById("U901");
        assertNotNull(found);
        assertEquals("U901", found.getId());
    }

    @Test
    void testUpdateUserPasswordAndEmail() {
        UserCatalog u = new regularUser("U902", "User2", "oldpass", "u2@x.com");
        userService.getAllUsers().add(u);
        u.setPassword("newpass");
        u.setEmail("new@x.com");
        assertEquals("newpass", u.getPassword());
    }
}
