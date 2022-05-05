package com.buybricks.app.repository;

import com.buybricks.app.model.Brick;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface BrickRepository extends CrudRepository<Brick, Integer>{
    @Query(value = "SELECT * FROM brick b WHERE b.dim_h = ?1 or b.dim_l = ?1 or b.dim_w = ?1", nativeQuery = true)
    Iterable<Brick> findByDimension(long dim);

    @Query(value = "SELECT * FROM brick b WHERE b.price >= ?1 and b.price <= ?2", nativeQuery = true)
    Iterable<Brick> findByPrice(long min, long max);

    Brick findByNameIgnoreCase(String name);

    @Query(value = "SELECT * FROM brick b WHERE b.name LIKE %?1%", nativeQuery = true)
    Iterable<Brick> findByNameContainingIgnoreCase(String name);

    void deleteById(int id);

}