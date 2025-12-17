package services;

import utils.RegexValidator;
import utils.exceptions.InvalidInputException;
import utils.exceptions.UserNotFoundException;
import models.UserCatalog;
import models.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.ConsoleMenu;
import utils.ValidationUtils;

public class UserService {
    private static final List<UserCatalog> users = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int userCounter = 0;

    private static String generateUserId() {
        userCounter++;
        String userId = "UR" + String.format("%03d", userCounter);
        RegexValidator.isValidUserId(userId);
        System.out.println("Auto-generated User ID: UR" + userId);
        return userId;
    }

    public void createUser() throws InvalidInputException {
        String id = generateUserId();
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

        while (true) {
            System.out.println("Select user type (1=admin, 2=regular):");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter 1 or 2.");
                continue;
            }

            int type;

            try {
                type = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                continue;
            }
            UserCatalog newUser;
            if (type == 1) {
                newUser = new models.adminUser(id, name, password, email);
                users.add(newUser);
                System.out.println("User created successfully! -> " + newUser);
                break;
            } else if (type == 2) {
                newUser = new models.regularUser(id, name, password, email);
                users.add(newUser);
                System.out.println("User created successfully! -> " + newUser);
                break;
            } else {
                System.out.println("!!!Invalid choice!!!");
            }
        }
    }

    public List<UserCatalog> getAllUsers(){
        System.out.println("LIST OF ALL SYSTEM USERS");
        if (users.isEmpty()){
            System.out.println("NO user found");
        }
        for (UserCatalog u : users) {
            System.out.println(u);
        }
       return users;
    }

    public UserCatalog findUserById(String id) throws UserNotFoundException {
        for(UserCatalog user : users){
            if(user.getId().equals(id)){
                System.out.println(user);
                return user;
            }
        }
        throw new UserNotFoundException("User Not Found");
    }

    public void userActivation(){
        System.out.println("Enter user ID to update status:");
        String userId = scanner.nextLine();

        boolean found = false;
        for(UserCatalog user : users){
            if (user.getId().equals(userId)){
                if (user.getStatus() == UserStatus.ACTIVE){
                    user.setStatus(UserStatus.INACTIVE);
                }
                else {
                    user.setStatus(UserStatus.ACTIVE);
                }
                System.out.println("Status updated: " + user.getStatus());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("User not found");
        }
    }

    public void updateUser() throws UserNotFoundException{
        getAllUsers();
        System.out.println("Enter user ID to update:");
        String userId =scanner.nextLine();
        UserCatalog user;
        user = findUserById(userId);
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

    public void deleteUser(){
        getAllUsers();
        System.out.println("Enter user ID to delete:");
        String userId = scanner.nextLine();
        users.removeIf(u -> u.getId().equals(userId));
        System.out.println("User deleted!!");
    }
    public void searchUser(){
        System.out.println("Enter name to search:");
        String search = scanner.nextLine().toLowerCase();

        boolean found = false ;
        for (UserCatalog user : users) {
            if (user.getName().toLowerCase().contains(search)) {
                System.out.println("FOUND ----> " + user);
                found = true;
            }
        }
        if (!found) {
            System.out.println("user not found");
        }
    }

}
