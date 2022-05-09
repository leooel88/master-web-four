package com.buybricks.app.repository;

import com.buybricks.app.model.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{
    public Order findByUserId(int userId);
}
