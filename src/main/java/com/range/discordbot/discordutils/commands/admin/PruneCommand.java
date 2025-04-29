package com.range.discordbot.discordutils.commands.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PruneCommand {
    private final Logger log = LoggerFactory.getLogger(PruneCommand.class);

    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            log.warn("User {} tried to prune messages without permission.", event.getUser().getName());
            event.reply("❌ You don't have permission to manage messages.").setEphemeral(true).queue();
            return;
        }

        OptionMapping amountOption = event.getOption("amount");
        int amount = amountOption == null
                ? 100
                : (int) Math.min(200, Math.max(2, amountOption.getAsLong()));

        String userId = event.getUser().getId();
        event.reply("This will delete " + amount + " messages.\nAre you sure?")
                .addActionRow(
                        Button.danger(userId + ":prune:" + amount, "✅ Confirm"),
                        Button.secondary(userId + ":delete", "❌ Cancel")
                )
                .setEphemeral(true)
                .queue();
    }

    public void handleButtonInteraction(ButtonInteractionEvent event) {
        String[] parts = event.getButton().getId().split(":");
        if (parts.length < 2) return;

        String userId = parts[0];
        String action = parts[1];

        if (!userId.equals(event.getUser().getId()))
            return;

        event.deferEdit().queue();

        if (action.equals("prune")) {
            int amount = Integer.parseInt(parts[2]);
            MessageChannel channel = event.getChannel();
            channel.getIterableHistory()
                    .skipTo(event.getMessageIdLong())
                    .takeAsync(amount)
                    .thenAccept(channel::purgeMessages);
            event.getHook().deleteOriginal().queue();
        } else if (action.equals("delete")) {
            event.getHook().deleteOriginal().queue();
        }
    }
}
