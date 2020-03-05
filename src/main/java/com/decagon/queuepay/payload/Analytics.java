package com.decagon.queuepay.payload;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Analytics {
    private int volume;
    private AtomicReference<Double> value;
    private AtomicInteger successfulTransaction;
    private AtomicInteger failedTransaction;
    private AtomicReference<Double> accountBalance;

    public int getVolume(int transactionVolume) {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public AtomicReference<Double> getValue() {
        return value;
    }

    public void setValue(AtomicReference<Double> value) {
        this.value = value;
    }

    public AtomicInteger getSuccessfulTransaction() {
        return successfulTransaction;
    }

    public void setSuccessfulTransaction(AtomicInteger successfulTransaction) {
        this.successfulTransaction = successfulTransaction;
    }

    public AtomicInteger getFailedTransaction() {
        return failedTransaction;
    }

    public void setFailedTransaction(AtomicInteger failedTransaction) {
        this.failedTransaction = failedTransaction;
    }

    public AtomicReference<Double> getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(AtomicReference<Double> accountBalance) {
        this.accountBalance = accountBalance;
    }
}
