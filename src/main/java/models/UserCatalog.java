package models;


public abstract class UserCatalog {
    protected String id;
    protected String name;
    protected String email;
    protected String password;
    protected UserStatus status;

    public UserCatalog(String id, String name, String password , UserStatus status, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.status = status;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return String.format("User[Id=%s, Name=%s, Email=%s, Status=%s]",id, name, email, status);
    }
}
