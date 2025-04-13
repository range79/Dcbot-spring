package com.range.discordbot.controlelr;

import com.range.discordbot.model.BannedUser;
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

    @GetMapping("/bannedUserS/all")
    public List<BannedUser> getAllBannedUsers() {
       return bannedUserService.bannedUsers();
    }

}
