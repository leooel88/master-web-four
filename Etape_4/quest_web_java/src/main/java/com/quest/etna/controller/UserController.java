package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// FIND ALL
@RestController
@EnableJpaRepositories("com.quest.etna.repositories")
public class UserController {
    private static UserRepository userRepository;
    
    @Autowired
    private UserRepository userRepo;

    @PostConstruct
    private void init() {
        userRepository = this.userRepo;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> readAll() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<HashMap<String, String>> jsonResponse = User.buildMultipleJson(users);
        return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
    }

    // FIND BY ID
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> readById(@PathVariable int userId) {
        if (userId < 0) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "Invalid id passed (id < 0) !");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        User user = null;

        try {
            user = userOpt.get();
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No user found for id : " + userId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
        if (user != null) {
            HashMap<String, String> jsonResponse = user.buildJson();
            return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
        } else {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No user found for id : " + userId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody Map<String, String> body, @PathVariable int userId){
        if (userId < 0) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "Invalid id passed (id < 0) !");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        User user = null;

        try {
            user = userOpt.get();
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No user found for id : " + userId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authUsername;
        if (principal instanceof JwtUserDetails) {
            authUsername = ((JwtUserDetails)principal).getUsername();
        } else {
            authUsername = principal.toString();
        }

        if (userId != userRepository.findFirstByUsernameIgnoreCase(authUsername).getId() && userRepository.findFirstByUsernameIgnoreCase(authUsername).getRole().toString() != "ROLE_ADMIN") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This user is not you, you cannot modify it !");
            return new ResponseEntity<Object>(map, HttpStatus.METHOD_NOT_ALLOWED);
        }

        String username = body.get("username");
        String role = body.get("role");

        if (username != null) {
            user.setUsername(username);
        }
        if (role != null) {
            user.setRoleFromString(role);
        }

        try {
            User savingResponse = userRepository.save(user);
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "User couldn't be saved");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(savingResponse.getId()));
            map.put("username", savingResponse.getUsername());
            map.put("role", savingResponse.getRole().toString());
            return new ResponseEntity<Object>(map, HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int userId){
        Optional<User> userOpt = userRepository.findById(userId);
        User user = null;

        try {
            user = userOpt.get();
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No user found for id : " + userId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authUsername;
        if (principal instanceof JwtUserDetails) {
            authUsername = ((JwtUserDetails)principal).getUsername();
        } else {
            authUsername = principal.toString();
        }

        if (user.getId() != userRepository.findFirstByUsernameIgnoreCase(authUsername).getId() && userRepository.findFirstByUsernameIgnoreCase(authUsername).getRole().toString() != "ROLE_ADMIN") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This user is not you, you cannot delete it !");
            return new ResponseEntity<Object>(map, HttpStatus.METHOD_NOT_ALLOWED);
        }

        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Success", "FALSE");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> error = new HashMap<String, String>();
        error.put("Success", "TRUE");
        return new ResponseEntity<Object>(error, HttpStatus.OK);
    }
}
