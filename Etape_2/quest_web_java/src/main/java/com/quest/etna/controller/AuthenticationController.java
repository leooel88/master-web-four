package com.quest.etna.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;
import com.quest.etna.responses.ResponseHandler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || username == "" || password == null || password == "") {
            return ResponseHandler.generateResponse("Error : username or password is missing !", HttpStatus.BAD_REQUEST, null);
        }
        else if (userRepository.findFirstByUsernameIgnoreCase(username) != null) {
            return ResponseHandler.generateResponse("Error : this username has already been used !", HttpStatus.CONFLICT, null);
        }
        User savingResponse = userRepository.save(new User(username, password));
        UserDetails userDetails = new UserDetails(savingResponse);
        return ResponseHandler.generateResponse(null, HttpStatus.CREATED, userDetails);
    }
}
