package com.decagon.queuepay.service;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.models.transaction.TransactionStatus;
import com.decagon.queuepay.models.transaction.TransactionType;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.payload.CashOut;
import com.decagon.queuepay.repositories.BusinessRepository;
import com.decagon.queuepay.repositories.TransactionRepository;
import com.decagon.queuepay.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class CashOutService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private BusinessRepository businessRepository;


    @Transactional
    public String cashOut(Integer businessId, Integer walletId, CashOut cashOut) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isPresent()) {
            if (!wallet.get().getPin().equals(cashOut.getPin())) {
                throw new Exception("Incorrect pin");
            }
            if (cashOut.getAmount() > wallet.get().getBalance()) {
                throw new Exception("Insufficient balance");
            }

            Optional<Business> business = businessRepository.findById(businessId);
            Double cashOutAmount = cashOut.getAmount();
            wallet.get().setBalance(wallet.get().getBalance() - cashOutAmount);
            wallet.get().setBusiness(business.get());
            walletRepository.save(wallet.get());
            Transaction transaction = new Transaction();
            transaction.setBusiness(business.get());
            transaction.setWallet(wallet.get());
            transaction.setAmount(cashOutAmount);
            transaction.setTransactionType(TransactionType.DEBIT);
            transaction.setStatus(TransactionStatus.SUCCESSFUL);

            transactionRepository.save(transaction);
            return "Successful";
        } else {
            throw new Exception("Wallet not present");
        }
    }
}