package com.micropos.webui.control;

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
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping()
    public String pos(Model model) {
        Product[] productResponse = restTemplate.getForObject("http://localhost:8080/products",
                Product[].class);
        model.addAttribute("products", productResponse);
        Item[] cartResponse = restTemplate.getForObject("http://localhost:8080/cart", Item[].class);
        model.addAttribute("cart", cartResponse);
        return "index";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        Product[] productResponse = restTemplate.getForObject("http://localhost:8080/products",
                Product[].class);
        model.addAttribute("products", productResponse);
        restTemplate.postForObject(
                "http://localhost:8080/cart/" + pid, null, Item[].class);
        Item[] cartResponse = restTemplate.getForObject("http://localhost:8080/cart", Item[].class);
        model.addAttribute("cart", cartResponse);
        return "index";
    }
}
