package com.range.discordbot.discordutils.commands.configuration;

import com.range.discordbot.discordutils.commands.admin.PruneCommand;
import com.range.discordbot.discordutils.commands.admin.RegisterServerCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlashCommands extends ListenerAdapter {
    private final SlashCommandRouter router;
    private final RegisterServerCommand registerServerCommand;
    private final PruneCommand pruneCommand;

    public SlashCommands(SlashCommandRouter router, RegisterServerCommand registerServerCommand, PruneCommand pruneCommand) {
        this.router = router;
        this.registerServerCommand = registerServerCommand;
        this.pruneCommand = pruneCommand;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        router.route(event);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        if (buttonId == null) return;

        if (buttonId.startsWith("confirm_register") || buttonId.startsWith("cancel_register")) {
            registerServerCommand.handleButtonInteraction(event);
        } else if (buttonId.contains(":prune") || buttonId.contains(":delete")) {
            pruneCommand.handleButtonInteraction(event);
        }
    }
}
