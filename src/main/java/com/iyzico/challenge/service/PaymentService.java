package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.ProductPaymentDto;

import java.util.concurrent.CompletableFuture;

public interface PaymentService {
    CompletableFuture<String> payment(ProductPaymentDto productPaymentDto) throws Exception;
}
