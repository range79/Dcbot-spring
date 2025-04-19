package com.range.discordbot.service.impl;

import com.range.discordbot.exception.BannedUserNotFoundException;
import com.range.discordbot.model.BannedUser;
import com.range.discordbot.repo.BannedUserRepo;
import com.range.discordbot.service.BannedUserService;
import com.range.discordbot.utils.DiscordGuildUtil;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BannedUserServiceImpl implements BannedUserService {
    private final BannedUserRepo bannedUserRepo;
    private final DiscordGuildUtil discordGuildUtil;
    private final Logger log = LoggerFactory.getLogger(BannedUserServiceImpl.class);

    public BannedUserServiceImpl(BannedUserRepo bannedUserRepo,@Lazy DiscordGuildUtil discordGuildUtil) {
        this.bannedUserRepo = bannedUserRepo;
        this.discordGuildUtil = discordGuildUtil;

    }

    @Override
    public void unbanUser(String tag) {
        if (!bannedUserRepo.existsBannedUserByTag(tag)){
            throw new BannedUserNotFoundException("Banned user with tag " + tag + " does not exist");
        }
        bannedUserRepo.deleteBannedUserByTag(tag);

    }


    @Override
    public List<BannedUser> bannedUsers() {
        return bannedUserRepo.findAll();
    }

    public BannedUser banUser(String user, String reason) {
        Member member =discordGuildUtil.getUserById(user);
        log.info("Banned user tag: {}", user);
        LocalDateTime bannedAt = LocalDateTime.now();

        BannedUser bannedUser= new BannedUser(bannedAt, reason, member.getUser().getEffectiveName(),member.getUser().getAsTag());
        try {
            member.getGuild().ban(member, 0, TimeUnit.DAYS).queue(); // 0, ban time == infinite ban time
            log.info("User {} has been banned for reason: {}", user, reason);
            bannedUserRepo.save(bannedUser);
            return bannedUser;
        } catch (Exception e) {
            log.error("Failed to ban user: {}", user);
            return null;
        }
    }

    public boolean isBanned(String tag) {
        return bannedUserRepo.existsBannedUserByTag(tag);
    }
}
