package com.range.discordbot.discordutils.commands.admin;

import com.range.discordbot.service.ServerInfoService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SetLogChannel {
    private final ServerInfoService serverInfoService;
    public SetLogChannel(ServerInfoService serverInfoService) {
        this.serverInfoService = serverInfoService;
    }
    public void execute(SlashCommandInteractionEvent event) {

    }

}
