package com.buybricks.app.controller;

import com.buybricks.app.service.BrickService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.buybricks.repository")
public class BrickController {

    @Autowired
	private BrickService brickService;

    // FIND ALL
    @RequestMapping(value = "/brick", method = RequestMethod.GET)
    public ResponseEntity<?> readAll() {
        return brickService.getBrickAll();
    }

    // FIND BY NAME
    @RequestMapping(value = "/brick/{brickName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> readByName(@PathVariable String brickName) {
        return brickService.getBricksByName(brickName);
    }

    // CREATE
    @RequestMapping(value = "/brick", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@PathVariable String brickName, @PathVariable long brickDimH, @PathVariable long brickDimL, @PathVariable long brickDimW, @PathVariable long brickPrice, @PathVariable long brickQuantity, @PathVariable String brickImageUrl) {
        return brickService.createBrick(brickName, brickDimH, brickDimL, brickDimW, brickPrice, brickQuantity, brickImageUrl);
    }
    
}
