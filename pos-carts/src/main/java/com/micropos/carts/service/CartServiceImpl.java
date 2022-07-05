package com.micropos.carts.service;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private Cart singleton_cart;

    public CartServiceImpl() {
        this.singleton_cart = new Cart();
    }

    @Override
    public Cart cart() {
        return this.singleton_cart;
    }

    @Override
    public Flux<Item> getItems() {
        return Flux.fromIterable(singleton_cart.getItems());
    }

    @Override
    public Mono<Item> getItem(String id) {
        for (Item i : singleton_cart.getItems())
            if (i.getProduct().getId().equals(id))
                return Mono.just(i);
        return null;
    }

    @Override
    public boolean putItem(String id) {
        return this.singleton_cart.addItem(id);
    }

}
