package com.springboot.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @GetMapping("/main")
    public String templateAnalytics() {
        return "analytics-main";
    }

    @GetMapping("/product/{productId}")
    public String productAnalytics(@PathVariable("productId") String productId, Model model) {
        model.addAttribute("productId", productId);
        return "analytics-product-detail";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryAnalytics(@PathVariable("categoryId") String categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "analytics-category-detail";
    }
}
