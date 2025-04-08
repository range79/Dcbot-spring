package com.range.discordbot.service;

import com.range.discordbot.model.BannedUser;
import com.range.discordbot.repo.BannedUserRepo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

;import java.util.Optional;

@Component
public class UnBanCommand {

    private final BannedUserRepo bannedUserRepo;

    @Value("${discord.bot.banneduser.channelid}")
    private String channelId;

    public UnBanCommand(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }

    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);

        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            hook.sendMessage("You do not have the required permissions to unban users from this server.").queue();
            return;
        }
        String getUser= event.getUser().getAsTag();

   BannedUser bannedUser= bannedUserRepo
           .findByTag(event.getMember().getUser().getAsTag()).orElseThrow(()->new RuntimeException("User not finded in repo"));
    bannedUserRepo.deleteBannedUserById(bannedUser.getId());
    }

}
