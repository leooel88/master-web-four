package com.quest.etna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/testSuccess")
    @ResponseStatus(HttpStatus.OK)
    public String testSuccess(){
        return "success";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/testNotFound")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String testNotFound(){
        return "not found";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/testError")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String testError(){
        return "error";
    }
}
