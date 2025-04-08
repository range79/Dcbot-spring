package com.range.discordbot.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "bannedusers")
@AllArgsConstructor
@NoArgsConstructor
public class BannedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String reason;

    public BannedUser(String name, String reason, String author, String tag) {
        this.name = name;
        this.reason = reason;
        this.author = author;
        this.tag = tag;
    }

    private String author;
    private String tag;




}
