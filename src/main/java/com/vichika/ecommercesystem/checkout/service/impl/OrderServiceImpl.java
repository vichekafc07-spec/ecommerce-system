package com.vichika.ecommercesystem.checkout.service.impl;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import com.vichika.ecommercesystem.cart.repository.CartRepository;
import com.vichika.ecommercesystem.checkout.dto.request.CheckoutRequest;
import com.vichika.ecommercesystem.checkout.mapper.OrderMapper;
import com.vichika.ecommercesystem.checkout.dto.response.OrderItemResponse;
import com.vichika.ecommercesystem.checkout.dto.response.OrderResponse;
import com.vichika.ecommercesystem.checkout.model.Address;
import com.vichika.ecommercesystem.checkout.model.Order;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import com.vichika.ecommercesystem.checkout.model.OrderStatus;
import com.vichika.ecommercesystem.checkout.repository.AddressRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderItemRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderRepository;
import com.vichika.ecommercesystem.checkout.service.OrderService;
import com.vichika.ecommercesystem.email.EmailService;
import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.repository.ProductRepository;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final AuthUtil authUtil;
    private final AddressRepository addressRepository;
    private final EmailService emailService;

    @Override
    public OrderResponse checkout(CheckoutRequest request) {

        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);
        var address = getAddressOrder(request.addressId(), user);
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
                .address(address)
                .receiverName(address.getFullName())
                .receiverPhone(address.getPhoneNumber())
                .province(address.getProvince())
                .city(address.getCity())
                .street(address.getStreet())
                .build();
        orderRepository.save(order);

        emailService.sendOrderPlacedEmail(order);

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

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {
        var user = authUtil.getCurrentUser();
        return orderRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {

        var user = authUtil.getCurrentUser();
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        if (!order.getUser().getId().equals(user.getId())){
            throw new BadRequestException("You cannot access this order");
        }

        return buildOrderResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {

        var user = authUtil.getCurrentUser();
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())){
            throw new BadRequestException("You cannot access this order");
        }

        if (order.getStatus() == OrderStatus.CANCELLED){
            throw new BadRequestException("Order already cancelled");
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

        for (var item : orderItems){
            var product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return buildOrderResponse(order);
    }

    private Cart getCartUser(AppUser user){
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private Address getAddressOrder(Long addressId,AppUser user){
        return addressRepository.findByIdAndUser(addressId,user)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
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
