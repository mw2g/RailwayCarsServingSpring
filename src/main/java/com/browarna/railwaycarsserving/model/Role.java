package com.browarna.railwaycarsserving.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_WATCH,
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return name();
    }
    
}
