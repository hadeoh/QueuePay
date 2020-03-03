package com.decagon.queuepay.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends AuditModel{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(strategy = "org.hibernate.id.UUIDGenerator", name = "UUID")
    @Column(updatable = false, nullable = false, name = "id")
    private UUID id;

    @NotBlank(message = "Please fullname should not be empty")
    private String fullName;

    @NotBlank(message = "Please email should not be empty")
    @Column(unique = true)
    private String Email;

    @NotBlank(message = "Please password should not be empty")
    @Size(min = 8, max = 15)
    private String password;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
