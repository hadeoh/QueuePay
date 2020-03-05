package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

public class BusinessDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String logoUrl;

    @NotNull
    @NotBlank
    private String CACDocumentUrl;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "text")
    private String description;

    public BusinessDto(@NotNull @NotBlank String name, @NotNull @NotBlank String logoUrl, @NotNull @NotBlank String CACDocumentUrl, @NotNull @NotBlank String description) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.CACDocumentUrl = CACDocumentUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCACDocumentUrl() {
        return CACDocumentUrl;
    }

    public void setCACDocumentUrl(String CACDocumentUrl) {
        this.CACDocumentUrl = CACDocumentUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
