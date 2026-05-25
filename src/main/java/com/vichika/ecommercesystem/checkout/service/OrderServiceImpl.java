package com.vichika.ecommercesystem.checkout.service;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import com.vichika.ecommercesystem.cart.repository.CartRepository;
import com.vichika.ecommercesystem.checkout.OrderMapper;
import com.vichika.ecommercesystem.checkout.dto.OrderItemResponse;
import com.vichika.ecommercesystem.checkout.dto.OrderResponse;
import com.vichika.ecommercesystem.checkout.model.Order;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import com.vichika.ecommercesystem.checkout.model.OrderStatus;
import com.vichika.ecommercesystem.checkout.repository.OrderItemRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderRepository;
import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.ProductRepository;
import com.vichika.ecommercesystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final AuthUtil authUtil;

    @Override
    public OrderResponse checkout() {

        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        BigDecimal totalAmount = getTotalAmounts(cartItems);

        var order = Order.builder()
                .orderNumber(generateOrderNumber())
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        for (var items : cartItems){

            var product = items.getProduct();

            if (items.getQuantity() > product.getQuantity()){
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }
            BigDecimal subTotal = product.getFinalPrice()
                    .multiply(BigDecimal.valueOf(items.getQuantity()));

            var orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .quantity(items.getQuantity())
                    .subTotal(subTotal)
                    .build();
            orderItemRepository.save(orderItem);

            product.setQuantity(product.getQuantity() - items.getQuantity());
            productRepository.save(product);
        }
        cartItemRepository.deleteAll(cartItems);

        return buildOrderResponse(order);
    }

    private Cart getCartUser(AppUser user){
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private OrderResponse buildOrderResponse(Order order){
        List<OrderItemResponse> items = orderItemRepository.findByOrder(order)
                .stream()
                .map(orderMapper::toOrderItemResponse)
                .toList();
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                items
        );
    }

    private BigDecimal getTotalAmounts(List<CartItem> cartItems){
        return cartItems.stream()
                .map(item -> item.getProduct()
                        .getFinalPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
