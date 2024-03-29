package com.alura.food.payment.controller;


import com.alura.food.payment.dto.PaymentDto;
import com.alura.food.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> list(@PageableDefault(size = 10) Pageable pagination) {
        return ResponseEntity.ok(paymentService.getAll(pagination));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> detail(@PathVariable @NotNull Long id) {
        PaymentDto paymentDto = paymentService.getById(id);
        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PaymentDto> register(@RequestBody @Valid PaymentDto paymentDto, UriComponentsBuilder uriBuilder) {
        PaymentDto paymentDtoPost = paymentService.createPayment(paymentDto);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDtoPost.id()).toUri();

        return ResponseEntity.created(address).body(paymentDtoPost);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto paymentDtoPut = paymentService.updatePayment(id, paymentDto);
        return ResponseEntity.ok(paymentDtoPut);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<PaymentDto> delete(@PathVariable @NotNull Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "updateOrder", fallbackMethod = "authorizedPaymentWithPendingPayment")
    public void confirmPayment(@PathVariable @NotNull Long id) {
        paymentService.confirmPayment(id);
    }

    public void authorizedPaymentWithPendingPayment(Long id, Exception e) {
        paymentService.alterStatus(id);
    }
}
