package com.vichika.ecommercesystem.payment;

import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.payment.dto.PaymentResponse;
import com.vichika.ecommercesystem.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<APIResponse<PaymentResponse>> checkout(@PathVariable Long orderId) {
        return ResponseEntity.ok(APIResponse.ok(paymentService.createCheckoutSession(orderId)));
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> webhook(@RequestBody String payload,
                                     @RequestHeader("Stripe-Signature") String signature) {
        paymentService.handleWebhook(payload, signature);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/success")
    public String success() {
        return "Payment Success";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "Payment Cancelled";
    }
}
