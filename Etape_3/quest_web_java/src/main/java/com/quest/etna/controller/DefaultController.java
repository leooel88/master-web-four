package com.quest.etna.controller;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.UserDetails;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @RequestMapping("/testSuccess")
    @ResponseStatus(HttpStatus.OK)
    public String testSuccess(){
        return "success";
    }

    @RequestMapping("/testNotFound")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String testNotFound(){
        return "not found";
    }

    @RequestMapping("/testError")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String testError(){
        return "error";
    }
    
    @RequestMapping(value = "/me")
    @ResponseStatus(HttpStatus.OK)
    public String me(){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        return userDetails.getUsername();
    }
}
