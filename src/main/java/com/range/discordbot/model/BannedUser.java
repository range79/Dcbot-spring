package com.range.discordbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String author;
    private String tag;
    public BannedUser(String name, String reason, String author, String tag) {
        this.name = name;
        this.reason = reason;
        this.author = author;
        this.tag = tag;
    }



}
