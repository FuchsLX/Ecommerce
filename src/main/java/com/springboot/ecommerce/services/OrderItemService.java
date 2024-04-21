package com.springboot.ecommerce.services;


import com.springboot.ecommerce.entities.cart.CartItem;
import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.order.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    List<OrderItem> setOrderItemByCartItem(List<CartItem> cartItems, Order order);

    void saveOrderItem(OrderItem orderItem);
}
