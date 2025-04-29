package com.range.discordbot.discordutils.commands.admin;

import com.range.discordbot.service.ServerInfoService;

public class SetLogChannel {
    private final ServerInfoService serverInfoService;
    public SetLogChannel(ServerInfoService serverInfoService) {
            this.serverInfoService = serverInfoService;
    }


}
