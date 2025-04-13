package com.range.discordbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bannedusers")
@AllArgsConstructor
@NoArgsConstructor
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
    public BannedUser(LocalDateTime bannedAt, String reason, String author, String tag) {
   this.bannedAt = bannedAt;
        this.reason = reason;
        this.author = author;
        this.tag = tag;
    }



}
