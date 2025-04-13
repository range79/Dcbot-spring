package com.range.discordbot.utils;

import com.range.discordbot.dto.ServerUserDto;
import com.range.discordbot.exception.ServerNotFoundException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class DiscordGuildUtil{
    @Value(value = "${discord.bot.server_id}")
    private  String serverId;
    private static final Logger log = LoggerFactory.getLogger(DiscordGuildUtil.class);

    public List<ServerUserDto> getAllMembers(JDA jda) {
        // for getting guild  get server id
        Guild guild = jda.getGuildById(serverId);
        if (guild == null) {
            // if guild not found throw a exception
            throw new ServerNotFoundException("Server not found " + serverId);
        }
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
             *
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
}}

