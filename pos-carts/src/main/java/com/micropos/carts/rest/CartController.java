package com.micropos.carts.rest;

// import com.micropos.carts.api.*;
// import com.micropos.carts.dto.*;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;

import reactor.core.publisher.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@EnableDiscoveryClient
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public Flux<Item> listItems() {
        return this.cartService.getItems();
    }

    @PostMapping(value = "/add/{productId}")
    public Flux<Item> putProductById(@PathVariable String productId) {
        boolean flag = cartService.putItem(productId);
        return this.cartService.getItems();
    }

    @PostMapping(value = "/del/{productId}")
    public Flux<Item> delProductById(@PathVariable String productId) {
        boolean flag = cartService.delItem(productId);
        return this.cartService.getItems();
    }

    @PostMapping(value = "/min/{productId}")
    public Flux<Item> minProductById(@PathVariable String productId) {
        boolean flag = cartService.modItem(productId, -1);
        return this.cartService.getItems();
    }

    @PostMapping(value = "/clear")
    public Flux<Item> clear() {
        boolean flag = cartService.clearItem();
        return this.cartService.getItems();
    }
}
