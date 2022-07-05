package com.micropos.products.service;

import com.micropos.products.model.Product;

import reactor.core.publisher.*;

import java.util.List;

public interface ProductService {


    public Flux<Product> products();

    public Mono<Product> getProduct(String id);

    public Mono<Product> randomProduct();
}
