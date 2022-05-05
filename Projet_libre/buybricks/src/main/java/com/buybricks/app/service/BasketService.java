package com.buybricks.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface BasketService {
    public ResponseEntity<Object> createBasketFromUserId(int userId);

    public ResponseEntity<Object> getBasketAll();

    public ResponseEntity<Object> getBasketById(int id);

    public ResponseEntity<Object> getBasketByUserId(int userId);

    public ResponseEntity<Object> updateBasket(int id, int brickId, long productNb);

    public ResponseEntity<Object> emptyBasketById(int id);

    public ResponseEntity<Object> emptyBasketByUserId(int userId);

    public ResponseEntity<Object> deleteBasket(int id);
    
}
