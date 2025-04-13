package com.range.discordbot.service.impl;

import com.range.discordbot.model.BannedUser;
import com.range.discordbot.repo.BannedUserRepo;
import com.range.discordbot.service.BannedUserService;
import com.range.discordbot.exception.BannedUserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BannedBannedUserServiceImpl implements BannedUserService {
   private BannedUserRepo bannedUserRepo;
    public BannedBannedUserServiceImpl(BannedUserRepo bannedUserRepo) {
        this.bannedUserRepo = bannedUserRepo;
    }
    @Override
    public void unbanUser(String tag) {
    if (bannedUserRepo.existsBannedUserByTag(tag)!=true){
    throw new BannedUserNotFoundException("Banned user with tag " + tag + " does not exist");
    }
    bannedUserRepo.deleteBannedUserByTag(tag);

    }

    @Override
    public List<BannedUser> bannedUsers() {
        return bannedUserRepo.findAll();
    }

    @Override
    public void banUser(String tag) {

    }
}
