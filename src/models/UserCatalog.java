package models;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class UserCatalog {
    protected int id;
    protected String name;
    protected String email;
    protected UserStatus status;

    public UserCatalog(int id, String name, UserStatus status, String email) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return String.format("User[Id=%d, Name=%s, Email=%s, Status=%s]",id, name, email, status);
    }
}

