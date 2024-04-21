package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.entities.cart.CartItem;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.entities.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    CartItem getCartItemByProductAndCart(String productId, String cartId);

    void saveCartItem (CartItem cartItem);

    Cart deleteCartItem(String cartItemId, User currentUser);

    CartItem getCartItemById(String cartItemId);

    void updateQuantityCartItem(String cartItemId, Long quantity, HttpSession session);

    void updateQuantityCartItem(CartItem cartItem, Long quantity);

    void deleteCartItemByProduct(String productId);

    void cartItemInitializer(CartItem cartItem, Cart activeCart,
                             Product product, Long quantity,
                             User currentUser, HttpSession session);

    void updateExistingCartItemWhenAddProductToCart(CartItem existingCartItem,
                                                    Long quantity,Product product,
                                                    HttpSession session);
}
