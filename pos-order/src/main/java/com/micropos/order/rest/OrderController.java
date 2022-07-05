package com.micropos.order.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.micropos.carts.api.*;
// import com.micropos.carts.dto.*;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;

import reactor.core.publisher.Flux;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.micropos.carts.mapper")
public class OrderController {

    private final StreamBridge streamBridge;

    private static final String bindingName = "summitOrder-out-0";

    public OrderController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    private List<Item> getCart() throws Exception {
        String url = "http://localhost:8080/cart";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        ObjectMapper mapper = new ObjectMapper();
        Item[] items = mapper.readValue(response.toString(), Item[].class);

        if (items != null && items.length > 0)
            streamBridge.send(bindingName, items);

        List<Item> res = new ArrayList<>();
        for (Item item : items)
            res.add(item);
        return res;
    }

    @PostMapping()
    public Flux<Item> summitOrder() {
        List<Item> items = null;
        try {
            items = getCart();
        } catch (Exception e) {
            System.out.print("\n\n");
            System.out.println(e);
            System.out.print("\n\n");
        }
        return Flux.fromIterable(items);
    }
}
