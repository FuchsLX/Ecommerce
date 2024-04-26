package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.exception.EmptyCartException;
import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.services.*;
import com.springboot.ecommerce.entities.cart.CartItem;
import com.springboot.ecommerce.entities.product.Product;
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
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;


    @GetMapping()
    public String viewCartPage(Model model,
                               HttpSession session){
        Cart activeCart = cartService.getActiveCartBySession(session);
        if (activeCart != null && ! activeCart.getCartItems().isEmpty()){
            model.addAttribute("cart", activeCart);
            return "cart";
        } else {
            throw new EmptyCartException();
        }
    }

    @PostMapping("/test")
    @ResponseBody
    public String wtf() {
        return "deo gieu kieu gi";
    }

    @GetMapping("/test")
    @ResponseBody
    public String wtfGet() {
        return "deo gieu kieu gi";
    }

    @PostMapping("/add-product-to-cart/{productId}/{quantity}")
    @ResponseBody
    public void addProductToCart(@PathVariable("productId") String productId,
                                 @PathVariable("quantity") Long quantity,
                                 @AuthenticationPrincipal UserDetails user,
                                 HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartService.getActiveCartBySession(session);
        Product product = productService.getProductById(productId);

        if (activeCart == null){
            activeCart = new Cart();
            cartService.initNewActiveCart(activeCart, currentUser);

        }

        CartItem cartItem = cartItemService.getCartItemByProductAndCart(
                product.getId(), activeCart.getId()
        );
        if (cartItem == null){
            cartItem = new CartItem();
            cartItemService.cartItemInitializer(
                    cartItem, activeCart, product,
                    quantity, currentUser, session
            );
        } else {
            cartItemService.updateExistingCartItemWhenAddProductToCart(
                    cartItem, quantity, product, session
            );
        }
    }


    @GetMapping("delete-cart-item/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") String cartItemId,
                                 @AuthenticationPrincipal UserDetails user,
                                 HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartItemService.deleteCartItem(cartItemId, currentUser);
        cartService.updateSubTotal(activeCart);
        cartService.setActiveCartSessionAttribute(session, activeCart);
        return "redirect:/cart";
    }

    @PostMapping("update-quantity-cart-item/{cartItemId}/{quantity}")
    @ResponseBody
    public void  updateQuantityCartItem(
            @PathVariable("cartItemId") String cartItemId,
            @PathVariable("quantity") Long quantity,
            HttpSession session){
        cartItemService.updateQuantityCartItem(cartItemId, quantity, session);
    }

}
