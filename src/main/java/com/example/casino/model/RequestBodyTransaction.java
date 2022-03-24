package com.example.casino.model;

public class RequestBodyTransaction {
    Long id;
    double amount;
    String promoCode;

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getPromoCode() {
        return promoCode;
    }
}
