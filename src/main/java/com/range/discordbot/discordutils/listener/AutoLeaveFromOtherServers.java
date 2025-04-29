package com.range.discordbot.discordutils.listener;

import com.range.discordbot.dao.repo.ServerInfoRepo;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AutoLeaveFromOtherServers extends ListenerAdapter {
    private final Logger log = LoggerFactory.getLogger(AutoLeaveFromOtherServers.class);
    @Value("${discord.bot.allow-connect-other-servers}")
    private boolean allowConnectOtherServers;
    @Value("${discord.bot.server_id}")
    private String serverId;



    /**
     * this method checks application yaml or properties if
     * discord.botQ.allow-connect-other-servers if false
     * other users can't add this bot to other servers
     */
    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {

        if(!allowConnectOtherServers && !serverId.equals(event.getGuild().getId())) {

            event.getGuild().leave().queue(success -> log.info("Successfully leaved server {} from other servers", serverId),
                    error-> log.error("leave failed from other servers", error));}

    }


}

