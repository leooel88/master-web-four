package com.buybricks.app.repository;

import java.util.Optional;

import com.buybricks.app.model.Basket;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Integer>{
    
    @Query(value = "SELECT * FROM basket b WHERE b.user_id = ?1", nativeQuery = true)
    Basket findByUserId(int userId);

    // @Modifying
    // @Query(value="UPDATE basket b SET b.product_nb = ?2, b.price = ?3 WHERE b.id = ?1", nativeQuery = true)
    // long updateBasket(int basketId, int productNb, int totalPrice);

    @Transactional
    @Modifying
    @Query(value="UPDATE basket b SET b.product_nb = 0, b.total_price = 0 WHERE b.id = ?1", nativeQuery = true)
    void emptyBasket(int id);

    @Modifying
    void deleteById(int id);

}
