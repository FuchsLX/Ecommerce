package com.springboot.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @GetMapping("/demo")
    public String demoAnalytics() {
        return "demo-analytics";
    }

    @GetMapping("/product")
    public String productAnalytics() {
        return "product-analytics";
    }

    @GetMapping("/product/{productId}")
    public String productAnalytics(@PathVariable("productId") String productId, Model model) {
        model.addAttribute("productId", productId);
        return "product-detail-analytics";
    }

    @GetMapping("/category")
    public String categoryAnalytics() {
        return "category-analytics";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryAnalytics(@PathVariable("categoryId") String categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "category-detail-analytics";
    }
}
