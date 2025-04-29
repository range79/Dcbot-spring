package com.range.discordbot.discordutils.commands.admin;

import com.range.discordbot.dao.model.BannedUser;
import com.range.discordbot.dao.repo.BannedUserRepo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class BanCommand {
    public BanCommand(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }
    private final BannedUserRepo bannedUserRepo;
    private final Logger log = LoggerFactory.getLogger(BanCommand.class);
    @Value(value = "${discord.bot.log.channel_id}")
    private String channelid;


    public void execute(SlashCommandInteractionEvent event)
    {
        Member member = event.getOption("user").getAsMember();
        User user = event.getOption("user").getAsUser();
        if (member == null) {
            event.reply("User not found!").setEphemeral(true).queue();
            return;
        }

        event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
        InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("You do not have the required permissions to ban users from this server.").queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("I don't have the required permissions to ban users from this server.").queue();
            return;
        }

        if (!selfMember.canInteract(member))
        {
            hook.sendMessage("This user is too powerful for me to ban.").queue();
            return;
        }


        // optional command argument, fall back to 0 if not provided
        int delDays = event.getOption("del_days", 0, OptionMapping::getAsInt); // this last part is a method reference used to "resolve" the option value

        // optional ban reason with a lazy evaluated fallback (supplier)
        String reason = event.getOption("reason",
                () -> "Banned by " + event.getUser().getName(), // used if getOption("reason") is null (not provided)
                OptionMapping::getAsString); // used if getOption("reason") is not null (provided)
        try {
            LocalDateTime bannedAt = LocalDateTime.now();
            log.info("User{} has banned at {}", event.getUser().getAsTag(), bannedAt);
            BannedUser bannedUser = bannedUserRepo.findBannedUserByTag(member.getUser().getAsTag());

            if (bannedUser == null) {
                bannedUser = new BannedUser();
                bannedUser.setBannedAt(LocalDateTime.now());
                bannedUser.setReason(reason);
                bannedUser.setAuthor(member.getUser().getEffectiveName());
                bannedUser.setTag(member.getUser().getAsTag());
                bannedUser.setServerId(new ArrayList<>(Collections.singletonList(event.getGuild().getId())));
            } else {
                List<String> serverIds = bannedUser.getServerId();
                if (!serverIds.contains(event.getGuild().getId())) {
                    serverIds.add(event.getGuild().getId());
                }
            }

            // Ban the user and send a success response
            event.getGuild().ban(user, delDays, TimeUnit.DAYS)

                    .reason(reason) // audit-log ban reason (sets X-AuditLog-Reason header)

                    .flatMap(v->{
                        // Ban işleminden sonra mesajı belirli bir kanala gönder
                        TextChannel channel = event.getGuild().getTextChannelById(channelid);
                        if (channel != null) {
                            // chain a followup message after the ban is executed
                            return channel.sendMessage("Banned user " + user.getName() + " for reason: " + reason);
                        }
                        log.info(event.getChannel().getId());
                        return event.getChannel().sendMessage("Ban failed or channel not found.");

                    })
                    .queue();
            bannedUserRepo.save(bannedUser) ;
        }
        catch (Exception e){
            log.error("A problem occured while banning user {}", user.getName());
        }

    }
}

