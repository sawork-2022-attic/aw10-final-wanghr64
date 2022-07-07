package com.micropos.products.repository;

import com.micropos.products.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.sql.*;

@Repository
public class AMZ implements ProductRepository {

    private List<Product> products = null;

    static final String USER = "sa";
    static final String PASS = "";
    static final String DB_URL = "jdbc:mysql://localhost:3306/satest";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection conn;

    private Random priceGenerator;

    private ResultSet rs;

    public AMZ() throws Exception {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        priceGenerator = new Random();
        rs = conn.createStatement().executeQuery("SELECT * FROM amazon LIMIT 50;");
        products = new ArrayList<>();
        try {
            while (rs.next()) {
                products.add(new Product(rs.getString("asin"), rs.getString("title"), priceGenerator.nextInt(20) + 10,
                        rs.getString("imageURLHighRes")));

            }
        } catch (Exception e) {
            System.out.println("Failed to get Products.");
            System.out.println(e);
        }
    }

    @Override
    public Flux<Product> allProducts() {
        System.out.println("Try to get Products.");
        
        return Flux.fromIterable(products);
    }

    @Override
    public Mono<Product> findProduct(String productId) {
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                return Mono.just(p);
            }
        }
        return null;
    }

}
