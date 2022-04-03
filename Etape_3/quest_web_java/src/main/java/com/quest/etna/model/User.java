package com.quest.etna.model;

import java.util.Date;

import java.lang.String;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="username", length = 255, nullable = false, unique = true)
    private String username;

    @Column(name="password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role", length = 255)
    private UserRole role = UserRole.ROLE_USER;
    public enum UserRole implements GrantedAuthority {
        ROLE_USER,
        ROLE_ADMIN;

        @Override
        public String getAuthority() {
            return this.toString();
        }
    }

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="updated_date")
    private Date updatedDate;

    public User(){
        super();
    }

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.role = UserRole.ROLE_USER;
        this.creationDate = new Date();
        this.updatedDate = new Date();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public UserRole getRole() {
        return this.role;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Date getUpdateDate() {
        return this.updatedDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return String.format(
            "User[username=%d, password='%s']", this.username, this.password);
    }

    public boolean equals(User user) {
        return (user.getId() != this.id || !user.getUsername().equals(this.username) || !user.getPassword().equals(this.password) || user.getRole() != this.role);
    }

    public int hashCode() {
        long hash = 1;
        hash = hash * 17 + this.id;
        hash = hash * 31 + this.username.hashCode();
        hash = hash * 13 + this.password.hashCode();
        return (int)hash;
    }
}
