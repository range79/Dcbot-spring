package com.range.discordbot.service;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlashCommands extends ListenerAdapter {
    private final BanCommand banCommand;
    private final SayCommand sayCommand;
    private final LeaveCommand leaveCommand;
    private final PruneCommand pruneCommand;
    private final UnBanCommand unbanCommand;
    public SlashCommands(BanCommand banCommand, SayCommand sayCommand, LeaveCommand leaveCommand, PruneCommand pruneCommand,UnBanCommand unbanCommand) {
        this.banCommand = banCommand;
        this.sayCommand = sayCommand;
        this.leaveCommand = leaveCommand;
        this.pruneCommand = pruneCommand;
        this.unbanCommand = unbanCommand;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName())
        {
            case "ban":

                handleBanCommand(event);
                break;
            case "say":
                handleSayCommand(event); // content is required so no null-check here
                break;
            case "leave":
                handleLeaveCommand(event);
                break;
            case "prune": // 2 stage command with a button prompt
                handlePruneCommand(event);
                break;
            case "unban":
                handleUnBanCommand(event);
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event)
    {
        String[] id = event.getComponentId().split(":"); // this is the custom id we specified in our button
        String authorId = id[0];
        String type = id[1];
        // Check that the button is for the user that clicked it, otherwise just ignore the event (let interaction fail)
        if (!authorId.equals(event.getUser().getId()))
            return;
        event.deferEdit().queue(); // acknowledge the button was clicked, otherwise the interaction will fail

        MessageChannel channel = event.getChannel();
        switch (type)
        {
            case "prune":
                int amount = Integer.parseInt(id[2]);
                event.getChannel().getIterableHistory()
                        .skipTo(event.getMessageIdLong())
                        .takeAsync(amount)
                        .thenAccept(channel::purgeMessages);
                // fallthrough delete the prompt message with our buttons
            case "delete":
                event.getHook().deleteOriginal().queue();
        }
    }
    private void handleUnBanCommand(SlashCommandInteractionEvent event){
        try {


            unbanCommand.execute(event);
        }catch (Exception e){
            log.warn("unban failed:"+e.getMessage());
            TextChannel textChannel =event.getChannel().asTextChannel();
            textChannel.sendMessage("Wrong user id").queue();
        }
    }

    private void handleBanCommand(SlashCommandInteractionEvent event) {
        Member member = event.getOption("user").getAsMember();
        User user = event.getOption("user").getAsUser();
        banCommand.execute(event, user, member);
    }

    private void handleSayCommand(SlashCommandInteractionEvent event) {
        String content = event.getOption("content").getAsString();
        sayCommand.execute(event, content);
    }

    private void handleLeaveCommand(SlashCommandInteractionEvent event) {
        leaveCommand.execute(event);
    }

    private void handlePruneCommand(SlashCommandInteractionEvent event) {
        pruneCommand.execute(event);
    }





}
