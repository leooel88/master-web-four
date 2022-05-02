package com.buybricks.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.buybricks.app.utils.UserRole;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails{

    private int id;

    private String username;

    private String password;

    private UserRole role;

    public JwtUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(this.role);
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public UserRole getRole() {
        return this.role;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public HashMap<String, Object> buildJson() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("id", this.id);
        result.put("username", this.username);
        result.put("role", this.role.toString());
        return result;
    }

    public String toString() {
        return "Id : " + this.id + ";\nUsername : " + this.username + ";\nUser role : " + this.role.toString(); 
    }
    
}
