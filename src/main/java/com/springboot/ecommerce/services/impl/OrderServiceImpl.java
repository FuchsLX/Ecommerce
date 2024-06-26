package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.order.OrderStatus;
import com.springboot.ecommerce.entities.transaction.TransactionType;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.exception.QuantityExceededOrderException;
import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.services.*;
import com.springboot.ecommerce.entities.cart.CartItem;
import com.springboot.ecommerce.entities.order.OrderItem;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.entities.transaction.Transaction;
import com.springboot.ecommerce.entities.user.UserMeta;
import com.springboot.ecommerce.repositories.OrderRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final TransactionService transactionService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserMetaService userMetaService;
    private final OrderItemService orderItemService;



    @Override
    public Order saveOrder(Order order,
                          List<OrderItem> orderItems,
                          UserMeta userMeta,
                          Transaction transaction) {
        BigDecimal subTotal = BigDecimal.valueOf(0);
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderItem orderItem: orderItems) {
            subTotal = subTotal.add(orderItem.getPrice());
            BigDecimal discountedPrice = orderItem.getPrice().multiply(
                    BigDecimal.valueOf(1).subtract(
                        orderItem.getDiscount().divide
                            (BigDecimal.valueOf(100), RoundingMode.HALF_UP))
                    );
            total = total.add(discountedPrice);
        }
        order.setSubTotal(subTotal);
        order.setTotal(total);
        order.setAddress(userMeta.getAddress());
        order.setMobile(userMeta.getMobile());
        order.setFirstName(userMeta.getFirstName());
        order.setMiddleName(userMeta.getMiddleName());
        order.setLastName(userMeta.getLastName());
        order.setTransaction(transaction);
        order.setOrderItems(orderItems);
        return orderRepository.save(order);

    }

//    @Override
//    public void saveOrder(Order order, Transaction transaction) {
//        order.setTransaction(transaction);
//        orderRepository.save(order);
//    }

//    @Override
//    public void saveOrder(Order order) {
//        orderRepository.save(order);
//    }

    @Override
    public List<Order> getAllCancelledOrder() {
        return orderRepository.getAllCancelledOrder();
    }

    @Override
    public List<Order> getAllDeliveredOrder() {
        return orderRepository.getAllDeliveredOrder();
    }

    @Override
    public List<Order> getAllProcessingOrder() {
        return orderRepository.getAllProcessingOrder();
    }

    @Override
    public List<Order> getAllCompletedOrder() {
        return orderRepository.getAllCompletedOrder();
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getCancelledOrderByCurrentUser(String currentUserId) {
        return orderRepository.getCancelledOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getDeliveredOrderByCurrentUser(String currentUserId) {
        return orderRepository.getDeliveredOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getProcessingOrderByCurrentUser(String currentUserId) {
        return orderRepository.getProcessingOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getCompletedOrderByCurrentUser(String currentUserId) {
        return orderRepository.getCompletedOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getAllOrderByCurrentUser(String currentUserId) {
        return orderRepository.getAllOrderByCurrentUser(currentUserId);
    }


    @Override
    public void setCompletedOrder(String orderId) {
        Order order = this.getOrderById(orderId);
        Transaction transaction = order.getTransaction();
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        transactionService.setSuccessTransaction(transaction);
    }

    @Override
    public void setCancelledOrder(String orderId) {
        Order order = this.getOrderById(orderId);
        if (!order.getStatus().equals(OrderStatus.PROCESSING)){
            for (OrderItem orderItem : order.getOrderItems()){
                Product product = orderItem.getProduct();
                Long newQuantityProduct = product.getQuantity() + orderItem.getQuantity();
                product.setQuantity(newQuantityProduct);
                productService.saveProduct(product);
            }
        }
        Transaction transaction = order.getTransaction();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        transactionService.setCancelledTransaction(transaction);
    }

    @Override
    public void setDeliveredOrder(String orderId) {
        Order order = this.getOrderById(orderId);
        for (OrderItem orderItem: order.getOrderItems()) {
            Product product = orderItem.getProduct();
            long newQuantityProduct = product.getQuantity() - orderItem.getQuantity();
            if (newQuantityProduct < 0 ){
                throw new QuantityExceededOrderException(orderId);
            } else {
                product.setQuantity(newQuantityProduct);
                productService.saveProduct(product);
            }
        }
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get();
        } else {
            throw new IllegalStateException("Order not found for id: " + orderId);
        }
    }


    @Override
    public int checkCartBeforeOrder(User currentUser, HttpSession session) {
        Cart activeCart = cartService.getActiveCartBySession(session);
        int countChangeInCartBeforeOrder = 0;

        activeCart.setUser(currentUser);
        cartService.saveCart(activeCart);

        for (CartItem cartItem : activeCart.getCartItems()){
            Product product = productService.getProductById(
                    cartItem.getProduct().getId()
            );

            cartItem.setProduct(product);
            cartItem.setCart(activeCart);
            cartItemService.saveCartItem(cartItem);

            if (product.getQuantity() == 0){
                activeCart = cartItemService.deleteCartItem(
                        cartItem.getId(), currentUser
                );
                countChangeInCartBeforeOrder ++;

            } else if (product.getQuantity() > 0 && product.getQuantity() < cartItem.getQuantity()) {
                cartItemService.updateQuantityCartItem(cartItem, 1L);
                activeCart = cartItem.getCart();
                countChangeInCartBeforeOrder ++;
            }
        }
        cartService.updateSubTotal(activeCart);
        cartService.setActiveCartSessionAttribute(session, activeCart);

        return countChangeInCartBeforeOrder;
    }


    @Override
    public void buyAgainHandler(String orderId, HttpSession session) {
        Order oldOrder = this.getOrderById(orderId);
        Cart activeCart = cartService.getActiveCartBySession(session);

        if (activeCart == null){
            activeCart = new Cart();
            cartService.initNewActiveCart(activeCart, oldOrder.getUser());
        }

        for (OrderItem orderItem : oldOrder.getOrderItems()){
            if (orderItem.getProduct().getQuantity() != 0){

                CartItem existingCartItem = cartItemService
                        .getCartItemByProductAndCart(
                                orderItem.getProduct().getId(),
                                activeCart.getId()
                        );

                if (existingCartItem != null){
                    Long newQuantityCartItem = existingCartItem.getQuantity() + orderItem.getQuantity();

                    if (newQuantityCartItem > existingCartItem.getProduct().getQuantity()){
                        cartItemService.updateQuantityCartItem(
                                existingCartItem,
                                1L
                        );
                    } else {
                        cartItemService.updateQuantityCartItem(
                                existingCartItem,
                                newQuantityCartItem
                        );
                    }

                    activeCart = existingCartItem.getCart();
                    cartService.saveCart(activeCart);

                }  else {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setProduct(orderItem.getProduct());
                    newCartItem.setPrice(orderItem.getPrice());
                    newCartItem.setDiscount(orderItem.getDiscount());

                    if (orderItem.getProduct().getQuantity() < orderItem.getQuantity()){
                        newCartItem.setQuantity(1L);
                    } else {
                        newCartItem.setQuantity(orderItem.getQuantity());
                    }

                    newCartItem.setCart(activeCart);
                    activeCart.getCartItems().add(newCartItem);
                    cartItemService.saveCartItem(newCartItem);
                    cartService.saveCart(activeCart);
                }
            }
        }
        cartService.setActiveCartSessionAttribute(session, activeCart);
    }


    @Override
    public Order processingNewOrder(Transaction newTransaction, User currentUser, HttpSession session) {
        Cart activeCart = cartService.getActiveCartBySession(session);
        UserMeta userMeta  = userMetaService.getUserMetaByCurrentUser(currentUser.getId());
        Order processingOrder = new Order();

        processingOrder.setUser(currentUser);
        orderRepository.save(processingOrder);
//        this.saveOrder(processingOrder);

        newTransaction.setOrder(processingOrder);
        newTransaction.setUser(currentUser);
        transactionService.saveTransaction(newTransaction);

        List<OrderItem> orderItems = orderItemService.setOrderItemByCartItem(
                activeCart.getCartItems(),
                processingOrder
        );
        cartService.setCompletedStatusCart(activeCart, currentUser);
        session.removeAttribute("cart");
        return this.saveOrder(processingOrder, orderItems, userMeta, newTransaction);
    }


}
