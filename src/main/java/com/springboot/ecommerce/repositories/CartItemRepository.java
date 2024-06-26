package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    @Query("select ct " +
            "from CartItem as ct " +
            "inner join Cart as c on c.id = ct.cart.id " +
            "inner join Product as p on p.id = ct.product.id " +
            "where c.id = :cartId and p.id = :productId"
    )
    CartItem findByProductAndCart(String productId, String cartId);

}
