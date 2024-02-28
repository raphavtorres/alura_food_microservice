package com.alura.food.payment.model;

import com.alura.food.payment.constant.Status;
import com.alura.food.payment.dto.PaymentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;


@Table(name = "payments")
@Entity(name = "Payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private BigDecimal value;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 19)
    private String number;

    @NotBlank
    @Size(max = 7)
    private String expiration;

    @NotBlank
    @Size(min = 3, max = 3)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Long orderId;

    @NotNull
    private Long paymentMethod;

    public Payment(PaymentDto paymentDto) {
        this.id = paymentDto.id();
        this.value = paymentDto.value();
        this.name = paymentDto.name();
        this.number = paymentDto.number();
        this.expiration = paymentDto.expiration();
        this.code = paymentDto.code();
        this.status = paymentDto.status();
        this.orderId = paymentDto.orderId();
        this.paymentMethod = paymentDto.paymentMethod();
    }
}
