package com.decagon.queuepay.payload;

public class Analytics {
    private int volume;
    private Double value;
    private Integer successfulTransaction;
    private Integer failedTransaction;
    private Double accountBalance;

    public int getVolume(int transactionVolume) {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getSuccessfulTransaction() {
        return successfulTransaction;
    }

    public void setSuccessfulTransaction(Integer successfulTransaction) {
        this.successfulTransaction = successfulTransaction;
    }

    public Integer getFailedTransaction() {
        return failedTransaction;
    }

    public void setFailedTransaction(Integer failedTransaction) {
        this.failedTransaction = failedTransaction;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
