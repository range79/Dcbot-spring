package com.range.discordbot.utils;

import com.range.discordbot.dto.ServerUserDto;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Lazy
public class DiscordGuildUtil{


    private static final Logger log = LoggerFactory.getLogger(DiscordGuildUtil.class);

    private final Guild guild;
    public DiscordGuildUtil(JDA jda,@Value("${discord.bot.server_id}")String serverId) {

        this.guild = jda.getGuildById(serverId);
    }
    public List<ServerUserDto> getAllMembers() {
        // for getting guild  get server id


        try {
            List<Member> members = guild.loadMembers().get();
            List<ServerUserDto> memberTags = new ArrayList<>();

            /**
             * Retrieves a list of server members and their associated data.
             * This data is processed and transferred temporarily; it is not stored in the database.
             * For each member, the following information is collected:
             * - Member ID
             * - Username
             * - User tag (including discriminator)
             * - Avatar URL (direct URL, not stored, accessed each time)
             * - List of roles assigned to the member
             * <p>
             * The data is transformed into `ServerUserDto` objects and added to a temporary list.
             * This list can be used for further processing, but it is not stored in the database.
             */
            for (Member member : members) {
                List<String> roleNames = member.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList());

                memberTags.add(new ServerUserDto(member.getId(),member.getUser().getName(),member.getUser().getAsTag(),member.getEffectiveAvatarUrl(),roleNames));
            }
            return memberTags;
        } catch (Exception e) {

            log.error("Problem occured when loading users: {}", e.getMessage());
            return null;
        }
    }

    public Member getUserById(String tag) {
        try {
            return guild.getMemberByTag(tag);
        } catch (Exception e) {
            log.error("Problem occured when loading user with tag {}", tag);
            return null;
        }
    }
}

