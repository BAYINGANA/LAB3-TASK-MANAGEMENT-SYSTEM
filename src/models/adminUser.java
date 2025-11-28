package models;

import java.util.Scanner;

public class adminUser extends UserCatalog {

    public adminUser(int id, String name, String email) {
        super(id, name, UserStatus.ACTIVE, email);
    }
}

