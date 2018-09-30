package com.github.fangming.springboot.repositories;

import com.github.fangming.springboot.domain.Product;

import java.util.Optional;

public interface ProductRepository {

    Iterable<Product> findAll();

    Optional<Product> findById(Integer id);

    Product save(Product product);

    Product deleteById(Integer id);
}