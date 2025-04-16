package com.range.discordbot.service;

import com.range.discordbot.model.BannedUser;

import java.util.List;

public interface BannedUserService {
    void unbanUser(String tag);
    List<BannedUser> bannedUsers();
    BannedUser banUser(String usertag, String reason);

}
