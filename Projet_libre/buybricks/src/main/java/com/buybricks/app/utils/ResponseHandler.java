package com.buybricks.app.utils;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> createBadRequest(String errorMessage) {
        HashMap<String,Object> json = new HashMap<String,Object>();
        json.put("Error", errorMessage);

        return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> createCreated(String createLabel, HashMap<String, Object> jsonObject) {
        HashMap<String,Object> json = new HashMap<String,Object>();
        json.put(createLabel, jsonObject);

        return new ResponseEntity<Object>(json, HttpStatus.CREATED);
    }

    public static ResponseEntity<Object> createInternalServerError(String errorMessage) {
        HashMap<String,Object> json = new HashMap<String,Object>();
        json.put("Error", errorMessage);

        return new ResponseEntity<Object>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Object> createNotFound(String errorMessage) {
        HashMap<String,Object> json = new HashMap<String,Object>();
        json.put("Error", errorMessage);

        return new ResponseEntity<Object>(json, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> createSuccess(String successLabel, HashMap<String, Object> jsonObject) {
        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put(successLabel, jsonObject);

        return new ResponseEntity<Object>(json, HttpStatus.OK);
    }
}
