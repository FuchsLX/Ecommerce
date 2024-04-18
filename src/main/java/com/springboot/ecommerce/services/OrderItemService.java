package com.springboot.ecommerce.services;


import com.springboot.ecommerce.entities.cart.CartItem;
import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.order.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> setOrderItemByCartItem(List<CartItem> cartItems, Order order);

    void saveOrderItem(OrderItem orderItem);
}
