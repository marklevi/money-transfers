package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Entry {
    private final BigDecimal amount;
    private final LocalDate date;

    public Entry(BigDecimal amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
