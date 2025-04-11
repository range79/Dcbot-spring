package com.range.discordbot.service;

import com.range.discordbot.model.BannedUser;
import com.range.discordbot.repo.BannedUserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UnBanCommand {

    private final BannedUserRepo bannedUserRepo;
    private static Logger log = LoggerFactory.getLogger(UnBanCommand.class);

    @Value("${discord.bot.log.channelid}")
    private String channelId;

    public UnBanCommand(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
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

 Boolean bannedUser= bannedUserRepo.existsBannedUserByTag(name);
   TextChannel channel = event.getGuild().getTextChannelById(channelId);

   if (bannedUser==true) {

try{

    channel.sendMessage("Unbanned user"+name+" for reason: ").queue();
    bannedUserRepo.deleteBannedUserByTag(name);
}
catch (Exception e){
    channel.sendMessage("Unban failed: "+e.getMessage()).queue();
}
           }



    }

}
