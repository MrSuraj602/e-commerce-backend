package com.MrSuraj.eco.ecommerce.repo;

import com.MrSuraj.eco.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
