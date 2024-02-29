package com.alura.food.order.controller;

import com.alura.food.order.dto.OrderDto;
import com.alura.food.order.dto.StatusDto;
import com.alura.food.order.service.OrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderDto>> list(Pageable pagination) {
        var page = orderService.getAll(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> detail(@PathVariable @NotNull Long id) {
        OrderDto order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
        OrderDto orderDto = orderService.createOrder(dto);

        URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderDto.id()).toUri();

        return ResponseEntity.created(uri).body(orderDto);
    }

    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<OrderDto> updateStatus(@PathVariable @NotNull Long id, @RequestBody StatusDto status) {
        OrderDto orderDto = orderService.updateStatus(id, status);
        return ResponseEntity.ok(orderDto);
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> approvesPayment(@PathVariable @NotNull Long id) {
        orderService.approvesOrderPayment(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/port")
    public String returnsPort(@Value("${local.server.port}") String port) {
        return String.format("Request answered by instance executing on port %s", port);
    }

}
