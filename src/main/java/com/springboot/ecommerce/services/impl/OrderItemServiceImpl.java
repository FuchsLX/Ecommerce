package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.cart.CartItem;
import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.order.OrderItem;
import com.springboot.ecommerce.repositories.OrderItemRepository;
import com.springboot.ecommerce.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;


    @Override
    public List<OrderItem> setOrderItemByCartItem(List<CartItem> cartItems, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            this.saveOrderItem(orderItem);
        }
        return orderItems;
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
