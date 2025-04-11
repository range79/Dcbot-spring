package com.range.discordbot.service;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class InfoCommand {
    public void execute(SlashCommandInteractionEvent event)
    {
        //todo   l get version info from gradle on next version
//        String version = System.getProperty("app.version");
        event.reply("✨ Sprida is a bot built with Spring and JDA (Java Discord API) designed to bring a variety of helpful features to your Discord server. " +
                "🚀 Using Spring for backend development and JDA for smooth Discord integration, Sprida allows seamless interactions that enhance the server experience. " +
                "🤖 We welcome you to explore the project on GitHub and contribute to its growth. " +
                "Feel free to check it out and be part of the journey here: https://github.com/range79 🌟 " +
//                "Current Version: " + version

        ).queue();
    }
}
