package com.range.discordbot.controller;

import com.range.discordbot.dto.ServerUserDto;
import com.range.discordbot.utils.DiscordGuildUtil;
import net.dv8tion.jda.api.JDA;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
private DiscordGuildUtil discordGuildUtil;
    private final JDA jda;
    public UserController(JDA jda,DiscordGuildUtil discordGuildUtil) {
this.discordGuildUtil = discordGuildUtil;
        this.jda = jda;
    }
    @GetMapping("/all")
    public List<ServerUserDto> getAllUsers() {

      return  discordGuildUtil.getAllMembers();

    }

}
