package com.range.discordbot.commands.user;

import com.range.discordbot.utils.BannerUtilKt;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;




@Component
public class InfoCommand {
    private final Logger log = LoggerFactory.getLogger(InfoCommand.class);
    @Value("${app.version}")
    private String appVersion;
    private final String repo= "https://github.com/range79";
    private final String banner = "  ______             _     _       \n" +
            " / _____)           (_)   | |      \n" +
            "( (____  ____   ____ _  __| |_____ \n" +
            " \\____ \\|  _ \\ / ___) |/ _  (____ |\n" +
            " _____) ) |_| | |   | ( (_| / ___ |\n" +
            "(______/|  __/|_|   |_|\\____\\_____|\n" +
            "        |_|                        ";

    public void execute(SlashCommandInteractionEvent event)

    {
        String bannerMessage = "```" + banner + "\nCurrent Version: " + appVersion + "```";
        String description = """
        Sprida is a bot built with Spring and JDA (Java Discord API) designed to bring a variety of helpful features to your Discord server.
        🚀 Using Spring for backend development and JDA for smooth Discord integration, Sprida allows seamless interactions that enhance the server experience.
        🤖 We welcome you to explore the project on GitHub and contribute to its growth.
        👉 GitHub: %s
        """.formatted(repo);
        event.reply(bannerMessage + "\n" + description)
                .queue();
    }
}
