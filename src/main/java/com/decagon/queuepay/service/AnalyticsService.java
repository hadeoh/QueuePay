package com.decagon.queuepay.service;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.models.transaction.TransactionStatus;
import com.decagon.queuepay.models.transaction.TransactionType;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.payload.Analytics;
import com.decagon.queuepay.repositories.BusinessRepository;
import com.decagon.queuepay.repositories.TransactionRepository;
import com.decagon.queuepay.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public Double totalWalletAmounts(Integer businessId){
        List<Wallet> wallets = walletRepository.findByBusinessId(businessId);
        Double balance = 0.0;
        for (Wallet wallet : wallets){
            balance += wallet.getBalance();
        }
        return balance;
    }

    public Analytics getAnalytics(Integer businessId) {
        List<Transaction> transactions = transactionRepository.findAllByBusinessId(businessId);
        List<Wallet> wallets = walletRepository.findByBusinessId(businessId);

        Integer transactionVolume = 0;
        Double value = 0.0;
        Double accountBalance = totalWalletAmounts(businessId);
        Integer successfulTransaction = 0;
        Integer failedTransaction = 0;
        Analytics analytics = new Analytics();
        if (!transactions.isEmpty()) {
            for (Transaction trans : transactions) {
                transactionVolume++;
                value += trans.getAmount();
                if (trans.getStatus().equals(TransactionStatus.SUCCESSFUL)) {
                    successfulTransaction++;
                } else if (trans.getStatus().equals(TransactionStatus.FAILED)) {
                    failedTransaction++;
                }
                if (trans.getTransactionType().equals(TransactionType.CREDIT)) {
                    accountBalance += trans.getAmount();
                }
            }
            analytics.setAccountBalance(accountBalance);
            analytics.setFailedTransaction(failedTransaction);
            analytics.setSuccessfulTransaction(successfulTransaction);
            analytics.setVolume(transactionVolume);
            analytics.setValue(value);
            analytics.setWallet(wallets);
            return analytics;
        }
        return null;
    }
}
