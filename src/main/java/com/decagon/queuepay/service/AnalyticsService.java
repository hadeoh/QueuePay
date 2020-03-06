package com.decagon.queuepay.service;

import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.models.transaction.TransactionStatus;
import com.decagon.queuepay.models.transaction.TransactionType;
import com.decagon.queuepay.payload.Analytics;
import com.decagon.queuepay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Analytics getAnalytics(Integer businessId) {
        List<Transaction> transactions = transactionRepository.findAllByBusinessId(businessId);
        Integer transactionVolume = 0;
        Double value = 0.0;
        Double accountBalance = 0.0;
        Integer successfulTransaction = 0;
        Integer failedTransaction = 0;
        Analytics analytics = new Analytics();

        for (Transaction trans : transactions) {
            transactionVolume++;
            value += trans.getAmount();
            if (trans.getStatus().equals(TransactionStatus.SUCCESSFUL)) {
                successfulTransaction++;
            } else if (trans.getStatus().equals(TransactionStatus.FAILED)) {
                failedTransaction++;
            } if (trans.getTransactionType().equals(TransactionType.CREDIT)) {
                accountBalance += trans.getAmount();
            } else if (trans.getTransactionType().equals(TransactionType.DEBIT)) {
                accountBalance -= trans.getAmount();
            }
        }

        analytics.setAccountBalance(accountBalance);
        analytics.setFailedTransaction(failedTransaction);
        analytics.setSuccessfulTransaction(successfulTransaction);
        analytics.setVolume(transactionVolume);
        analytics.setValue(value);
        return analytics;
    }
}
