package com.quest.etna.model;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private UserRole role = UserRole.ROLE_USER;
    public enum UserRole {
        ROLE_USER,
        ROLE_ADMIN;
    }

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="update_date")
    private Date updatedDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.ROLE_USER;
        this.creationDate = new Date();
        this.updatedDate = new Date();
    }

    public Long getId() {
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

    public void setId(Long id) {
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

    @Override
    public boolean equals(User user) {
        return (!user.getId().equals(this.id) || !user.getUsername().equals(this.username) || !user.getPassword().equals(this.password) || user.getRole() != this.role);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + this.id;
        hash = hash * 31 + this.username.hashCode();
        hash = hash * 13 + this.password.hashCode();
        return hash;
    }
}
