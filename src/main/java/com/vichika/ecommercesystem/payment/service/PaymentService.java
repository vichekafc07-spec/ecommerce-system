package com.vichika.ecommercesystem.payment.service;

import com.vichika.ecommercesystem.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse createCheckoutSession(Long orderId);

    void handleWebhook(String payload, String signature);
}
