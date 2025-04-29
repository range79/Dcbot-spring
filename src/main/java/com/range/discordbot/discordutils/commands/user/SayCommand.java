package com.range.discordbot.discordutils.commands.user;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SayCommand {

    public void execute(SlashCommandInteractionEvent event)
    {
        String message = Objects.requireNonNull(event.getOption("content")).getAsString();
        if (!message.isEmpty()){
        event.reply(message).queue(); // This requires no permissions!
    }
    }
}
