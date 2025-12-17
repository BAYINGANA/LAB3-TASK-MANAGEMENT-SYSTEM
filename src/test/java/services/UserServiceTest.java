package services;

import models.*;
import utils.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    private Map<String, UserCatalog> usersMap;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        userService = new UserService();

        Field usersField = UserService.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersMap = (Map<String, UserCatalog>) usersField.get(null);
        usersMap.clear();
    }

    @Test
    void testGetAllUsersReturnsEmptyListInitially() {
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void testFindUserByIdThrowsWhenNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findUserById("NO_SUCH_USER"));
    }

    @Test
    void testFindUserByIdReturnsUser() throws UserNotFoundException {
        UserCatalog user = new regularUser("UR001", "User", "pass", "user@x.com");
        usersMap.put(user.getId(), user);
        UserCatalog found = userService.findUserById("UR001");
        assertNotNull(found);
        assertEquals("UR001", found.getId());
    }

    @Test
    void testUpdateUserPasswordAndEmail() {
        UserCatalog u = new regularUser("UR002", "User2", "oldpass", "u2@x.com");
        usersMap.put(u.getId(),u);
        u.setPassword("newpass");
        u.setEmail("new@x.com");
        assertEquals("newpass", u.getPassword());
        assertEquals("new@x.com", u.getEmail());
    }
}
