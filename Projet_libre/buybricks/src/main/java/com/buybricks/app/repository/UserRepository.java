package com.buybricks.app.repository;

import com.buybricks.app.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    User findFirstByUsernameIgnoreCase(String username);
}
