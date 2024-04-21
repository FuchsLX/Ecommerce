package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    List<Cart> findByUser_Id (String id);

    @Query("select c " +
            "from Cart as c " +
            "where c.user.id=?1 and c.cartStatus= 'ACTIVE'")
    Cart getActiveCartByUser(String userId);




}
