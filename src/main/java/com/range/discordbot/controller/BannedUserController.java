package com.range.discordbot.controller;

import com.range.discordbot.model.BannedUser;
import com.range.discordbot.service.BannedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(name = "/users")
public class BannedUserController {

    private final BannedUserService bannedUserService;

    public BannedUserController(BannedUserService bannedUserService) {
        this.bannedUserService = bannedUserService;

    }
    //Todo l will make changes in this class

    @DeleteMapping("/unban/{usertag}")
    public void Unbanuser(@PathVariable String usertag) {
        bannedUserService.unbanUser(usertag);
    }

    @GetMapping("/bannedUserS/all")
    public List<BannedUser> getAllBannedUsers() {
       return bannedUserService.bannedUsers();
    }

    @PostMapping("/ban/user/{usertag}")
    public BannedUser banUser(@PathVariable String usertag,@RequestParam(required = false) String reason) {
      return bannedUserService.banUser(usertag,reason);
    }

}
