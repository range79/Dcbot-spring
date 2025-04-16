package com.range.discordbot.commands.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

@Service
public class SlashCommandRegisterer {
    private final Logger logger = LoggerFactory.getLogger(SlashCommandRegisterer.class);
    public SlashCommandRegisterer(JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
                Commands.slash("ban", "Ban a user from this server. Requires permission to ban users.")
                        .addOptions(new OptionData(USER, "user", "The user to ban") // USER type allows to include members of the server or other users by id
                                .setRequired(true)) // This command requires a parameter
                        .addOptions(new OptionData(INTEGER, "del_days", "Delete messages from the past days.") // This is optional
                                .setRequiredRange(0, 7)) // Only allow values between 0 and 7 (inclusive)
                        .addOptions(new OptionData(STRING, "reason", "The ban reason to use (default: Banned by <user>)")) // optional reason
                        .setContexts(InteractionContextType.GUILD) // This way the command can only be executed from a guild, and not the DMs
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)) // Only members with the BAN_MEMBERS permission are going to see this command
        );

        // Simple reply commands
        commands.addCommands(
                Commands.slash("say", "Makes the bot say what you tell it to")
                        .setContexts(InteractionContextType.ALL) // Allow the command to be used anywhere (Bot DMs, Guild, Friend DMs, Group DMs)
                        .setIntegrationTypes(IntegrationType.ALL) // Allow the command to be installed anywhere (Guilds, Users)
                        .addOption(STRING, "content", "What the bot should say", true) // you can add required options like this too
        );

        // Commands without any inputs
        commands.addCommands(
                Commands.slash("leave", "Make the bot leave the server")
                        // The default integration types are GUILD_INSTALL.
                        // Can't use this in DMs, and in guilds the bot isn't in.
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED) // only admins should be able to use this command.
        );

        commands.addCommands(
                Commands.slash("prune", "Prune messages from this channel")
                        .addOption(INTEGER, "amount", "How many messages to prune (Default 100)") // simple optional argument
                        // The default integration types are GUILD_INSTALL.
                        // Can't use this in DMs, and in guilds the bot isn't in.
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE))
        );
        commands.addCommands(
                Commands.slash("unban", "Unban messages from this server")
                        .addOption(STRING,"tag","Tell me which user to unban",true)
                        .addOption(STRING, "reason", "Why you unban this user?", false)
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
        );
        commands.addCommands(
                Commands.slash("info"," This method shows information about the bot, including the developer's details.")
                        .setContexts(InteractionContextType.GUILD)
        );
        commands.addCommands(
                Commands.slash("ban-list","This method shows the list of users who banned users.")
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
        );
        commands.queue(success -> logger.info("Successfully registered slash commands!")
                , failure -> logger.error("Failed to register slash commands!"));

    }

}
