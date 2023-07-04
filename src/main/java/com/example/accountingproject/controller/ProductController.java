package com.example.accountingproject.controller;

import com.example.accountingproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/list")
    public String listProducts(Model model) {

        model.addAttribute("products", productService.findAll());

        return "/company/company-list";
    }




}
