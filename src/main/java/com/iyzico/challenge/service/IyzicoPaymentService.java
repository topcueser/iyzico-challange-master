package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
//@Transactional
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    private BankService bankService;
    private PaymentRepository paymentRepository;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
    }

//    public void pay(BigDecimal price) {
//        //pay with bank
//        BankPaymentRequest request = new BankPaymentRequest();
//        request.setPrice(price);
//        BankPaymentResponse response = bankService.pay(request);
//
//        //insert records
//        Payment payment = new Payment();
//        payment.setBankResponse(response.getResultCode());
//        payment.setPrice(price);
//        paymentRepository.save(payment);
//        logger.info("Payment saved successfully!");
//    }

    public void pay(BigDecimal price) {
        //pay with bank
        BankPaymentResponse bankPaymentResponse = bankPaymentProcess(price);
        logger.info("Bank payment successfully!");

        //insert records
        savePayment(bankPaymentResponse.getResultCode(), price);
        logger.info("Payment saved successfully!");
    }

    private BankPaymentResponse bankPaymentProcess(BigDecimal price) {
        BankPaymentRequest bankPaymentRequest = new BankPaymentRequest();
        bankPaymentRequest.setPrice(price);
        return bankService.pay(bankPaymentRequest);
    }

    /*
    * Answer - 2
    * */
    @Transactional
    void savePayment(String bankResultCode, BigDecimal price) {
        Payment payment = new Payment();
        payment.setBankResponse(bankResultCode);
        payment.setPrice(price);
        paymentRepository.save(payment);
    }
}
