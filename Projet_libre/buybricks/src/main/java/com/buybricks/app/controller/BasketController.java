package com.buybricks.app.controller;

import java.util.Map;

import com.buybricks.app.service.BasketService;
import com.buybricks.app.utils.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.buybricks.repository")
public class BasketController {

    @Autowired
    BasketService basketService;

    // FIND ALL
    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ResponseEntity<?> readAll() {
        return basketService.getBasketAll();
    }

    // FIND BY ID
    @RequestMapping(value = "/basket/{basket_id}", method = RequestMethod.GET)
    public ResponseEntity<?> readById(@PathVariable String basket_id) {
        return basketService.getBasketById(Integer.parseInt(basket_id));
    }

    // FIND BY USER ID
    @RequestMapping(value = "/basket/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> readByUserId(@PathVariable int user_id) { 
            return basketService.getBasketByUserId(user_id);
    }

    // CREATE BASKET
    @RequestMapping(value = "/basket", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Map<String, String> body) {
        int userId = -1;

        if (body.get("user_id") != null)
            userId = Integer.parseInt(body.get("user_id"));
        return basketService.createBasketFromUserId(userId);
    }

    // UPDATE BASKET
    @RequestMapping(value = "/basket/{basket_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable String basket_id, @RequestBody Map<String, String> body) {
        int basketId = Integer.parseInt(basket_id);
        int brickId = -1;
        long quantity = -1;

        if (body.get("brick_id") != null)
            brickId = Integer.parseInt(body.get("brick_id"));
        if (body.get("brick_quantity") != null)
            quantity = Long.parseLong(body.get("brick_quantity"));

        return basketService.updateBasket(basketId, brickId, quantity);
    }

    // EMPTY BASKET
    @RequestMapping(value = "/basket/empty/{basket_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> empty(@PathVariable String basket_id) {
        int basketId = Integer.parseInt(basket_id);

        return basketService.emptyBasketById(basketId);
    }

    // ORDER BASKET
    @RequestMapping(value = "/basket/order/{basket_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> order(@PathVariable String basket_id) {
        int basketId = Integer.parseInt(basket_id);

        return basketService.orderBasket(basketId);
    }

    // DELETE BASKET
    @RequestMapping(value = "/basket/{basket_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String basket_id) {
        int basketId = Integer.parseInt(basket_id);

        return basketService.deleteBasket(basketId);
    }
    
}
