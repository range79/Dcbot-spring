package com.range.discordbot.service.impl;

import com.range.discordbot.dao.model.ServerInfo;
import com.range.discordbot.dao.repo.BannedUserRepo;
import com.range.discordbot.dao.repo.ServerInfoRepo;
import org.apache.catalina.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ServerInfoServiceImplTest {
    @Mock
    private ServerInfoRepo serverInfoRepo;
    @InjectMocks
    private ServerInfoServiceImpl serverInfoService;
    ServerInfo serverInfo;
    ServerInfo serverInfo1;

   @BeforeEach
   void setUp() {
        serverInfo = new ServerInfo(1,"31231","aaa","213213","https://nN",false,false);;
        serverInfo1 = new ServerInfo(2,"31231432","aaardfsdf","213213","htsdftps://nN",false,false);;

   }

   @Test
    void registerServer() {

    }

    @Test
    void getServerInfoByServerId() {

        given(serverInfoRepo.findServerInfoByServerId("31231")).willReturn(Optional.of(serverInfo));
        assertEquals(serverInfoService.getServerInfoByServerId("31231"), serverInfo);
    }
    @Test
    void getServerInfoByServerIdNull() {
        assertThrows(RuntimeException.class,()->serverInfoService.getServerInfoByServerId(null));
    }

    @Test
    void getAllServers() {
        List<ServerInfo> serverInfos = List.of(serverInfo, serverInfo1);
        given(serverInfoRepo.findAll()).willReturn(serverInfos);
        assertEquals(serverInfoService.getAllServers(), serverInfos);
    }
}

