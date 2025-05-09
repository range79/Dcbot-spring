package com.range.discordbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {


    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }
    @GetMapping("admin")
    public String getadmin(){

        return "admin";

    }

}
