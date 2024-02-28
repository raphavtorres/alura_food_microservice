package com.alura.food.order.service;

import com.alura.food.order.constant.Status;
import com.alura.food.order.dto.OrderDto;
import com.alura.food.order.dto.StatusDto;
import com.alura.food.order.model.Order;
import com.alura.food.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Page<OrderDto> getAll(Pageable pagination) {
        return orderRepository.findAll(pagination).map(OrderDto::new);
    }

    public OrderDto getById(Long id) {
        var order = orderRepository.getReferenceById(id);
        return new OrderDto(order);
    }

    public OrderDto createOrder(OrderDto dto) {
        Order order = new Order(dto);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.DONE);
        order.getItems().forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        return new OrderDto(savedOrder);
    }

    public OrderDto updateStatus(Long id, StatusDto statusDto) {
        Order order = orderRepository.byIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(statusDto.status());
        orderRepository.updateStatus(statusDto.status(), order);

        return new OrderDto(order);
    }

    public void approvesOrderPayment(Long id) {

        Order order = orderRepository.byIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        orderRepository.updateStatus(Status.PAID, order);
    }
}