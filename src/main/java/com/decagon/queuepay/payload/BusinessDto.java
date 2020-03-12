package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.models.wallet.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessDto {

    @JsonIgnore
    private User user;

    @NotNull
    @NotBlank(message = "Please name is required")
    private String name;

    @NotNull
    @NotBlank(message = "Please logo url is required")
    private String logoUrl;

    @NotNull
    @NotBlank(message = "Please CAC Document Url is required")
    private String CACDocumentUrl;

    @NotNull
    @NotBlank(message = "Please description is required")
    @Column(columnDefinition = "text")
    private String description;

    @NotNull
    @NotNull(message = "Select a wallet type")
    private WalletType walletType;

    @NotNull
    @NotBlank(message = "Please pin is required")
    private String pin;

}
