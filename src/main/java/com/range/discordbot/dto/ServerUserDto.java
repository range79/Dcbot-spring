package com.range.discordbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.utils.ImageProxy;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ServerUserDto {
    /**
     * This DTO is used for retrieving server user information,
     * primarily for moderation purposes such as listing members,
     * accessing their roles, tags, and other relevant details.
     */

    private String id;
    private String username;
    private String userTag;
    private String avatarUrl;
    private List<String> role;
}
