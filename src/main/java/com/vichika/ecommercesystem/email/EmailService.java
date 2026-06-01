package com.vichika.ecommercesystem.email;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.model.Order;

public interface EmailService {

    void sendWelcomeEmail(AppUser user);

    void sendOrderPlacedEmail(Order order);

    void sendPaymentSuccessEmail(Order order);

}
