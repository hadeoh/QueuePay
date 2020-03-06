package com.decagon.queuepay.service;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.models.transaction.TransactionStatus;
import com.decagon.queuepay.models.transaction.TransactionType;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.payload.Analytics;
import com.decagon.queuepay.payload.CashOut;
import com.decagon.queuepay.repositories.BankAccountRepository;
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
    public Transaction cashOut(Integer businessId, Integer walletId, CashOut cashOut) throws Exception {
        System.out.println(businessRepository.findAll());
        //Optional<BankAccount> bank = bankAccountRepository.findById(businessId);
        Optional<Wallet> wallet = walletRepository.findById(walletId);


        if ((wallet.get().getPin().equals(cashOut.getPin()))) {
            throw new Exception("Incorrect password");
        }
        //if (!(bank.get().getAccountNumber().equals(cashOut.getBankAccountNumber()))) { throw new Exception("Incorrect account number");}
        if (cashOut.getAmount() > wallet.get().getBalance()) {
            throw new Exception("Insufficient balance");
        }

        Optional<Business> business = businessRepository.findById(businessId);
        Double cashOutAmount = cashOut.getAmount();
        wallet.get().setBalance(wallet.get().getBalance() - cashOutAmount);
        wallet.get().setBusiness(business.get());
        Transaction transaction = new Transaction();
        transaction.setBusiness(business.get());
        transaction.setWallet(wallet.get());
        transaction.setAmount(cashOutAmount);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);

        return transactionRepository.save(transaction);
    }


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
                System.out.println("im here 1");
            } if (trans.getTransactionType().equals(TransactionType.CREDIT)) {
                accountBalance += trans.getAmount();
                System.out.println("im here2");
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



