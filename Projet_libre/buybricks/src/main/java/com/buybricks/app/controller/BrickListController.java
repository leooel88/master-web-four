package com.buybricks.app.controller;

import com.buybricks.app.service.BrickListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.buybricks.repository")
public class BrickListController {

    @Autowired
    BrickListService brickListService;

    // FIND ALL FROM BASKET
    @RequestMapping(value = "/bricklist/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> readByUserId(@PathVariable int user_id) {
        return brickListService.getBasketByUserId(user_id);
    }
}
