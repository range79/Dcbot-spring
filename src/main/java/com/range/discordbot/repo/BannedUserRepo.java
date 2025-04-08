package com.range.discordbot.repo;

import com.range.discordbot.model.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannedUserRepo extends JpaRepository<BannedUser,Long> {
    void deleteBannedUserByTag(String tag);

   Optional<BannedUser>  findByTag(String tag);

    void deleteBannedUserById(Long id);

    boolean findByName(String name);

    boolean existsBannedUserByTag(String tag);

    boolean existsBannedUserByName(String name);
}
