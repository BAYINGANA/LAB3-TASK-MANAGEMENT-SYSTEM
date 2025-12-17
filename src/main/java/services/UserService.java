package services;

import models.adminUser;
import models.regularUser;
import utils.exceptions.InvalidInputException;
import utils.exceptions.UserNotFoundException;
import models.UserCatalog;
import models.UserStatus;

import java.util.*;

import utils.ConsoleMenu;
import utils.ValidationUtils;

public class UserService {
    private static final Map<String, UserCatalog> users = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int userCounter = 0;

    /* ====================USER ID==================*/

    private static String generateUserId() {
        userCounter++;
        String userId = "UR" + String.format("%03d", userCounter);
        System.out.println("Auto-generated User ID: " + userId);
        return userId;
    }

    /*==================CREATE USER==================*/

    public void createUser() throws InvalidInputException {
        String id = generateUserId();

        //Name validation
        String name;
        while (true) {
            System.out.println("Enter user name:");
            name = scanner.nextLine();
            try{
                ValidationUtils.isValidName(name);
                break;
            }catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }

        //Password validation
        String password;
        while(true) {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            try{
                ValidationUtils.isNotEmpty(password);
                ValidationUtils.isValidPassword(password);
                break;
            }catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }
        //Email validation
        String email;
        while (true) {
            System.out.println("Enter user email:");
            email = scanner.nextLine();
            if (ValidationUtils.isValidEmail(email)){
                break;
            }else {
                System.out.println("Invalid email. Please include an '@' and a domain (e.g. user@domain.com).");
            }
        }

        //User type
        while (true) {
            System.out.println("Select user type (1=admin, 2=regular):");
            String input = scanner.nextLine().trim();

            try {
                int type = Integer.parseInt(input);
                UserCatalog newUser;

                if (type == 1) {
                    newUser = new adminUser(id, name, password, email);
                    users.put(id,newUser);
                    System.out.println("User created successfully! -> " + newUser);
                    break;
                } else if (type == 2) {
                    newUser = new regularUser(id, name, password, email);
                    users.put(id,newUser);
                    System.out.println("User created successfully! -> " + newUser);
                    break;
                } else {
                    System.out.println("!!!Invalid choice!!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }
    }

    /*=================READ USERS================*/

    public List<UserCatalog>getAllUsers(){
        System.out.println("====LIST OF ALL SYSTEM USERS====");
        if (users.isEmpty()){
            System.out.println("NO user found");
        }
        return users.values()
                .stream()
                .toList();
    }

    public UserCatalog findUserById(String id) throws UserNotFoundException {
        return users.values()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    /* ==================USER ACTIVATION================== */

    public void userActivation(){
        System.out.println("Enter user ID to update status:");
        String userId = scanner.nextLine();

        users.values().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .ifPresentOrElse(
                        u -> {
                            u.setStatus(
                                    u.getStatus() == UserStatus.ACTIVE
                                            ? UserStatus.INACTIVE
                                            : UserStatus.ACTIVE
                            );
                            System.out.println("Status updated: " + u.getStatus());
                        },
                        () -> System.out.println("User not found")
                );
    }

    /* ====================UPDATE USER==================== */

    public void updateUser() throws UserNotFoundException{
        getAllUsers();
        System.out.println("Enter user ID to update:");
        String userId =scanner.nextLine();
        UserCatalog user = findUserById(userId);
        if (user == null){
        throw new UserNotFoundException("User not found");
        }
        ConsoleMenu console = new ConsoleMenu();
        console.showUserUpdateOptions(user);
        System.out.println("User updated.");
    }

    public void updateUserName(UserCatalog user){
        System.out.println("Enter new task name:");
        String name = scanner.nextLine();
        try {
            ValidationUtils.isValidName(name);
            user.setName(name);
            System.out.println("User name updated.");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserEmail(UserCatalog user){
        String email;
        while (true) {
            System.out.println("Enter new user email:");
            email = scanner.nextLine();
            if (ValidationUtils.isNotEmpty(email) && ValidationUtils.isValidEmail(email)){
                user.setEmail(email);
                break;
            }else {
                System.out.println("Invalid email. Please include an '@' and a domain (e.g. user@domain.com).");
            }
        }
    }

    public void updateUserPassword(UserCatalog user){
        String oldPassword;
        String newPassword;
        while(true) {
            System.out.println("Enter old user password:");
            oldPassword = scanner.nextLine();
            if(user.getPassword().equals(oldPassword)) {
                System.out.println("Enter new user password:");
                newPassword = scanner.nextLine();
                try {
                    ValidationUtils.isNotEmpty(newPassword);
                    ValidationUtils.isValidPassword(newPassword);
                    user.setPassword(newPassword);
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Wrong password. Try again.");
            }
        }
    }

    /*=================DELETE  USER===============*/

    public void deleteUser(){
        getAllUsers();
        System.out.println("Enter user ID to delete:");
        String userId = scanner.nextLine();
        if(users.remove(userId) != null){
            System.out.println("User not found");
        }
    }

    /* ==================Search USER================== */

    public void searchUser(){
        System.out.println("Enter name to search:");
        String search = scanner.nextLine().toLowerCase();

        boolean found = users.values()
                .stream()
                .filter(u -> u.getName().toLowerCase().contains(search))
                .peek(u -> System.out.println("FOUND----> " + u))
                .findAny()
                .isPresent();
        if (!found) {
            System.out.println("user not found");
        }
    }

}
