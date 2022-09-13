package com.iyzico.challenge.service.impl;

import com.iyzico.challenge.dto.ProductPaymentDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.OutOfStockException;
import com.iyzico.challenge.service.PaymentService;
import com.iyzico.challenge.service.PaymentServiceClients;
import com.iyzico.challenge.service.ProductService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentServiceClients paymentServiceClients;
    private final ProductService productService;

    public PaymentServiceImpl(PaymentServiceClients paymentServiceClients, ProductService productService) {
        this.paymentServiceClients = paymentServiceClients;
        this.productService = productService;
    }

    @Override
    public CompletableFuture<String> payment(ProductPaymentDto productPaymentDto) throws Exception {

        Product product = productService.findById(productPaymentDto.getProductId());

        int quantity = productPaymentDto.getQuantity();
        if (!product.checkStockQuantity(quantity)) {
            throw new OutOfStockException("Invalid stock number");
        }

        int currentStock = product.getStock() - quantity;
        try{
            productService.updateStock(product.getId(), currentStock);
            BigDecimal price = product.getPrice().multiply(new BigDecimal(quantity));
            return paymentServiceClients.call(price);
        }catch (ObjectOptimisticLockingFailureException opEx) {
            throw new Exception("Optimistic Lock : The record you attempted to edit was modified by another user after you got the original value");
        }catch (Exception ex) {
            throw new Exception("Payment error : " + ex.getMessage());
        }
    }
}
