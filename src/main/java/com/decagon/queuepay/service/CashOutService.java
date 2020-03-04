package com.decagon.queuepay.service;

import com.decagon.queuepay.models.BankAccount;
import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.CashOut;
import com.decagon.queuepay.models.transaction.CardType;
import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.models.transaction.TransactionStatus;
import com.decagon.queuepay.models.transaction.TransactionType;
import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Service
public class CashOutService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private WalletRepository walletRepository;


    public Wallet updateWalletBalance(Double withdrawAmount, UUID walletId){
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        wallet.get().setBalance(wallet.get().getBalance() - withdrawAmount);
        return walletRepository.save(wallet.get());
    }

    /*public String hashedPassword (String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }*/


    public Transaction businessCashOut(
            @PathVariable(value = "businessId") UUID businessId,
            @PathVariable(value = "walletId") UUID walletId,
            @Valid @RequestBody CashOut cashOut){

        Transaction transaction = null;
        Optional<Business> business = businessRepository.findById(businessId);
        Optional<User> user = userRepository.findById(business.get().getUser().getId());
        Optional<BankAccount> bank = Optional.ofNullable(bankAccountRepository.findByBusinessId(businessId));
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (user.get().getPassword().equals(cashOut.getPassword())){
            if(bank.get().getAccountNumber().equals(cashOut.getBankAccountName())) {
                if(cashOut.getAmount() <= wallet.get().getBalance()) {
                    transaction.setCustomerName("SELF");
                    transaction.setCardType(CardType.MASTERCARD);
                    transaction.setAmount(cashOut.getAmount());
                    transaction.setTransactionType(TransactionType.DEBIT);
                    transaction.setStatus(TransactionStatus.SUCCESSFUL);

                    updateWalletBalance(cashOut.getAmount(), walletId);
                }
            }
        }
        return transactionRepository.save(transaction);
    }

}
