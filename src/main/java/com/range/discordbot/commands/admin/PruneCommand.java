package com.range.discordbot.commands.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PruneCommand {
    private final Logger log = LoggerFactory.getLogger(PruneCommand.class);
    public void execute(SlashCommandInteractionEvent event)
    {
        if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
            log.warn("User {} try prune messages without permition",event.getUser().getName());
            event.reply("❌ You don't have permission to manage messages.").setEphemeral(true).queue();
            return;
        }
        OptionMapping amountOption = event.getOption("amount"); // This is configured to be optional so check for null
        int amount = amountOption == null
                ? 100 // default 100
                : (int) Math.min(200, Math.max(2, amountOption.getAsLong())); // enforcement: must be between 2-200
        String userId = event.getUser().getId();
        event.reply("This will delete " + amount + " messages.\nAre you sure?") // prompt the user with a button menu
                .addActionRow(// this means "<style>(<id>, <label>)", you can encode anything you want in the id (up to 100 characters)
                        Button.secondary(userId + ":delete", "Nevermind!"),
                        Button.danger(userId + ":prune:" + amount, "Yes!")) // the first parameter is the component id we use in onButtonInteraction above
                .queue();
    }



}
