package com.github.fangming.springboot.bootstrap;

import com.github.fangming.springboot.domain.Product;
import com.github.fangming.springboot.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AppInitDataBootstrap implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(AppInitDataBootstrap.class);

    private final ProductRepository productRepository;

    @Autowired
    public AppInitDataBootstrap(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadProducts();
    }

    private void loadProducts() {
        Product shirt = new Product();
        shirt.setDescription("Spring Framework Guru Shirt");
        shirt.setPrice(new BigDecimal("18.95"));
        shirt.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
        shirt.setProductId("235268845711068308");
        productRepository.save(shirt);

        logger.info("Saved Shirt - id: " + shirt.getId());

        Product mug = new Product();
        mug.setDescription("Spring Framework Guru Mug");
        mug.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
        mug.setProductId("168639393495335947");
        mug.setPrice(new BigDecimal("11.95"));
        productRepository.save(mug);

        logger.info("Saved Mug - id:" + mug.getId());
    }
}
