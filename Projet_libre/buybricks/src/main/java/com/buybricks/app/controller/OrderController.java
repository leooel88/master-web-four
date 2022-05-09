package com.buybricks.app.controller;

import java.util.Map;

import com.buybricks.app.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.buybricks.repository")
public class OrderController {
    @Autowired
    OrderService orderService;

    // FIND ALL
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<?> readAll() {
        return orderService.getOrderAll();
    }

    // FIND BY USER_ID
    @RequestMapping(value = "/order/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> readByUserId(@PathVariable int user_id) {
        return orderService.getOrderByUserId(user_id);
    }

    // DELETE ORDER
    @RequestMapping(value = "/order/{order_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable int order_id) {
        return orderService.deleteOrder(order_id);
    }

}
