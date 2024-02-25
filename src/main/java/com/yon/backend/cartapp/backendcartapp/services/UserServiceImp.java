package com.yon.backend.cartapp.backendcartapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yon.backend.cartapp.backendcartapp.models.entities.User;
import com.yon.backend.cartapp.backendcartapp.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
         Optional<User> o = findById(id);
         User userOptional = null;
        if(o.isPresent()){
            User userDb = o.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setPassword(user.getPassword());
            userDb.setEmail(user.getEmail());
            userOptional = this.save(userDb);
            save(userDb);
        }
        return Optional.ofNullable(userOptional);
    }
    

}
