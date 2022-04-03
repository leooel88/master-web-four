package com.quest.etna.model;

import com.quest.etna.model.User.UserRole;

public class UserDetails {
    private String username;
    private UserRole role;

    public UserDetails(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public UserDetails(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    public String getUsername() {
        return this.username;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String toString() {
        return "Username : " + this.username + "; User role : " + this.role.toString(); 
    }

}
