package com.range.discordbot.discordutils.commands.admin;

import com.range.discordbot.service.BannedUserService;
import jakarta.transaction.Transactional;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class UnBanCommand {

    private final BannedUserService bannedUserService;
    private static final Logger log = LoggerFactory.getLogger(UnBanCommand.class);

    @Value("${discord.bot.log.channel_id}")
    private String channelId;

    public UnBanCommand(BannedUserService bannedUserService) {
        this.bannedUserService = bannedUserService;
    }
    @Transactional
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);
        String name =event.getOption("tag").getAsString();


        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            hook.sendMessage("You do not have the required permissions to unban users from this server.").queue();
            return;
        }
        String getUser= event.getUser().getAsTag();

        Boolean bannedUser= bannedUserService.isBanned(getUser);
        TextChannel channel = event.getGuild().getTextChannelById(channelId);

        if (bannedUser) {

            try{

                channel.sendMessage("Unbanned user"+name+" for reason: ").queue();
                log.info("Unbanned user {} with slash command", name);
                bannedUserService.unbanUser(name);
            }
            catch (Exception e){
                channel.sendMessage("Unban failed: "+e.getMessage()).queue();
            }
        }
    }

}
