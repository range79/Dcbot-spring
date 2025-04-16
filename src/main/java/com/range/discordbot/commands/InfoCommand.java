package com.range.discordbot.commands;

import com.range.discordbot.utils.BannerUtilKt;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;




@Component
public class InfoCommand {
    private Logger log = LoggerFactory.getLogger(InfoCommand.class);
    @Value("${app.version}")
    private String appVersion;
    private final String repo= "https://github.com/range79";

    public void execute(SlashCommandInteractionEvent event)

    {
        String bannerMessage = "```" + BannerUtilKt.getBanner() + "\nCurrent Version: " + appVersion + "```";
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
