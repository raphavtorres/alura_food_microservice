package com.alura.food.payment.dto;

import com.alura.food.payment.constant.Status;
import com.alura.food.payment.model.Payment;

import java.math.BigDecimal;

public record PaymentDto(
        Long id,
        BigDecimal value,
        String name,
        String number,
        String expiration,
        String code,
        Status status,
        Long orderId,
        Long paymentMethod
) {
    public PaymentDto(Payment payment) {
        this(payment.getId(), payment.getValue(), payment.getName(), payment.getNumber(), payment.getExpiration(), payment.getCode(), payment.getStatus(), payment.getOrderId(), payment.getPaymentMethod());
    }
}
