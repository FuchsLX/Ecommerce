package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.entities.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    List<Cart> getAllCartsByUser(String userId);

    void saveCart(Cart cart);

    Cart getActiveCartByUser(String userId);

    Cart getCartById(String cartId);

    void setCompletedStatusCart(Cart cart, User currentUser);

    Cart getActiveCartBySession(HttpSession session);

    void setActiveCartSessionAttribute(HttpSession session, Cart activeCart);

    void updateSubTotal(Cart cart);

    void initNewActiveCart(Cart activeCart, User currentUser);

}
