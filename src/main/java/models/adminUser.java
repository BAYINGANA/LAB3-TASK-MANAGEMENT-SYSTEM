package models;

public class adminUser extends UserCatalog {

    public adminUser(String id, String name, String password ,String email) {
        super(id, name, password , UserStatus.ACTIVE, email);
    }
}
