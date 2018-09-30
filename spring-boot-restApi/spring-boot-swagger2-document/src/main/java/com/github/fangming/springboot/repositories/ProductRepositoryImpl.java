package com.github.fangming.springboot.repositories;

import com.github.fangming.springboot.domain.Product;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProductRepositoryImpl implements ProductRepository{

    private static final Map<Integer, Product> productDb = Maps.newConcurrentMap();
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public Iterable<Product> findAll() {
        return productDb.values();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(productDb.get(id));
    }

    @Override
    public Product save(Product product) {
        if (null == product.getId()){
            product.setId(idGenerator.getAndDecrement());
        }
        productDb.put(product.getId(), product);
        return product;
    }

    @Override
    public Product deleteById(Integer id) {
        return productDb.remove(id);
    }
}
