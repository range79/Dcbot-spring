package com.range.discordbot.service;

import com.range.discordbot.model.BannedUser;

import java.util.List;

public interface BannedUserService {

    /**
     * Unbans a user from the server using their unique tag.
     *
     * @param tag the Discord tag (e.g., Username#1234) of the user to unban
     */
    void unbanUser(String tag);
    /**
     * Retrieves a list of all banned users from the database.
     * <p>
     * This method queries the repository and returns a list of users
     * who have been banned from the Discord server. Each banned user
     * is represented with detailed information such as username, tag,
     * reason for ban, and the time they were banned.
     *
     * @return a list of banned users retrieved from the database
     */
    List<BannedUser> bannedUsers();

    /**
     * Bans a user with the given tag and reason.
     *
     * @param usertag the Discord tag (e.g., Username#1234) of the user to ban
     * @param reason  the reason for banning the user
     * @return the banned user object containing details of the ban
     */
    BannedUser banUser(String usertag, String reason);
    /**
     * Checks if a user with the given tag exists in the repository.
     *
     * @param tag the Discord tag (e.g., Username#1234) to search for
     * @return true if the user exists, false otherwise
     */
    boolean isBanned(String tag);

}
