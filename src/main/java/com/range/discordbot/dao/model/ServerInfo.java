package com.range.discordbot.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "servers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String serverId;
    private String serverName;
    private String logChannelId;
    private String serverPhotoUrl;
    private boolean isBanned;
    private boolean isRegistered;
}
