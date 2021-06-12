package com.sekarre.shop.service;

import com.sekarre.shop.dto.Purchase;
import com.sekarre.shop.dto.PurchaseResponse;
import com.sekarre.shop.entity.Customer;
import com.sekarre.shop.entity.Order;
import com.sekarre.shop.entity.OrderItem;
import com.sekarre.shop.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);


        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();

        //check for existing customer in db
        String email = customer.getEmail();

        Customer customerDb = customerRepository.findByEmail(email);

        if (customerDb != null) {
            customer = customerDb;
        }

        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
