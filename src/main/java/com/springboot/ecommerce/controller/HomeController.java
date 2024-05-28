package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.services.CartService;
import com.springboot.ecommerce.services.ProductReviewService;
import com.springboot.ecommerce.services.ProductService;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final ProductReviewService productReviewService;


    @GetMapping("/setCartSession")  
    public String setCartSession(@AuthenticationPrincipal UserDetails user, HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        if (!currentUser.getRole().getName().equals(BootstrapRole.CUSTOMER.getName())) {
            return "redirect:/admin";
        } else {
            Cart activeCart = cartService.getActiveCartByUser(currentUser.getId());
            cartService.setActiveCartSessionAttribute(session, activeCart);
            return "redirect:/home";
        }

    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("phoneProducts",
                productService.getAllProductByCategoryName(
                        "Smart Phone",
                        productService.findPaginated(1, 10, "title", "asc")).getContent()
        );
        model.addAttribute("laptopProducts",
                productService.getAllProductByCategoryName(
                        "Laptop",
                        productService.findPaginated(1, 10, "title", "asc")).getContent()
        );
        model.addAttribute("watchProducts",
                productService.getAllProductByCategoryName(
                        "Watch",
                        productService.findPaginated(1,10, "title", "asc")).getContent());
        return "home";
    }

    
    @GetMapping("/login")
    public String getSignInPage(){
        return "login";
    }

    @GetMapping("/product/{slugProduct}")
    public String getProductDetailForCustomer(
            @PathVariable("slugProduct") String slugProduct,
            Model model){
        Product product = productService.findBySlugProduct(slugProduct);
        var reviews = productReviewService.getAllReviewWithPagination(product.getId());
        model.addAttribute("totalReviews", reviews.getTotalElements());
        model.addAttribute("totalReviewPages", reviews.getTotalPages());
        model.addAttribute("currentReviewPage", 1);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("relatedProducts",
                productService.getAllRelatedProduct(
                        product, productService.findPaginated(1,10, "title", "asc")).getContent()
        );
        return "product-detail";
    }
}
