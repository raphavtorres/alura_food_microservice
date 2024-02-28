package com.alura.food.order.dto;

import com.alura.food.order.constant.Status;
import com.alura.food.order.model.Order;
import com.alura.food.order.model.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long id, LocalDateTime dateTime, Status status, List<OrderItem> itens) {
    public OrderDto(Order order) {
        this(order.getId(), order.getDateTime(), order.getStatus(), order.getItens());
    }
}
