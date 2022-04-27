package com.buybricks.app.service;

import java.util.HashMap;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.buybricks.app.config.BrickConstant;
import com.buybricks.app.model.Brick;
import com.buybricks.app.repository.BrickRepository;
import com.buybricks.app.utils.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("BrickService")
public class BrickServiceImpl implements BrickService {
    private static BrickRepository brickRepository;
    
    @Autowired
    private BrickRepository brickRepo;

    @PostConstruct
    private void init() {
        brickRepository = this.brickRepo;
    }

    @Override
    public ResponseEntity<Object> createBrick(String name, long dimH, long dimL, long dimW, long price, long quantity, String imageUrl) {
        if (name == null || name.length() < BrickConstant.NAME_MIN_LENGTH || name.length() > BrickConstant.NAME_MAX_LENGTH)
            return ResponseHandler.createBadRequest("Cannot create Brick : name is not valid.");

        if (dimH <= BrickConstant.DIM_MIN || dimL <= BrickConstant.DIM_MIN || dimW <= BrickConstant.DIM_MIN || dimH > BrickConstant.DIM_MAX || dimL > BrickConstant.DIM_MAX || dimW > BrickConstant.DIM_MAX)
            return ResponseHandler.createBadRequest("Cannot create Brick : dimensions are not valid.");

        if (price < BrickConstant.PRICE_MIN || price > BrickConstant.DIM_MAX)
            return ResponseHandler.createBadRequest("Cannot create Brick : price is not valid.");

        if (quantity < BrickConstant.QUANTITY_MAX || quantity > BrickConstant.QUANTITY_MIN)
            return ResponseHandler.createBadRequest("Cannot create Brick : quantity is not valid.");

        if (imageUrl == null || imageUrl.length() < BrickConstant.IMAGE_URL_MIN_LENGTH || imageUrl.length() > BrickConstant.IMAGE_URL_MAX_LENGTH)
            return ResponseHandler.createBadRequest("Cannot create Brick : imageUrl is not valid.");

        Brick brick = new Brick(name, dimH, dimL, price, quantity, price, imageUrl);
        Brick resBrick = brickRepository.save(brick);

        if (resBrick == null)
            return ResponseHandler.createInternalServerError("Cannot create Brick : couldn't create data in database.");
        
        return ResponseHandler.createCreated("Brick created", resBrick.buildJson());
    }

    @Override
    public ResponseEntity<Object> getBrickAll() {
        Iterable<Brick> bricks = brickRepository.findAll();

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("data", Brick.buildMultipleJson(bricks));

        return ResponseHandler.createSuccess("Bricks", json);
    }

    @Override
    public ResponseEntity<Object> getBricksByName(String name) {
        if (name.length() < BrickConstant.NAME_MIN_LENGTH || name.length() > BrickConstant.NAME_MAX_LENGTH)
            return ResponseHandler.createBadRequest("Cannot search Brick : name is not valid.");

        Iterable<Brick> bricks = brickRepository.findByNameContainingIgnoreCase(name);

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("name", name);
        json.put("data", Brick.buildMultipleJson(bricks));

        return ResponseHandler.createSuccess("Bricks", json);
    }

    @Override
    public ResponseEntity<Object> getBricksByDimension(long dimension) {
        if (dimension < BrickConstant.DIM_MIN || dimension > BrickConstant.DIM_MAX)
            return ResponseHandler.createBadRequest("Cannot search Brick : dimension is not valid.");

        Iterable<Brick> bricks = brickRepository.findByDimension(dimension);

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("dimension", dimension);
        json.put("data", Brick.buildMultipleJson(bricks));

        return ResponseHandler.createSuccess("Bricks", json);
    }

    @Override
    public ResponseEntity<Object> getBricksByPrice(long min, long max) {
        if (min < BrickConstant.PRICE_MIN || min > BrickConstant.PRICE_MAX || max < BrickConstant.PRICE_MIN || max > BrickConstant.PRICE_MAX || min > max)
            return ResponseHandler.createBadRequest("Cannot search Brick : prices are not valid.");

        Iterable<Brick> bricks = brickRepository.findByPrice(min, max);

        HashMap<String, Object> json = new HashMap<String, Object>();
        HashMap<String, Object> prices = new HashMap<String, Object>();
        prices.put("min", min);
        prices.put("max", max);
        json.put("prices", prices);
        json.put("data", Brick.buildMultipleJson(bricks));

        return ResponseHandler.createSuccess("Bricks", json);
    }

    @Override
    public ResponseEntity<Object> updateBrick(int id, long price, long quantity) {
        if (price < BrickConstant.PRICE_MIN || price > BrickConstant.PRICE_MAX)
            return ResponseHandler.createBadRequest("Cannot update Brick : price is invalid.");
        if (quantity < BrickConstant.QUANTITY_MIN || quantity > BrickConstant.QUANTITY_MAX)
            return ResponseHandler.createBadRequest("Cannot update Brick : quantity is not valid.");

        Optional<Brick> optBrick = brickRepository.findById(id);

        if (optBrick.get() == null)
            return ResponseHandler.createNotFound("Cannot update Brick : couldn't find brick with id : " + id + ".");
        Brick brick = optBrick.get();
        brick.setPrice(price);
        brick.setQuantity(quantity);

        brick = brickRepository.save(brick);

        if (brick == null)
            return ResponseHandler.createInternalServerError("Cannot update Brick : couldn't update data in database.");
        return ResponseHandler.createSuccess("Brick updated", brick.buildJson());
    }

    @Override
    public ResponseEntity<Object> deleteBrick(int id) {
        long deletedNb = brickRepository.deleteById(id);

        if (deletedNb == 0)
            return ResponseHandler.createNotFound("Cannot delete Brick : couldn't find brick with id : " + id + ".");
        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("deleted", deletedNb);

        return ResponseHandler.createSuccess("Brick deleted", json);
    }
    
}
