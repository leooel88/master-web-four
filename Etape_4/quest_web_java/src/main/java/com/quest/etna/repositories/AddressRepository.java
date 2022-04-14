package com.quest.etna.repositories;

import com.quest.etna.model.Address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer>{
    @Query(value = "SELECT * FROM address a WHERE a.street = ?1 AND a.postal_code = ?2 AND a.city = ?3 AND a.country = ?4 LIMIT 1", 
            nativeQuery = true)
    Address findFirstSameAddress(String street, String postalCode, String city, String country);

    @Query(value = "SELECT * FROM address a WHERE a.user_id = ?1",
            nativeQuery = true)
    Iterable<Address> findByUser(int userId);
}
