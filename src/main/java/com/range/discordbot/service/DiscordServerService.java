package com.range.discordbot.service;

import com.range.discordbot.dto.ServerUserDto;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public interface DiscordServerService {
    /**
     * Finds a Member from the server using their tag.
     *
     * @param tag the tag of the user to search for (e.g., Azad#1234)
     * @return the Member object if found, otherwise null
     */
    Member getUserById(String tag);
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
    List<ServerUserDto> getAllMembers();
}
