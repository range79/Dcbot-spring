package com.range.discordbot.dao.repo;

import com.range.discordbot.dao.model.ServerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerInfoRepo extends JpaRepository<ServerInfo, Long> {
    Optional <ServerInfo> findServerInfoByServerId(String serverId);


}
