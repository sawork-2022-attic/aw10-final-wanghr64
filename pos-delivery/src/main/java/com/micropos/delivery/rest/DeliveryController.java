package com.micropos.delivery.rest;

// import com.micropos.carts.api.*;
// import com.micropos.carts.dto.*;
import com.micropos.carts.model.Item;

import reactor.core.publisher.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/delivery")
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.micropos.carts.mapper")
public class DeliveryController {

  class OrderChecker implements Consumer<Item[]> {
    @Override
    public void accept(Item[] items) {
      orders.add(Arrays.asList(items));
      System.out.println("add new order!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
  }

  @Bean
  public Consumer<Item[]> checkDelivery() {
    return new OrderChecker();
  }

  private List<List<Item>> orders = new ArrayList<>();

  @GetMapping()
  public Flux<List<Item>> viewDelivery() {
    return Flux.fromIterable(orders);
  }
}
