package com.range.discordbot.controller;

import com.range.discordbot.dao.model.BannedUser;
import com.range.discordbot.dto.BanUserRequest;
import com.range.discordbot.service.BannedUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/banned")
    public List<BannedUser> getAllBannedUsers() {
        return bannedUserService.bannedUsers();
    }

    @PostMapping("/ban/{usertag}")
    public BannedUser banUser(@PathVariable String usertag ,@RequestBody BanUserRequest banUserRequest
    ) {
        return bannedUserService.banUser(usertag, banUserRequest.getReason(), banUserRequest.getReason());
    }
}
