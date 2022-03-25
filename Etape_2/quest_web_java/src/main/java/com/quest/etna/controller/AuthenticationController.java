package com.quest.etna.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.quest.etna.repositories")
public class AuthenticationController {

    private static UserRepository userRepository;

    @Autowired
    private UserRepository userRepo;

    @PostConstruct
    private void init() {
        userRepository = this.userRepo;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || username == "" || password == null || password == "") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "Username or password is missing !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        else if (userRepository.findFirstByUsernameIgnoreCase(username) != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This username has already been used !");
            return new ResponseEntity<Object>(map, HttpStatus.CONFLICT);
        }
        try {
            User savingResponse = userRepository.save(new User(username, password));
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "User couldn't be created");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }
            UserDetails userDetails = new UserDetails(savingResponse);
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", userDetails.getUsername());
            map.put("role", userDetails.getRole().toString());
            return new ResponseEntity<Object>(map, HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }
}
