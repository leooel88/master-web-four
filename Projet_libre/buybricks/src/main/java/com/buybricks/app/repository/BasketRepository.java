package com.buybricks.app.repository;

import com.buybricks.app.model.Basket;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Integer>{
    
    @Query(value = "SELECT * FROM basket b WHERE b.user_id = ?1", nativeQuery = true)
    Basket findByUserId(int userId);

    @Modifying
    @Query(value="UPDATE basket b SET b.product_nb = ?2, b.price = ?3 WHERE b.id = ?1", nativeQuery = true))
    long updateBasket(int basketId, int productNb, int totalPrice);

    @Modifying
    @Query(value="UPDATE basket b SET b.product_nb = 0, b.price = 0 WHERE b.id = ?1", nativeQuery = true)
    long emptyBasket(int id);

}
