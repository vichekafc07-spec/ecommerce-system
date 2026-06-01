package com.vichika.ecommercesystem.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.model.Order;
import com.vichika.ecommercesystem.checkout.model.OrderStatus;
import com.vichika.ecommercesystem.checkout.repository.OrderRepository;
import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.payment.Payment;
import com.vichika.ecommercesystem.payment.PaymentRepository;
import com.vichika.ecommercesystem.payment.dto.PaymentResponse;
import com.vichika.ecommercesystem.payment.enums.PaymentStatus;
import com.vichika.ecommercesystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final AuthUtil authUtil;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @Override
    public PaymentResponse createCheckoutSession(Long orderId) {

        var user = authUtil.getCurrentUser();
        var order = getOrderById(orderId,user);

        if (order.getStatus() == OrderStatus.CANCELLED){
            throw new BadRequestException("Can't not checkout");
        }

        if (order.getStatus() == OrderStatus.PAID){
            throw new BadRequestException("Already checkout");
        }

        if (order.getStatus() == OrderStatus.FAILED){
            throw new BadRequestException("Can't not checkout");
        }

        try {

            var productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName("Order #" + order.getOrderNumber())
                    .build();

            var priceData = SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(order.getTotalAmount()
                                            .multiply(BigDecimal.valueOf(100))
                                            .longValue()
                            )
                            .setProductData(productData)
                            .build();

            var lineItem = SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(priceData)
                            .build();

            var params = SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/api/v1/payments/success")
                    .setCancelUrl("http://localhost:8080/api/v1/payments/cancel")
                            .addLineItem(lineItem)
                            .build();

            var session = Session.create(params);

            var payment = Payment.builder()
                    .sessionId(session.getId())
                    .amount(order.getTotalAmount())
                    .status(PaymentStatus.PENDING)
                    .order(order)
                    .build();
            paymentRepository.save(payment);

            return new PaymentResponse(session.getUrl());

        } catch (StripeException e) {
            throw new RuntimeException("Stripe payment failed");
        }

    }

    @Override
    public void handleWebhook(String payload, String signature) {

        try {

            var event = Webhook.constructEvent(payload, signature, webhookSecret);

            switch (event.getType()) {

                case "checkout.session.completed" ->
                        handleCheckoutCompleted(event);
                case "checkout.session.expired" ->
                        handleCheckoutExpired(event);
                default ->
                        log.info(
                                "Unhandled Stripe event: {}",
                                event.getType());
            }
        } catch (Exception e) {
            System.err.println("WEBHOOK FAILED");
            e.printStackTrace();
        }
    }

    private void handleCheckoutCompleted(Event event) {

        try {
            var session = (Session) event.getDataObjectDeserializer()
                    .deserializeUnsafe();
            var payment = paymentRepository.findBySessionId(session.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found for session: " + session.getId()));

            payment.setStatus(PaymentStatus.PAID);
            payment.setTransactionId(session.getPaymentIntent());

            paymentRepository.save(payment);

            var order = payment.getOrder();

            if (order == null) {
                throw new RuntimeException("Order is null for payment id: " + payment.getId());
            }

            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

        } catch (Exception e) {
            System.err.println("WEBHOOK ERROR");
            e.printStackTrace();
        }
    }

    private void handleCheckoutExpired(Event event){

        try {

            var session = (Session) event.getDataObjectDeserializer()
                    .deserializeUnsafe();

            var payment = paymentRepository.findBySessionId(session.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + session.getId()));
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

        } catch (Exception e) {
            System.err.println("WEBHOOK ERROR");
            e.printStackTrace();
        }
    }

    private Order getOrderById(Long orderId, AppUser user){
        return orderRepository.findByIdAndUser(orderId,user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }
}
