package models;

import java.util.Scanner;

public class regularUser extends UserCatalog {

    public regularUser(int id, String name, String email) {
        super(id, name, UserStatus.ACTIVE, email);
    }

}
