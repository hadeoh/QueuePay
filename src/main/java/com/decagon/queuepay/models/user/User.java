package com.decagon.queuepay.models.user;

import com.decagon.queuepay.models.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User extends AuditModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, name = "id")
    private UUID id;

    @NotBlank(message = "Please name should not be empty")
    private String fullName;

    @NotBlank(message = "Please phone number should not be empty")
    private String phoneNumber;

    @NotBlank(message = "Please email should not be empty")
    private String email;

    @NotBlank(message = "Please password should not be empty")
    @JsonIgnoreProperties
    @Size(min = 6)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    List<Role> roles;

    @JsonIgnore
    private String emailVerificationToken;

    @JsonIgnore
    private EmailVerificationStatus emailVerificationStatus = EmailVerificationStatus.UNVERIFIED;
}
