package com.micropos.products.repository;


import com.micropos.products.model.Product;

import reactor.core.publisher.*;

import java.util.List;

public interface ProductRepository {

    public Flux<Product> allProducts();

    public Mono<Product> findProduct(String productId);

}