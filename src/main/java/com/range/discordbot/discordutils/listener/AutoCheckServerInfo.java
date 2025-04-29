package com.range.discordbot.discordutils.listener;

import com.range.discordbot.dao.model.ServerInfo;
import com.range.discordbot.dao.repo.ServerInfoRepo;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AutoCheckServerInfo extends ListenerAdapter {
   private final Logger log = LoggerFactory.getLogger(AutoCheckServerInfo.class);
   private final ServerInfoRepo serverInfoRepo;
   @Value("${discord.bot.server_id}")
   private  String serverId;
   public AutoCheckServerInfo(ServerInfoRepo serverInfoRepo) {
       this.serverInfoRepo = serverInfoRepo;
   }
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Optional<ServerInfo> findServerInfo =  serverInfoRepo.findServerInfoByServerId(event.getGuild().getId());
        if (findServerInfo.isEmpty()&&!serverId.equals(event.getGuild().getId())) {
            ServerInfo serverInfo = ServerInfo.builder()
                    .serverName(event.getGuild().getName())
                    .serverId(event.getGuild().getId())
                    .serverPhotoUrl(event.getGuild().getIconUrl())
                    .isBanned(false)
                    .isRegistered(false)
                    .build();
            serverInfoRepo.save(serverInfo);
            event.getGuild().leave().queue(success -> log.info("Server not registered in database {}", event.getGuild().getId()),
            error->log.error("leaving failed from server {} ", event.getGuild().getId(), error));
        }
    }
}
