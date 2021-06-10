package com.sekarre.shop.service;


import com.sekarre.shop.dto.Purchase;
import com.sekarre.shop.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
