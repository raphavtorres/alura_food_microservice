package com.alura.food.payment.service;

import com.alura.food.payment.constant.Status;
import com.alura.food.payment.dto.PaymentDto;
import com.alura.food.payment.model.Payment;
import com.alura.food.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.module.ResolutionException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Page<PaymentDto> getAll(Pageable pagination) {
        return paymentRepository.findAll(pagination).map(PaymentDto::new);
    }

    public PaymentDto getById(Long id) {
        try {
            Payment payment = paymentRepository.getReferenceById(id);
            return new PaymentDto(payment);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment(paymentDto);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);

        return new PaymentDto(payment);
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment payment = new Payment(paymentDto);
        payment.setId(id);
        payment = paymentRepository.save(payment);
        return new PaymentDto(payment);
    }


    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
