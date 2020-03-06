package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.transaction.CardType;
import com.decagon.queuepay.models.transaction.TransactionType;

public class CustomerDto {

    private String customerName;
    private CardType cardType;
    private Double amount;
    private TransactionType transactionType = TransactionType.CREDIT;
}
