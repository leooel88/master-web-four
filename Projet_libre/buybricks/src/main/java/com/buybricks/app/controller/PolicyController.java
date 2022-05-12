package com.buybricks.app.controller;

import java.util.HashMap;

import com.buybricks.app.model.JwtUserDetails;
import com.buybricks.app.repository.UserRepository;
import com.buybricks.app.utils.ResponseHandler;
import com.buybricks.app.utils.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PolicyController {
    @Autowired
    private UserRepository userRepository;
    
    // IS ADMIN
    @RequestMapping(value = "/user/isadmin", method = RequestMethod.GET)
    public ResponseEntity<?> checkPolicy() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRole userRole;
        if (principal instanceof JwtUserDetails) {
            userRole = ((JwtUserDetails)principal).getRole();

            HashMap<String, Object> json = new HashMap<String, Object>();

            if (userRole.equals(UserRole.ROLE_ADMIN)) {
                json.put("admin", true);
            } else {
                json.put("admin", false);
            }
            return ResponseHandler.createSuccess("data", json);
        } else {
            return ResponseHandler.createBadRequest("No token passed !");
        }
    }
}
