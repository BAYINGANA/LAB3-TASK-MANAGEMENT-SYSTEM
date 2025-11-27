package services;

import models.UserCatalog;
import models.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private static List<UserCatalog> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public void createUser() {
        System.out.println("Enter user ID:");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter user name:");
        String name = scanner.nextLine();
        System.out.println("Enter user email:");
        String email = scanner.nextLine();
        UserCatalog newUser = new UserCatalog(id, name, UserStatus.ACTIVE, email){};
        users.add(newUser);
        System.out.println("User created successfully!");
    }

    public List<UserCatalog> getAllUsers(){
        System.out.println("LIST OF ALL SYSTEM USERS");
        if (users.isEmpty()){
            System.out.println("NO user found");
        }
       return users;
    }

    public UserCatalog findUserById(int id) {
        for(UserCatalog user : users){
            if(user.getId() == id){
                System.out.println(user);
                return user;
            }
        }
        System.out.println("User not found");
        return null;
    }

    public void userActivation(){
        System.out.println("Enter user ID to update status:");
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        for(UserCatalog user : users){
            if (user.getId() == userId) {
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

    public void updateUser(){
        System.out.println("Enter user ID to update:");
        int userId = scanner.nextInt();
        scanner.nextLine();
        for(UserCatalog user : users){
            if (user.getId() == userId){
                System.out.println("Enter new user name:");
                user.setName(scanner.nextLine());
                System.out.println("Enter new user email:");
                user.setEmail(scanner.nextLine());
                System.out.println("User updated!!");
                return;
            }
        }
        System.out.println("User not found");
    }

    public void deleteUser(){
        System.out.println("Enter user ID to delete:");
        int userId = scanner.nextInt();
        scanner.nextLine();
        users.removeIf(u -> u.getId() ==userId);
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

    public void createAdminUser() {

    }

}
