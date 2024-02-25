package com.yon.backend.cartapp.backendcartapp.services;

import java.util.List;

import com.yon.backend.cartapp.backendcartapp.models.entities.Product;

public interface ProductService {
    List<Product> findAll();
}
