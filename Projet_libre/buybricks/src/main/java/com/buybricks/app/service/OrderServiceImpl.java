package com.buybricks.app.service;

import java.util.HashMap;
import java.util.Optional;

import com.buybricks.app.model.Basket;
import com.buybricks.app.model.Order;
import com.buybricks.app.model.User;
import com.buybricks.app.repository.BasketRepository;
import com.buybricks.app.repository.BrickListRepository;
import com.buybricks.app.repository.OrderRepository;
import com.buybricks.app.repository.UserRepository;
import com.buybricks.app.utils.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("OrderService")
public class OrderServiceImpl implements OrderService{

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BrickListRepository brickListRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> createOrderFromBasketId(int basketId) {
        Optional<Basket> foundBasketOpt = basketRepository.findById(basketId);
        Basket foundBasket;

        try {
            foundBasket = foundBasketOpt.get();
        } catch(Exception e) {
            return ResponseHandler.createNotFound("Cannot create Order : no Basket found with id : " + basketId + ".");
        }

        Order order = new Order(foundBasket);
        orderRepository.save(order);

        basketRepository.emptyBasket(basketId);
        brickListRepository.emptyBasket(basketId);

        return ResponseHandler.createSuccess("Order created", order.buildJson());

    }

    @Override
    public ResponseEntity<Object> getOrderAll() {
        Iterable<Order> orders = orderRepository.findAll();

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("data", Order.buildMultipleJson(orders));

        return ResponseHandler.createSuccess("orders", json);
    }

    @Override
    public ResponseEntity<Object> getOrderByUserId(int userId) {
        Optional<User> foundUserOpt = userRepository.findById(userId);

        try {
            User foundUser = foundUserOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createBadRequest("Cannot search Order : couldn't find User with id : " + userId + ".");
        }

        Iterable<Order> foundOrders = orderRepository.findByUserId(userId);

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("data", Order.buildMultipleJson(foundOrders));

        return ResponseHandler.createSuccess("orders", json);
    }

    @Override
    public ResponseEntity<Object> deleteOrder(int id) {
        Optional<Order> foundOrderOpt = orderRepository.findById(id);
        Order foundOrder;

        try {
            foundOrder = foundOrderOpt.get();
        } catch (Exception e) {
            return ResponseHandler.createNotFound("Cannot delete Order : couldn't find order with id : " + id + ".");
        }

        orderRepository.deleteById(id);

        long remaining = orderRepository.count();

        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("Remaining", remaining);

        return ResponseHandler.createSuccess("Order deleted", json);
    }
    
}
