package com.vichika.ecommercesystem.email;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.model.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Async
    @Override
    public void sendWelcomeEmail(AppUser user) {

        String html = """
                <h2>Welcome to Ecommerce System</h2>
                <p>Hello %s,</p>
                <p>Your account has been created successfully.</p>
                """
                .formatted(user.getUsername());

        sendHtmlEmail(user.getEmail(), "Welcome", html);
    }

    @Async
    @Override
    public void sendOrderPlacedEmail(Order order) {

        String html = """
                <h2>Order Created Successfully</h2>

                <p>Order Number: <b>%s</b></p>

                <p>Total Amount: <b>$%s</b></p>

                <p>Status: <b>%s</b></p>
                """
                .formatted(order.getOrderNumber(), order.getTotalAmount(), order.getStatus());

        sendHtmlEmail(order.getUser().getEmail(), "Order Created", html);
    }

    @Async
    @Override
    public void sendPaymentSuccessEmail(Order order) {

        String html = """
                <h2>Payment Successful</h2>

                <p>Order Number: <b>%s</b></p>

                <p>Your payment has been received.</p>
                """
                .formatted(order.getOrderNumber());

        sendHtmlEmail(order.getUser().getEmail(), "Payment Successful", html);
    }

    private void sendHtmlEmail(String recipient, String subject, String html) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(mimeMessage);

            log.info("Email sent to {}", recipient);

        } catch (MessagingException e) {
            log.error("Email send failed", e);
        }
    }
}
