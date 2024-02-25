package com.yon.backend.cartapp.backendcartapp.services;

import java.util.List;
import java.util.Optional;

import com.yon.backend.cartapp.backendcartapp.models.entities.User;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> update(User user, Long id);
    void remove(Long id);

}
