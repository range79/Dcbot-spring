//package com.range.discordbot.service.impl;
//
//
//
//
//import com.range.discordbot.dao.model.RefreshToken;
//import com.range.discordbot.dao.repo.RefreshTokenRepo;
//import com.range.discordbot.security.util.JwtUtil;
//import com.range.discordbot.service.RefreshTokenService;
//
//import java.util.Date;
//import java.util.UUID;
//
//public class RefreshTokenServiceImpl implements RefreshTokenService {
//
//    private Long expiresIn;
//    private final RefreshTokenRepo refreshTokenRepo;
//    private final JwtUtil jwtUtil;
//    public RefreshTokenServiceImpl(RefreshTokenRepo refreshTokenRepo, JwtUtil jwtUtil) {
//        this.refreshTokenRepo = refreshTokenRepo;
//        this.jwtUtil = jwtUtil;
//    }
//    @Override
//    public String create(String username, String role) {
//      String jti = UUID.randomUUID().toString();
//      String token = jwtUtil.generateToken(username, role,jti);
//      RefreshToken refreshToken = new RefreshToken();
//      refreshToken.setToken(token);
//      refreshToken.setUsername(username);
//      refreshToken.setUsed(false);
//      refreshToken.setExpires(new Date(System.currentTimeMillis()+expiresIn*60*60*1000));
//    return token;
//    }
//
//
//
//    @Override
//    public boolean isValid(RefreshToken token) {
//        return false;
//    }
//
//    @Override
//    public void markAsUsed(RefreshToken token) {
//        token.setUsed(true);
//        refreshTokenRepo.save(token);
//    }
//
//    @Override
//    public void logout(String token) {
//
//    }
//}
