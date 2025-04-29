package com.range.discordbot.service.impl;

import com.range.discordbot.dto.ServerUserDto;
import com.range.discordbot.exception.ServerNotFoundException;
import com.range.discordbot.service.DiscordServerService;
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
public class DiscordServerServiceImpl implements DiscordServerService {


    private static final Logger log = LoggerFactory.getLogger(DiscordServerServiceImpl.class);

    private final Guild guild;
    public DiscordServerServiceImpl(JDA jda, @Value("${discord.bot.server_id}")String serverId) {

        this.guild = jda.getGuildById(serverId);
        if (guild == null) {
            log.error("Could not find Discord guild with ID {}", serverId);
            throw new ServerNotFoundException("Could not find Discord guild with ID " + serverId);
        }
    }

    @Override
    public List<ServerUserDto> getAllMembers() {
        try {
            List<Member> members = guild.loadMembers().get();
            List<ServerUserDto> memberTags = new ArrayList<>();


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


    @Override
    public Member getUserById(String tag) {
        try {
            return guild.getMemberByTag(tag);
        } catch (Exception e) {
            log.error("Problem occured when loading user with tag {}", tag);
            return null;
        }
    }
}

