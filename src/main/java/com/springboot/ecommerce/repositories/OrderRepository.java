package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {


    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1")
    List<Order> getAllOrderByCurrentUser(String currentUserId);


    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='COMPLETED'")
    List<Order> getCompletedOrderByCurrentUser(String currentUserId);

    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='PROCESSING'")
    List<Order> getProcessingOrderByCurrentUser(String currentUserId);

    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='DELIVERED'")
    List<Order> getDeliveredOrderByCurrentUser(String currentUserId);

    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='CANCELLED'")
    List<Order> getCancelledOrderByCurrentUser(String currentUserId);


    @Query("select o " +
            "from Order as o " +
            "where o.status='COMPLETED'")
    List<Order> getAllCompletedOrder();


    @Query("select o " +
            "from Order as o " +
            "where o.status='PROCESSING'")
    List<Order> getAllProcessingOrder();

    @Query("select o " +
            "from Order as o " +
            "where o.status='DELIVERED'")
    List<Order> getAllDeliveredOrder();

    @Query("select o " +
            "from Order as o " +
            "where o.status='CANCELLED'")
    List<Order> getAllCancelledOrder();
}
