package com.range.discordbot.listener;


import com.range.discordbot.repo.BannedUserRepo;
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
public class BannedUserJoinListener extends ListenerAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BannedUserRepo bannedUserRepo;
    @Value(value = "${discord.bot.banneduser.channelid}")
    private String channelid;

    public BannedUserJoinListener(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        log.info("onGuildMemberJoin");
        String membertag = event.getMember().getUser().getName();


        try {
            if (bannedUserRepo.existsBannedUserByName(membertag)) {
                Guild guild = event.getGuild();
                guild.ban(event.getUser(),0 ,TimeUnit.DAYS).queue();
                TextChannel channel = guild.getTextChannelById(channelid);
                channel.sendMessage("Banned User: " + event.getUser().getAsTag()+" trying join to server").queue();
            }
        }catch (Exception e){
            log.error("ban failed:"+e.getMessage());
        }
    }
}
