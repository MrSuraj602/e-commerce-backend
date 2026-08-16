package com.MrSuraj.eco.ecommerce.repo;

import com.MrSuraj.eco.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("""
    SELECT o FROM Order o
    WHERE o.user.id = :userId
    AND o.orderStatus IN (
        PENDING,
        PLACED,
        CONFIRMED,
        SHIPPED,
        DELIVERED
    )
    """)
    List<Order> getUsersOrders(@Param("userId") Long userId);
}
