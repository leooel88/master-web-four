package com.buybricks.app.service;

import java.util.HashMap;
import java.util.Optional;

import com.buybricks.app.config.BasketConstant;
import com.buybricks.app.model.Basket;
import com.buybricks.app.model.Brick;
import com.buybricks.app.model.BrickList;
import com.buybricks.app.model.User;
import com.buybricks.app.repository.BasketRepository;
import com.buybricks.app.repository.BrickListRepository;
import com.buybricks.app.repository.BrickRepository;
import com.buybricks.app.repository.UserRepository;
import com.buybricks.app.utils.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("BasketService")
public class BasketServiceImpl implements BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrickListRepository brickListRepository;

    @Autowired
    private BrickRepository brickRepository;

    @Override
    public ResponseEntity<Object> createBasketFromUserId(int userId) {
        if (userId == -1)
            return ResponseHandler.createBadRequest("Cannot create Basket : no user_id was passed.");
        Optional<User> foundUserOpt = userRepository.findById(userId);
        User foundUser;
        try {
            foundUser = foundUserOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createNotFound("Cannot create Basket : no user found with id : " + userId + ".");
        }

        Basket basket = new Basket(foundUser);
        Basket resBasket;
        try {
            resBasket = basketRepository.save(basket);
        } catch (Exception e) {
            return ResponseHandler.createInternalServerError("Cannot create Basket : couldn't create data in database");
        }

        return ResponseHandler.createCreated("basket_created", resBasket.buildJson());
    }

    @Override
    public ResponseEntity<Object> getBasketAll() {
        Iterable<Basket> baskets = basketRepository.findAll();

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("data", Basket.buildMultipleJson(baskets));

        return ResponseHandler.createSuccess("baskets", json);
    }

    @Override
    public ResponseEntity<Object> getBasketById(int id) {
        Optional<Basket> foundBasketOpt = basketRepository.findById(id);

        try {
            Basket foundBasket = foundBasketOpt.get();

            HashMap<String, Object> json = new HashMap<String, Object>();
            json.put("data", foundBasket.buildJson());

            return ResponseHandler.createSuccess("basket", json);
        } catch (Exception e) {
            return ResponseHandler.createNotFound("Cannot search Basket : couldn't find basket with id : " + id + ".");
        }
    }

    @Override
    public ResponseEntity<Object> getBasketByUserId(int userId) {
        Optional<User> foundUserOpt = userRepository.findById(userId);

        try {
            User foundUser = foundUserOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createBadRequest("Cannot search Basket : couldn't find User with id : " + userId + ".");
        }

        Basket foundBasket = basketRepository.findByUserId(userId);

        if (foundBasket == null)
            return ResponseHandler.createNotFound("Cannot search Basket : couldn't find basket with userId : " + userId + ".");

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("data", foundBasket.buildJson());

        return ResponseHandler.createSuccess("basket", json);
    }

    @Override
    public ResponseEntity<Object> updateBasket(int id, int brickId, long productNb) {
        Optional<Basket> optBasket = basketRepository.findById(id);
        Basket foundBasket;

        try {
            foundBasket = optBasket.get();
        } catch (Exception e) {
            return ResponseHandler.createNotFound("Cannot update Basket : couldn't find Basket with id : " + id + ".");
        }

        if (productNb == -1 || brickId == -1)
            return ResponseHandler.createBadRequest("Cannot create update Basket : productNb or brick_id is/are missing.");
        
        Optional<Brick> foundBrickOpt = brickRepository.findById(brickId);
        Brick foundBrick;

        try {
            foundBrick = foundBrickOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createBadRequest("Cannot update Basket : couldn't find a Brick with id : " + brickId + ".");
        }

        if (productNb > foundBrick.getQuantity() || productNb + foundBasket.getProductNb() < BasketConstant.PRODUCT_NB_MIN || productNb + foundBasket.getProductNb() > BasketConstant.PRODUCT_NB_MAX)
            return ResponseHandler.createBadRequest("Cannot update Basket : product_nb is not valid.");
        if ((productNb * foundBrick.getPrice()) + foundBasket.getTotalPrice() < BasketConstant.TOTAL_PRICE_MIN || (productNb * foundBrick.getPrice()) + foundBasket.getTotalPrice() > BasketConstant.TOTAL_PRICE_MAX)
            return ResponseHandler.createBadRequest("Cannot update Basket : total_price is not valid.");

        BrickList brickList = new BrickList(foundBasket.getUser(), foundBasket, foundBrick, productNb);
        brickListRepository.save(brickList);

        foundBasket.setProductNb(foundBasket.getProductNb() + productNb);
        foundBasket.setTotalPrice(foundBasket.getTotalPrice() + (foundBrick.getPrice() * productNb));

        foundBasket = basketRepository.save(foundBasket);

        if (foundBasket == null)
            return ResponseHandler.createInternalServerError("Cannot update Basket : couldn't update data in database.");
        return ResponseHandler.createSuccess("basket_updated", foundBasket.buildJson());
    }

    @Override
    public ResponseEntity<Object> emptyBasketById(int id) {
        Optional<Basket> optBasket = basketRepository.findById(id);
        Basket basket;
        try {
            basket = optBasket.get();
        } catch (Exception e) {
            return ResponseHandler.createNotFound("Cannot empty Basket : couldn't find basket with id : " + id + ".");
        } 

        basket.setProductNb(0);
        basket.setTotalPrice(0);

        basketRepository.save(basket);
        brickListRepository.emptyBasket(id);

        return ResponseHandler.createSuccessNoLabel("Basket successfully emptied.");   
    }

    @Override
    public ResponseEntity<Object> emptyBasketByUserId(int userId) {
        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.get() == null)
            return ResponseHandler.createNotFound("Cannot empty Basket : couldn't find user with id : " + userId + ".");

        Basket basket = basketRepository.findByUserId(userId);
        
        if (basket == null)
            return ResponseHandler.createNotFound("Cannot empty basket : couldn't find basket with userId" + userId + ".");

        return this.emptyBasketById(basket.getId());
    }

    @Override
    public ResponseEntity<Object> deleteBasket(int id) {
        Optional<Basket> foundBasketOpt = basketRepository.findById(id);

        try {
            Basket foundBasket = foundBasketOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createNotFound("Cannot delete Brick : couldn't find brick with id : " + id + ".");
        }
        brickListRepository.emptyBasket(id);
        basketRepository.deleteById(id);

        long remaining = basketRepository.count();

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("Remaining", remaining);

        return ResponseHandler.createSuccess("Basket deleted", json);
    }
    
}
