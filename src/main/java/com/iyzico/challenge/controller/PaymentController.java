package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.ProductPaymentDto;
import com.iyzico.challenge.response.ResponseHandler;
import com.iyzico.challenge.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Object> payment(@Valid @RequestBody ProductPaymentDto productPaymentDto) throws Exception{
        String responseText = paymentService.payment(productPaymentDto).join();
        return ResponseHandler.generateResponse("Successfully " + responseText, HttpStatus.OK, Boolean.TRUE);
    }
}
