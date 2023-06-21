package com.example.accountingproject.enums;

public enum InvoiceType {
    PURCHASE("Purchase"),SALES("Sales");

    private final String value;


    InvoiceType(String value) {
        this.value = value;
    }
}
