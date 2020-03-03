package com.decagon.queuepay.models.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT, ROLE_STAFF;

    public String getAuthority() {
        return name();
    }
}
