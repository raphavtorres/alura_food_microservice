package com.alura.food.order.model;


import com.alura.food.order.constant.Status;
import com.alura.food.order.dto.OrderDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity(name = "Order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    public Order(OrderDto dto) {
        this.id = dto.id();
    }
}
