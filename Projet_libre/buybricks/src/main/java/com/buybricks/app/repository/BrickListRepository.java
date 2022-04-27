package com.buybricks.app.repository;

import com.buybricks.app.model.Basket;
import com.buybricks.app.model.BrickList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BrickListRepository extends CrudRepository <BrickList, Integer> {

    @Query(value="SELECT * FROM brick_list b WHERE b.basket_id = ?1")
    Iterable<Basket> findByBasketId(int basketId);

    @Modifying
    @Query(value="UPDATE brick_list b SET b.quantity = ?2, b.price = ?3 WHERE b.id = ?1")
    long updateList(int brickListId, long quantity, long price);

    @Modifying
    @Query(value="DELETE * FROM brick_list b WHERE b.basket_id = ?1 and b.brick_id == ?2")
    long deleteList(int basketId, int brickId);
}
