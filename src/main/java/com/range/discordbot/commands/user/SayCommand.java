package com.range.discordbot.commands.user;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class SayCommand {

    public void execute(SlashCommandInteractionEvent event)
    {
        String message = event.getOption("text").getAsString();
        if (message.isEmpty()){
        event.reply(message).queue(); // This requires no permissions!
    }
    }


}
