package com.range.discordbot.service;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class SayCommand {

    public void execute(SlashCommandInteractionEvent event, String content)
    {
        event.reply(content).queue(); // This requires no permissions!
    }
}
