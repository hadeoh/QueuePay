package com.decagon.queuepay.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessRegDto {
    private BusinessDto businessDto;
    private WalletDto walletDto;


    public BusinessRegDto(BusinessDto businessDto, WalletDto walletDto) {
        this.businessDto = businessDto;
        this.walletDto = walletDto;
    }

    public BusinessDto getBusinessDto() {
        return businessDto;
    }

    public void setBusinessDto(BusinessDto businessDto) {
        this.businessDto = businessDto;
    }

    public WalletDto getWalletDto() {
        return walletDto;
    }

    public void setWalletDto(WalletDto walletDto) {
        this.walletDto = walletDto;
    }
}
