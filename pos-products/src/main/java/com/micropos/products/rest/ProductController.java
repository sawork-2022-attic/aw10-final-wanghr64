package com.micropos.products.rest;

import com.micropos.products.model.Product;
import com.micropos.products.service.ProductService;

import reactor.core.publisher.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@EnableDiscoveryClient
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public Flux<Product> listProducts() {
        return productService.products();
    }

    @GetMapping(value = "/{productId}")
    public Mono<Product> showProductById(@PathVariable String productId) {
        return productService.getProduct(productId);
    }
}
