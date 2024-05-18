package com.springboot.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @GetMapping("/demo")
    public String demoAnalytics() {
        return "demo-analytics";
    }
}
