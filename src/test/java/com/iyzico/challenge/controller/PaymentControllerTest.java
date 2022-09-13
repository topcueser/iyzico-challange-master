package com.iyzico.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyzico.challenge.dto.ProductPaymentDto;
import com.iyzico.challenge.exception.OutOfStockException;
import com.iyzico.challenge.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest {

    private PaymentService paymentService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static final String baseURL = "/api/payments";

    @Before
    public void init() {
        paymentService = mock(PaymentService.class);
        objectMapper = new ObjectMapper();
        PaymentController paymentController = new PaymentController(paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new Exception()).build();
    }

    @Test
    public void when_payment_then_return_success() throws Exception {
        when(paymentService.payment(any())).thenReturn(CompletableFuture.completedFuture("success"));
        ProductPaymentDto paymentDto = new ProductPaymentDto();
        paymentDto.setProductId(1L);
        paymentDto.setQuantity(10);

        mockMvc.perform(MockMvcRequestBuilders.post(baseURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void when_payment_then_throw_invalid_stock_number_error() throws Exception {
        when(paymentService.payment(any())).thenThrow(new OutOfStockException("Invalid stock number"));
        ProductPaymentDto paymentDto = new ProductPaymentDto();
        paymentDto.setProductId(1L);
        paymentDto.setQuantity(-1);

        mockMvc.perform(MockMvcRequestBuilders.post(baseURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)))
                .andExpect(status().is(400))
                .andReturn();
    }
}
