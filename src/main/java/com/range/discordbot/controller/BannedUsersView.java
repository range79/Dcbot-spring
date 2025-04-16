package com.range.discordbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class BannedUsersView {

    @GetMapping("/admin-panel")
    public String adminPanel() {
        return "admin-panel";
    }



}
