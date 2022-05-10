package com.buybricks.app.repository;

import java.util.Optional;

import com.buybricks.app.model.Basket;
import com.buybricks.app.model.BrickList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BrickListRepository extends CrudRepository <BrickList, Integer> {
    @Query(value="SELECT * FROM brick_list b WHERE b.user_id = ?1", nativeQuery = true)
    Iterable<BrickList> findByUserId(int userId);

    @Query(value="SELECT * FROM brick_list b WHERE b.basket_id = ?1 and b.brick_id = ?2", nativeQuery = true)
    Optional<BrickList> findByBasketAndBrick(int basketId, int brickId);

    @Modifying
    @Query(value="UPDATE brick_list b SET b.quantity = ?2, b.price = ?3 WHERE b.id = ?1", nativeQuery = true)
    Basket updateList(int brickListId, long quantity, long price);
    
    @Transactional
    @Modifying
    @Query(value="DELETE FROM brick_list b WHERE b.basket_id = ?1", nativeQuery = true)
    void emptyBasket(int basketId);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM brick_list b WHERE b.basket_id = ?1 and b.brick_id = ?2", nativeQuery = true)
    void deleteProductInBasket(int basketId, int brickId);
    
}
