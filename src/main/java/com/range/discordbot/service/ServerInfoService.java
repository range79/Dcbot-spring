package com.range.discordbot.service;

import com.range.discordbot.dao.model.ServerInfo;
import org.apache.catalina.Server;

import java.util.List;

/**
 * Handles the registration and removal of other Discord servers
 * when {@code discord.bot.allow-connect-other-servers} is set to {@code true}.
 *
 * <p>This service allows the bot to register, update, and remove
 * external servers dynamically based on events like joining or leaving a server.</p>
 *
 * <p>If the property is set to {@code false}, server operations are not performed
 * and the bot immediately leaves unauthorized servers.</p>
 */

public interface ServerInfoService {

    /**
     * @param serverInfo
     * save server info to database
     */
    ServerInfo registerServer(String serverId);

;

    /**
     * @param serverId
     * @return
     * get server info with id
     */
    ServerInfo getServerInfoByServerId(String serverId);

    List<ServerInfo> getAllServers();




}
