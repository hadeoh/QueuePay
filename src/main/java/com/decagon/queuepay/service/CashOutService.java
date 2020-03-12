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
import java.util.List;
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
        Business business = businessRepository.findById(businessId).orElse(null);
        if (business != null){
            Wallet wallet = walletRepository.findById(walletId).orElse(null);
            if (wallet != null){
                if (wallet.getBusiness().getId().equals(business.getId())){
                    if (!wallet.getPin().equals(cashOut.getPin())) {
                        throw new Exception("Incorrect pin");
                    }
                    if (cashOut.getAmount() > wallet.getBalance()) {
                        throw new Exception("Insufficient balance");
                    }
                    Double cashOutAmount = cashOut.getAmount();
                    wallet.setBalance(wallet.getBalance() - cashOutAmount);
                    wallet.setBusiness(business) ;
                    walletRepository.save(wallet);

                    Transaction transaction = new Transaction();
                    transaction.setBusiness(business);
                    transaction.setWallet(wallet);
                    transaction.setAmount(cashOutAmount);
                    transaction.setTransactionType(TransactionType.DEBIT);
                    transaction.setStatus(TransactionStatus.SUCCESSFUL);

                    transactionRepository.save(transaction);
                    return "Successful";
                }
            }
            throw new Exception("Wallet not available");
        }
        throw new Exception("Business not available");
    }
}