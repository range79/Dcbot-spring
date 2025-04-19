package com.range.discordbot.commands.user;


import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;




@Component
public class InfoCommand {

    @Value("${app.version}")
    private String appVersion;

    public void execute(SlashCommandInteractionEvent event)

    {
        String banner = """
                  ______             _     _      \s
                 / _____)           (_)   | |     \s
                ( (____  ____   ____ _  __| |_____\s
                 \\____ \\|  _ \\ / ___) |/ _  (____ |
                 _____) ) |_| | |   | ( (_| / ___ |
                (______/|  __/|_|   |_|\\____\\_____|
                        |_|                       \s""";
        String bannerMessage = "```" + banner + "\nCurrent Version: " + appVersion + "```";
        String description = getString();
        event.reply(bannerMessage + "\n" + description)
                .queue();
    }

    @NotNull
    private static String getString() {
        String repo = "https://github.com/range79";
        return """
        Sprida is a bot built with Spring and JDA (Java Discord API) designed to bring a variety of helpful features to your Discord server.
        🚀 Using Spring for backend development and JDA for smooth Discord integration, Sprida allows seamless interactions that enhance the server experience.
        🤖 We welcome you to explore the project on GitHub and contribute to its growth.
        👉 GitHub: %s
        """.formatted(repo);
    }
}
