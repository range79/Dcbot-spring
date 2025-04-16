package com.range.discordbot.commands.admin;

import com.range.discordbot.service.BannedUserService;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ListBannedUsers {
 private final BannedUserService bannedUserService;
 public ListBannedUsers(BannedUserService bannedUserService) {
     this.bannedUserService = bannedUserService;

 }
 public void execute(SlashCommandInteractionEvent event) {
     if (event.getMember().hasPermission(Permission.KICK_MEMBERS))
     {
         event.reply("You do not have permission to use this command!").queue();
     }
     else
         event.reply("Banned Users:"+bannedUserService.bannedUsers()).queue();
 }


}
