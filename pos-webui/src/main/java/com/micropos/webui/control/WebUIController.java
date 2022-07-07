package com.micropos.webui.control;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.products.model.Product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Controller
@RequestMapping("/")
@EnableDiscoveryClient
public class WebUIController {
    private RestTemplate restTemplate;

    private Cart cart;

    public WebUIController() throws Exception {
        this.restTemplate = new RestTemplate();
        this.cart = new Cart();
    }

    private void getInfo(Model model) {
        Product[] productResponse = restTemplate.getForObject("http://localhost:8080/products",
                Product[].class);
        model.addAttribute("products", productResponse);
        Item[] cartResponse = restTemplate.getForObject("http://localhost:8080/cart", Item[].class);
        cart.setItem(cartResponse);
        model.addAttribute("cart", cart);
        Item[][] deliveryResponse = restTemplate.getForObject(
                "http://localhost:8080/delivery", Item[][].class);
        model.addAttribute("deliveries", deliveryResponse);
    }

    @GetMapping()
    public String pos(Model model) {
        getInfo(model);
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "pid") String pid, Model model) {
        restTemplate.postForObject(
                "http://localhost:8080/cart/del/" + pid, null, Item[].class);
        getInfo(model);
        return "index";
    }

    @GetMapping("/minus")
    public String minus(@RequestParam(name = "pid") String pid, Model model) {
        restTemplate.postForObject(
                "http://localhost:8080/cart/min/" + pid, null, Item[].class);
        getInfo(model);
        return "index";
    }

    @GetMapping("/clear")
    public String clear(Model model) {
        restTemplate.postForObject(
                "http://localhost:8080/cart/clear", null, Item[].class);
        getInfo(model);
        return "index";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        restTemplate.postForObject(
                "http://localhost:8080/cart/add/" + pid, null, Item[].class);
        getInfo(model);
        return "index";
    }

    // @GetMapping("/category")
    // public String catogory(@RequestParam(name = "cate") String cate, Model model)
    // {
    // model.addAttribute("products", posService.products(cate));
    // model.addAttribute("cart", posService.getCart());
    // model.addAttribute("categories", posService.categories());
    // return "index";
    // }

    @GetMapping(value = "/post-order")
    public String getMethodName(Model model) {
        restTemplate.postForObject(
                "http://localhost:8080/order", null, Item[].class);
        getInfo(model);
        return "index";
    }

}
