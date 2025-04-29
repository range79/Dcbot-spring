package com.range.discordbot.dao.repo;

import com.range.discordbot.dao.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
