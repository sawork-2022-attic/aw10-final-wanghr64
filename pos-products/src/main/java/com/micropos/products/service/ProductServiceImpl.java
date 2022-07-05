package com.micropos.products.service;

import com.micropos.products.model.Product;
import com.micropos.products.repository.ProductRepository;

import reactor.core.publisher.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> products() {
        return productRepository.allProducts();
    }

    @Override
    public Mono<Product> getProduct(String id) {
        return productRepository.findProduct(id);
    }

    @Override
    public Mono<Product> randomProduct() {
        return null;
    }
}
