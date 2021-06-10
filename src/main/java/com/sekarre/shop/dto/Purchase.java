package com.sekarre.shop.dto;

import com.sekarre.shop.entity.Address;
import com.sekarre.shop.entity.Customer;
import com.sekarre.shop.entity.Order;
import com.sekarre.shop.entity.OrderItem;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems = new HashSet<>();

}
