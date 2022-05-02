package com.buybricks.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface BrickService {
    public ResponseEntity<Object> createBrick(String name, long dimH, long dimL, long dimW, long price, long quantity, String imageUrl);

    public ResponseEntity<Object> getBrickAll();

    public ResponseEntity<Object> getBricksByName(String name);

    public ResponseEntity<Object> getBricksByDimension(long dimension);

    public ResponseEntity<Object> getBricksByPrice(long min, long max);

    public ResponseEntity<Object> updateBrick(int id, long price, long quantity);

    public ResponseEntity<Object> deleteBrick(int id);
    
}
