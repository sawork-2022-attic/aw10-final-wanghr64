package com.micropos.carts.service;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;

import reactor.core.publisher.*;

import java.util.List;

public interface CartService {

    public Cart cart();

    public Flux<Item> getItems();

    public Mono<Item> getItem(String id);

    public boolean putItem(String id);

    public boolean delItem(String id);

    public boolean modItem(String id, int num);

    public boolean clearItem();
}
