package com.buybricks.app.model;

import java.util.HashMap;

import com.buybricks.app.utils.UserRole;

public class UserDetails {
    private String username;
    private UserRole role;
    private int id;

    public UserDetails(int id, String username, UserRole role) {
        this.username = username;
        this.role = role;
        this.id = id;
    }

    public UserDetails(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.id = user.getId();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public HashMap<String, Object> buildJson() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("id", this.id);
        result.put("username", this.username);
        result.put("role", this.role.toString());
        return result;
    }

    public String toString() {
        return "Username : " + this.username + "; User role : " + this.role.toString(); 
    }

}
