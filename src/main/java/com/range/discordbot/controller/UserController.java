package com.range.discordbot.controller;

import com.range.discordbot.dto.ServerUserDto;
import com.range.discordbot.service.impl.DiscordServerServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Lazy
@RestController
@RequestMapping("/users")
public class UserController {
    private final DiscordServerServiceImpl discordServerServiceImpl;

    public UserController(DiscordServerServiceImpl discordServerServiceImpl) {
        this.discordServerServiceImpl = discordServerServiceImpl;

    }
    @GetMapping("/all")
    public List<ServerUserDto> getAllUsers() {
        return  discordServerServiceImpl.getAllMembers();
    }

}
