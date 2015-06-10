package com.udl.softarch.springexample.repositories;

import com.udl.softarch.springexample.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by debian-jordi on 23/03/15.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(@Param("name") String name);
    User findUserByEmail(@Param("email") String email);
}
