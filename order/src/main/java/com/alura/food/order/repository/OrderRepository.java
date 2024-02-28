package com.alura.food.order.repository;

import com.alura.food.order.constant.Status;
import com.alura.food.order.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.status = :status where o = :order")
    void updateStatus(Status status, Order order);

    @Query(value = "SELECT o from Order o LEFT JOIN FETCH o.items where o.id = :id")
    Order byIdWithItems(Long id);
}
