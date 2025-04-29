package com.range.discordbot.dao.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,ROLE_USER,ROLE_MODERATOR;
    @Override
    public String getAuthority() {
        return name();
    }
}
