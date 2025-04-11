package com.range.discordbot.service;

import com.range.discordbot.model.BannedUser;
import com.range.discordbot.repo.BannedUserRepo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class BanCommand {
    public BanCommand(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }
    private final BannedUserRepo bannedUserRepo;
    private final Logger log = Logger.getLogger(BanCommand.class.getName());
    @Value(value = "${discord.bot.log.channelid}")
    private String channelid;


    public void execute(SlashCommandInteractionEvent event, User user, Member member)
    {
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

        if (member != null && !selfMember.canInteract(member))
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
            BannedUser bannedUser =new BannedUser(user.getName(), reason, event.getUser().getEffectiveName(),user.getName());
            bannedUserRepo.save(bannedUser) ;
        }
        catch (Exception e){
            log.warning("A problem occured while banning user " + user.getName());
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
                    log.warning(event.getChannel().getId());
                    return event.getChannel().sendMessage("Ban failed or channel not found.");

                })
                .queue();





    }
}

