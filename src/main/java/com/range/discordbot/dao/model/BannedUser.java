package com.range.discordbot.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bannedusers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannedUser {
    /**
     * This entity represents information about users who have been banned from the server.
     * When a user is banned, their relevant data is transformed into an instance of this class
     * and persisted to the database for audit and tracking purposes.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bannedAt;

    private String reason;

    private String author;

    private String tag;
    @ElementCollection
    @CollectionTable(name = "banned_user_servers", joinColumns = @JoinColumn(name = "banned_user_id"))
    @Column(name = "server_id")
    private List<String> serverId;

    public BannedUser(LocalDateTime bannedAt, String reason, String author, String tag,List<String> serverId) {

        this.bannedAt = bannedAt;
        this.reason = reason;
        this.author = author;
        this.tag = tag;
        this.serverId = serverId;

    }
}
