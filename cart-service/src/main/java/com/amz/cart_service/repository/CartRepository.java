package com.amz.cart_service.repository;

import com.amz.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUsername(String username);
    Optional<Cart> findByUsernameAndProductId(String username, Long productId);
}
