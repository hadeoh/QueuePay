package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

public class WalletDto {
    @NotNull
    private WalletType walletType;

    @NotNull
    @NotBlank
    private String pin;

    public WalletDto(@NotNull WalletType walletType, @NotNull @NotBlank String pin) {
        this.walletType = walletType;
        this.pin = pin;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
