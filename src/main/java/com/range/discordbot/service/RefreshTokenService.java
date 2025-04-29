package com.range.discordbot.service;

import com.range.discordbot.dao.model.RefreshToken;

public interface RefreshTokenService {

    String create(String username,String role);
    boolean isValid(RefreshToken token);
    void markAsUsed(RefreshToken token);
    void logout(String token);
}
