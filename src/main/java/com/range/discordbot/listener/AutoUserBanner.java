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
public class AutoUserBanner extends ListenerAdapter {

   /*
    This class is designed to automatically ban a user if they have been previously banned,
    and someone attempts to invite them to the server. It listens for attempts to invite banned users
    and triggers a ban action, ensuring that banned users cannot rejoin or interact with the server.
    This helps maintain the integrity of the server's community by preventing banned users from bypassing
    their ban through invitations.
*/

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BannedUserRepo bannedUserRepo;
    @Value(value = "${discord.bot.log.channel_id}")
    private String channelid;

    public AutoUserBanner(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }

    /**

     * @param event
     */
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {


        String membertag = event.getMember().getUser().getName();


        try {
            if (bannedUserRepo.existsBannedUserByTag(membertag)) {
                Guild guild = event.getGuild();
                guild.ban(event.getUser(),0 ,TimeUnit.DAYS).queue();
                TextChannel channel = guild.getTextChannelById(channelid);
                channel.sendMessage("Banned User: " + event.getUser().getAsTag()+" trying join to server").queue();
                log.warn("Banned User: " + event.getUser().getAsTag()+" trying join to server");
            }
        }catch (Exception e){
            log.error("ban failed:"+e.getMessage());
        }
    }
}
