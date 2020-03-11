package com.decagon.queuepay.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashOut {

    @NotNull
    private Double amount;

    @NotNull
    @NotBlank(message = "Please provide your wallet pin")
    private String pin;

    @NotNull
    @NotBlank(message = "Please provide a bank name")
    private String bankName;

    @NotNull
    @NotBlank(message = "Please provide a bank account number")
    private String bankAccountNumber;
}

