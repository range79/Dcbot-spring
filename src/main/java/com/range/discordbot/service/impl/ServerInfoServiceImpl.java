package com.range.discordbot.service.impl;

import com.range.discordbot.dao.model.ServerInfo;
import com.range.discordbot.dao.repo.ServerInfoRepo;
import com.range.discordbot.service.ServerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerInfoServiceImpl implements ServerInfoService {

    private final ServerInfoRepo serverInfoRepo;

    public ServerInfoServiceImpl(ServerInfoRepo serverInfoRepo) {
        this.serverInfoRepo = serverInfoRepo;


    }
    @Override
    public ServerInfo registerServer(String serverId) {
        Optional<ServerInfo> optionalServerInfo = serverInfoRepo.findServerInfoByServerId(serverId);

        if (optionalServerInfo.isPresent()) {
            ServerInfo serverInfo = optionalServerInfo.get();
            serverInfo.setBanned(false);
            serverInfo.setRegistered(true);

            return serverInfoRepo.save(serverInfo);
        } else {
            throw new IllegalStateException("Server with ID " + serverId + " not found!");
        }
    }




    @Override
    public ServerInfo getServerInfoByServerId(String serverId) {
        return serverInfoRepo.findServerInfoByServerId(serverId)
                .orElseThrow(()->new RuntimeException("Server not found"));
    }

    @Override
    public List<ServerInfo> getAllServers() {
        return serverInfoRepo.findAll();
    }



}
