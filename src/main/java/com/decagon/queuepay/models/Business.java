package com.decagon.queuepay.models;

import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.models.wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "businesses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Business extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private User user;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String logoUrl;

    @NotNull
    @NotBlank
    private String cacDocumentUrl;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "text")
    private String description;

    private String businessOwner;

}
