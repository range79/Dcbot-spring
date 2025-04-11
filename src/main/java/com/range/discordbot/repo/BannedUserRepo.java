package com.range.discordbot.repo;

import com.range.discordbot.model.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedUserRepo extends JpaRepository<BannedUser,Long> {



    /*
        This method checks if a user is banned by querying the database.
        It returns `true` if the user is found in the banned list,
        and `false` if the user is not banned. The method helps in
        determining whether a user should be allowed to perform certain actions
        based on their ban status.
    */
    Boolean existsBannedUserByTag(String tag);
    /*
        This method removes a user from the banned user database based on their tag.
        Once the user is deleted from the banned list, they are no longer restricted
        and can join the server or perform actions that were previously blocked.
        The method is typically used to lift a ban and restore the user's access to the server.
    */
    void deleteBannedUserByTag(String tag);
}
