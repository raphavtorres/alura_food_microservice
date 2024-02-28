package com.alura.food.payment.controller;


import com.alura.food.payment.dto.PaymentDto;
import com.alura.food.payment.service.PaymentService;
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
@RequestMapping("api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public Page<PaymentDto> list(@PageableDefault(size = 10) Pageable pagination) {
        return paymentService.getAll(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> detail(@PathVariable @NotNull Long id) {
        PaymentDto paymentDto = paymentService.getById(id);
        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> register(@RequestBody @Valid PaymentDto paymentDto, UriComponentsBuilder uriBuilder) {
        PaymentDto paymentDtoPost = paymentService.createPayment(paymentDto);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDtoPost.id()).toUri();

        return ResponseEntity.created(address).body(paymentDtoPost);
    }

    @PutMapping
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto paymentDtoPut = paymentService.updatePayment(id, paymentDto);
        return ResponseEntity.ok(paymentDtoPut);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> delete(@PathVariable @NotNull Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}
