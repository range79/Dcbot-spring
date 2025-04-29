package com.range.discordbot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BanUserRequest {
    private String reason;
    private String serverId;
}
