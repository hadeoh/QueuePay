package com.decagon.queuepay.models.transaction;

import com.decagon.queuepay.models.AuditModel;
import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "walletId", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "businessId", nullable = false)
    private Business business;

    private String customerName;

    private CardType cardType;

    @NotNull
    private Double amount;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private TransactionType transactionType;
}
