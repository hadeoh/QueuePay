package com.decagon.queuepay.service;

import com.decagon.queuepay.dto.Analytics;
import com.decagon.queuepay.dto.CashOut;
import com.decagon.queuepay.models.BankAccount;
import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.models.transaction.TransactionStatus;
import com.decagon.queuepay.models.transaction.TransactionType;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class CashOutService {

    private TransactionRepository transactionRepository;
    private BankAccountRepository bankAccountRepository;
    private WalletRepository walletRepository;
    private BusinessRepository businessRepository;

    @Autowired
    public void transactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void bankAccountRepository(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Autowired
    public void walletRepository(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Autowired
    public void businessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Transactional
    public Transaction cashOut(UUID businessId, UUID walletId, CashOut cashOut) throws Exception {

        Optional<BankAccount> bank = Optional.ofNullable(bankAccountRepository.findByBusinessId(businessId));
        Optional<Wallet> wallet = walletRepository.findById(walletId);


        if (!(wallet.get().getPin().equals(cashOut.getPin()))) { throw new Exception("Incorrect password"); }
        if (!(bank.get().getAccountNumber().equals(cashOut.getBankAccountName()))) { throw new Exception("Incorrect account number");}
        if (cashOut.getAmount() > wallet.get().getBalance()) { throw new Exception("Insufficient balance");}

        Business business = businessRepository.getOne(businessId);
        Double cashOutAmount = cashOut.getAmount();
        wallet.get().setBalance(wallet.get().getBalance() - cashOutAmount);
        wallet.get().setBusiness(business);
        Transaction transaction = new Transaction();
        transaction.setBusiness(business);
        transaction.setWallet(wallet.get());
        transaction.setAmount(cashOutAmount);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);

        return transactionRepository.save(transaction);
    }


    public Analytics getAnalytics(UUID businessId, UUID walletId){
        Transaction transactions = transactionRepository.findAllByBusinessId(businessId);
        int transactionVolume = transactions.toString().length();
        AtomicReference<Double> value = new AtomicReference<>(0.0);
        AtomicReference<Double> accountBalance = new AtomicReference<>(0.0);
        AtomicInteger successfulTransaction = new AtomicInteger();
        AtomicInteger failedTransaction = new AtomicInteger();
        Analytics analytics = new Analytics();

        Stream.of(transactions).filter(transaction->
        {
            if(transaction.getStatus().equals("SUCCESSFUL")){
                successfulTransaction.getAndIncrement();
            }
            else if(transaction.getStatus().equals("FAILED")){
                failedTransaction.getAndIncrement();
            }
            else if(transaction.getTransactionType().equals("CREDIT")){
                accountBalance.updateAndGet(v -> v + transaction.getAmount());
                value.updateAndGet(v -> new Double((double) (v + transaction.getAmount())));
            }
            else if(transaction.getTransactionType().equals("DEBIT")){
                accountBalance.updateAndGet(v -> v - transaction.getAmount());
                value.updateAndGet(v -> new Double((double) (v + transaction.getAmount())));
            }
            return true;
        });

        analytics.setAccountBalance(accountBalance);
        analytics.setFailedTransaction(failedTransaction);
        analytics.setSuccessfulTransaction(successfulTransaction);
        analytics.setVolume(transactionVolume);
        analytics.setValue(value);
        return analytics;

    }
}


/*public String hashedPassword (String pa.ssword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }*/