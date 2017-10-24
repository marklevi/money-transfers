package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Entry {
    private final BigDecimal amount;
    private final LocalDate date;

    public Entry(BigDecimal amount) {
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
