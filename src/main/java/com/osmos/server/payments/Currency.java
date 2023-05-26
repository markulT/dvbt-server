package com.osmos.server.payments;

public enum Currency {

    UAH("uah"),
    USD("usd");

    private final String currency;

    private Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
