package com.buybricks.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface BrickListService {
    public ResponseEntity<Object> emptyBasket(int basketId);
}
