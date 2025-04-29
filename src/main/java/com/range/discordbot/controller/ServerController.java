package com.range.discordbot.controller;

import com.range.discordbot.dao.model.ServerInfo;
import com.range.discordbot.service.ServerInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "/servers")
public class ServerController {
    private final ServerInfoService serverInfoService;
public ServerController(ServerInfoService serverInfoService) { this.serverInfoService = serverInfoService; }

    /**
     * this method for get all users data
     */
    @GetMapping("/all")
    public List<ServerInfo> getAllServers(){
       return serverInfoService.getAllServers();
    }

    /**
     * this endpoint for get specific servers data
     */
    @GetMapping("{id}")
    public ServerInfo getserverinfo(@PathVariable String id) {
        return  serverInfoService.getServerInfoByServerId(id);
    }
    @PostMapping("/register-server/{serverId}")
    public ServerInfo registerServer(@PathVariable String serverId) {
    return  serverInfoService.registerServer(serverId);

    }

}
