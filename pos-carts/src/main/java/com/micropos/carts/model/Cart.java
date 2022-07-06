package com.micropos.carts.model;

import java.io.Serializable;
import java.util.*;

import com.micropos.products.model.Product;
import com.micropos.products.repository.JDRepository;
import com.micropos.products.service.ProductServiceImpl;

public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();
    private List<Product> products;

    public Cart() {
        Iterable<Product> iter = new JDRepository().allProducts().toIterable();
        products = new ArrayList<>();
        for (Product product : iter)
            products.add(product);
    }

    public void setItem(Item[] items) {
        this.items.clear();
        for (Item item : items)
            this.items.add(item);
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public double total() {
        double res = 0;
        for (Item i : items) {
            res += i.getProduct().getPrice() * i.getQuantity();
        }
        return res;
    }

    public boolean addItem(String id) {
        for (Product p : products) {
            if (id.equals(p.getId())) {
                boolean flag = false;
                for (Item i : items) {
                    if (p.equals(i.getProduct())) {
                        i.setQuantity(i.getQuantity() + 1);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    Item i = new Item();
                    i.setProduct(p);
                    i.setQuantity(1);
                    items.add(i);
                }
                return true;
            }
        }
        return false;
    }

}