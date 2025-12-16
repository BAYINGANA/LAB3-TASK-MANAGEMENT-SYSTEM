package models;

public class regularUser extends UserCatalog {

    public regularUser(String id, String name, String password , String email) {
        super(id, name, password , UserStatus.ACTIVE, email);
    }

}
