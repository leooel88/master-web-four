package com.buybricks.app.service;

import java.util.HashMap;
import java.util.Optional;

import com.buybricks.app.model.Basket;
import com.buybricks.app.model.BrickList;
import com.buybricks.app.model.User;
import com.buybricks.app.repository.BasketRepository;
import com.buybricks.app.repository.BrickListRepository;
import com.buybricks.app.repository.UserRepository;
import com.buybricks.app.utils.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("BrickListService")
public class BrickListServiceImpl implements BrickListService {
    @Autowired
    BrickListRepository brickListRepository;
    
    @Autowired
    BasketRepository basketRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<Object> emptyBasket(int basketId) {
        Optional<Basket> foundBasketOpt = basketRepository.findById(basketId);
        Basket foundBasket;
        long remainingNb = 0;
        long initialNb = 0;

        try {
            foundBasket = foundBasketOpt.get();

            if (foundBasket == null)
                return ResponseHandler.createNotFound("Cannot empty brick_list : no Basket found with id : " + basketId + ".");
        } catch (Exception e) {
            return ResponseHandler.createInternalServerError("Cannot empty brick_list : couldn't search in database.");
        }

        initialNb = brickListRepository.count();

        brickListRepository.emptyBasket(basketId);
        
        remainingNb = brickListRepository.count();

        if (initialNb == remainingNb)
            return ResponseHandler.createSuccessNoLabel("Nothing to empty.");
        else
            return ResponseHandler.createSuccessNoLabel("Deleted " + (initialNb - remainingNb) + " entities.");

    }

    @Override
    public ResponseEntity<Object> getBasketByUserId(int userId) {
        Optional<User> foundUserOpt = userRepository.findById(userId);
        User foundUser;

        try {
            foundUser = foundUserOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createInternalServerError("Cannot get brick_list : no user with id : " + userId + ".");
        }

        Iterable<BrickList> foundBrickLists = brickListRepository.findByUserId(userId);
        
        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("data", BrickList.buildMultipleJson(foundBrickLists));

        return ResponseHandler.createSuccess("brick_lists", json);
    }
    
}