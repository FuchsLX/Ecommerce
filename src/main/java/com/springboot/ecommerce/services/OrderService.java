package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.order.OrderItem;
import com.springboot.ecommerce.entities.transaction.Transaction;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.entities.user.UserMeta;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order saveOrder(Order order,
                   List<OrderItem> orderItems,
                   UserMeta userMeta,
                   Transaction transaction);

//    void saveOrder(Order order);

//    void saveOrder(Order order, Transaction transaction);

    List<Order> getAllCancelledOrder();

    List<Order> getAllDeliveredOrder();

    List<Order> getAllProcessingOrder();

    List<Order> getAllCompletedOrder();

    List<Order> getAllOrder();

    List<Order> getCancelledOrderByCurrentUser(String currentUserId);

    List<Order> getDeliveredOrderByCurrentUser(String currentUserId);

    List<Order> getProcessingOrderByCurrentUser(String currentUserId);

    List<Order> getCompletedOrderByCurrentUser(String currentUserId);

    List<Order> getAllOrderByCurrentUser(String currentUserId);

    void setCompletedOrder(String orderId);

    void setCancelledOrder(String orderId);

    void setDeliveredOrder(String Id);

    Order getOrderById(String orderId);

    int checkCartBeforeOrder(User currentUser, HttpSession session);

    void buyAgainHandler(String orderId, HttpSession session);

    Order processingNewOrder(Transaction newTransaction, User currentUser, HttpSession session);

//    Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

//    Page<Order> getAllOrderWithPaginationAndSort(int pageNo, int pageSize, String sortField, String sortDirection);


}
