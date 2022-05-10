package com.buybricks.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    public ResponseEntity<Object> createOrderFromBasketId(int basketId);

    public ResponseEntity<Object> getOrderAll();

    public ResponseEntity<Object> getOrderByUserId(int userId);

    public ResponseEntity<Object> deleteOrder(int id);
    
}
