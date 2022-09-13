package com.iyzico.challenge.service;

import com.iyzico.challenge.TestProductUtils;
import com.iyzico.challenge.dto.ProductPaymentDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.service.impl.PaymentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    private PaymentService paymentService;

    @Mock
    private ProductService productService;
    @Mock
    private PaymentServiceClients paymentServiceClients;

    @Before
    public void init() {
        this.paymentService = new PaymentServiceImpl(paymentServiceClients, productService);
    }

    @Test(expected = Exception.class)
    public void when_payment_then_invalid_stock_number_error() throws Exception{
        ProductPaymentDto paymentDto = new ProductPaymentDto();
        paymentDto.setProductId(1L);
        paymentDto.setQuantity(-10);
        paymentService.payment(paymentDto);
    }

    @Test(expected = Exception.class)
    public void when_payment_then_not_enough_stock_found_error() throws Exception{
        Product product = TestProductUtils.createProduct();
        product.setStock(5);
        when(productService.findById(anyLong())).thenReturn(product);
        ProductPaymentDto paymentDto = new ProductPaymentDto();
        paymentDto.setProductId(1L);
        paymentDto.setQuantity(10);
        paymentService.payment(paymentDto);
    }
}
