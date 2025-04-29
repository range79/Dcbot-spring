package com.range.discordbot.discordutils.listener;

import com.range.discordbot.dao.model.BannedUser;
import com.range.discordbot.dao.repo.BannedUserRepo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AutoUserBanner extends ListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BannedUserRepo bannedUserRepo;

    @Value("${discord.bot.log.channel_id}")
    private String channelId;

    public AutoUserBanner(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String memberTag = event.getUser().getName();

        try {
            if (bannedUserRepo.existsBannedUserByTag(memberTag)) {
                BannedUser bannedUser = bannedUserRepo.findBannedUserByTag(memberTag);

                //is user banned?
                if (bannedUser.getServerId().contains(event.getGuild().getId())) {
                    Guild guild = event.getGuild();
                    guild.ban(event.getUser(), 0, TimeUnit.DAYS).queue();

                    TextChannel logChannel = guild.getTextChannelById(channelId);
                    if (logChannel != null) {
                        logChannel.sendMessage("❌ Banned user tried to join: " + memberTag).queue();
                    }
                    log.warn("Banned user tried to join: {}", memberTag);
                }
            }
        } catch (Exception e) {
            log.error("❗ Failed to auto-ban user '{}': {}", memberTag, e.getMessage());
        }
    }
}
